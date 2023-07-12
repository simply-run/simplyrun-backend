package com.simpllyrun.srcservice.api.feed.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostImageRepository;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;

import com.simpllyrun.srcservice.api.feed.service.post.PostService;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostImageRepository postImageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Test
    @WithMockUser(username = "1")
    @DisplayName("Post 생성 테스트")
    void createPost() {
        // given
        var toUser = User.builder()
                .build();

        List<MultipartFile> multipartFiles = new ArrayList<>();
        MockMultipartFile file1 = new MockMultipartFile("multipartFiles", "hello.png", MediaType.IMAGE_PNG_VALUE, "hello, world!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("multipartFiles", "byebye.png", MediaType.IMAGE_PNG_VALUE, "hello, world!".getBytes());
        multipartFiles.add(file1);
        multipartFiles.add(file2);

        var postDto = PostDto.PostRequestDto.builder()
                .title("title")
                .content("test")
                .multipartFiles(multipartFiles)
                .build();

        var result = Post.builder()
                .id(1L)
                .content(postDto.getContent())
                .user(toUser)
                .build();

        given(userRepository.findById(anyLong())).willReturn(Optional.of(toUser));
        given(postRepository.save(any())).willReturn(result);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(result));

        // when
        var postId = postService.createPost(postDto);

        // then
        verify(postRepository).save(any());

        System.out.println("postId = " + postId);
        assertThat(postId).isEqualTo(1L);
        Post post = postRepository.findById(postId).get();
        assertThat(post.getPostImages().get(0).getOriginalFilename()).isEqualTo("hello.png");
    }

    @Nested
    @DisplayName("Post 삭제 테스트")
    class deleteTest{
        Post post = Post.builder()
                .id(1L)
                .content("content")
                .build();
        @Test
        @WithMockUser(username = "1")
        @DisplayName("삭제 성공")
        void deletePost() {
            //given
            given(postRepository.findById(1L)).willReturn(Optional.of(post));

            // when
            postService.deletePost(post.getId());

            //then
            verify(postRepository).delete(post);

        }

        @Test
        @WithMockUser(username = "1")
        @DisplayName("NoSuchElementException 테스트")
        void exceptionTest(){
            given(postRepository.findById(1L)).willReturn(Optional.empty());
            //exception 테스트 (없는 id 넣기)
            assertThatThrownBy(() -> postService.deletePost(1L))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }


    @Nested
    @DisplayName("Post 수정 테스트")
    class updateTest{

        final Long postId = 1L;
        final String title1 = "Before change title";
        final String title2 = "After change title";
        final String content1 = "Before change content";
        final String content2 = "After change content";
        final List<PostImage> postImages = new ArrayList<>();
        final List<MultipartFile> multipartFiles = new ArrayList<>();

        PostImage postImage = PostImage.builder()
                .originalFilename("abc.png")
                .build();

        Post post = Post.builder()
                .id(postId)
                .title(content1)
                .content(content1)
                .build();

        PostDto.PostRequestDto postDto = PostDto.PostRequestDto.builder().title(title2).content(content2).build();

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Post 수정 성공")
        void updatePost(){
            postImages.add(postImage);
            post.updateImage(postImages);
            //given
            given(postRepository.save(any())).willReturn(post);
            given(postRepository.findById(postId)).willReturn(Optional.of(post));
            given(postImageRepository.save(any())).willReturn(postImage);

            MockMultipartFile imageFile = new MockMultipartFile("multipartFiles", "serviceUpdate.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());
            multipartFiles.add(imageFile);

            System.out.println("originalfilename = " + multipartFiles.get(0).getOriginalFilename());

            System.out.println("Before-update title = " + post.getTitle());
            System.out.println("Before-update content = " + post.getContent());

            //when
            postService.updatePost(postId, postDto, multipartFiles);

            //then
            verify(postRepository).save(post);
            assertThat(post.getTitle()).isEqualTo(title2);
            assertThat(post.getContent()).isEqualTo(content2);
            System.out.println("After-update title = " + post.getTitle());
            System.out.println("After-update content = " + post.getContent());

            System.out.println("postId = " + post.getId());
            System.out.println("postImages size = " + post.getPostImages().size());
            assertThat(post.getPostImages().get(1).getOriginalFilename()).isEqualTo("serviceUpdate.png");
            System.out.println("postImage originalFilename = "+ post.getPostImages().get(1).getOriginalFilename());

        }

        @Test
        @DisplayName("NoSuchElementException 테스트")
        void exceptionTest(){
            System.out.println("postId = " + post.getId());
            given(postRepository.findById(1L)).willReturn(Optional.of(post));

            //exception 테스트 (없는 id 넣기)
            assertThatCode(()->postService.updatePost(2L, postDto, multipartFiles))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Nested
    @DisplayName("Post 조회 테스트")
    class findTest{
        User user = User.builder()
                .id(1L)
                .userId("khw")
                .build();
        Post post = Post.builder().id(1L).title("title").content("content").user(user).build();
        Post post2 = Post.builder().id(2L).title("title2").content("content2").user(user).build();
        Post post3 = Post.builder().id(3L).title("title3").content("content3").user(user).build();

        @Test
        @DisplayName("Post 단건 조회 테스트")
        void findPostTest() throws JsonProcessingException {
            //given
            given(postRepository.findByIdFetchJoin(anyLong())).willReturn(post);

            //when
            PostDto.PostResponseDto postDto = postService.findPostById(1L);

            //then
            System.out.println("postDto = " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postDto));
            assertThat(postDto.getContent()).isEqualTo("content");
        }

        @Test
        @DisplayName("userId가 작성한 Post 전체 조회")
        void findPostUser() throws JsonProcessingException {
            //given
            List<Post> postList = new ArrayList<>();
            postList.add(post);
            postList.add(post2);
            postList.add(post3);

            PageRequest pageRequest = PageRequest.of(0, 10);
            PageImpl<Post> postPageImpl = new PageImpl<>(postList, pageRequest, postList.size());

            given(userRepository.findByUserId(user.getUserId())).willReturn(Optional.of(user));
            given(postRepository.findAllByUserId(anyString(), eq(pageRequest))).willReturn(postPageImpl);

            //when
            Page<Post> findPostPage = postService.findAllByUserId(user.getUserId(), pageRequest);

            //then
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(findPostPage));
            assertThat(findPostPage.getTotalElements()).isEqualTo(postPageImpl.getTotalElements());
        }

        @Test
        @DisplayName("Post 전체 조회")
        void findAll() throws JsonProcessingException {
            //given
            List<Post> postList = new ArrayList<>();
            postList.add(post);
            postList.add(post2);
            postList.add(post3);

            PageRequest pageRequest = PageRequest.of(0, 10);
            PageImpl<Post> postPageImpl = new PageImpl<>(postList, pageRequest, postList.size());

            given(postRepository.findAll(pageRequest)).willReturn(postPageImpl);

            //when
            Page<Post> findAll = postService.findAll(pageRequest);

            //then
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(findAll));
            assertThat(findAll.getTotalElements()).isEqualTo(postPageImpl.getTotalElements());
        }
    }


}
