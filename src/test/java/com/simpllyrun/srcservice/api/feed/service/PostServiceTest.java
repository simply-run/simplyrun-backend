package com.simpllyrun.srcservice.api.feed.service;


import com.simpllyrun.srcservice.api.feed.domain.Image;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.ImageRepository;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;

import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
    private ImageRepository imageRepository;

    @Autowired
    private PostService postService;

    @Test
    @WithMockUser(username = "1")
    @DisplayName("Post 생성 테스트")
    void createPost() {
        // given
        List<Image> postImages = new ArrayList<>();

        var toUser = User.builder()
                .build();
        var postDto = PostDto.builder()
                .content("test")
                .build();

        var image1 = Image.builder()
                .id(1L)
                .originalFilename("abc")
                .build();

        var image2 = Image.builder()
                .id(2L)
                .originalFilename("def")
                .build();
        postImages.add(image1);
        postImages.add(image2);

        var result = Post.builder()
                .id(1L)
                .content(postDto.getContent())
                .user(toUser)
                .postImages(postImages)
                .build();


        List<MultipartFile> multipartFiles = new ArrayList<>();
        MockMultipartFile file1 = new MockMultipartFile("images", "hello.png", MediaType.IMAGE_PNG_VALUE, "hello, world!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("images", "byebye.png", MediaType.IMAGE_PNG_VALUE, "hello, world!".getBytes());
        multipartFiles.add(file1);
        multipartFiles.add(file2);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(toUser));
        given(postRepository.save(any())).willReturn(result);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(result));
        given(imageRepository.save(any())).willReturn(image1);

        // when
        var postId = postService.createPost(postDto, multipartFiles);

        // then
        verify(postRepository).save(any());

        System.out.println("postId = " + postId);
        assertThat(postId).isEqualTo(1L);
        Post post = postRepository.findById(postId).get();
        assertThat(post.getPostImages().get(0).getOriginalFilename()).isEqualTo("abc");
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
        @DisplayName("IllegalArgumentException 테스트")
        void exceptionTest(){
            //exception 테스트 (없는 id 넣기)
            assertThatThrownBy(() -> postService.deletePost(2L))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Nested
    @DisplayName("Post 수정 테스트")
    class updateTest{

        final Long postId = 1L;
        final String content1 = "before change";
        final String content2 = "After change";
        final List<Image> postImages = new ArrayList<>();
        final List<MultipartFile> multipartFiles = new ArrayList<>();

        Image image = Image.builder()
                .id(1L)
                .originalFilename("serviceUpdate.png")
                .build();

        Post post = Post.builder()
                .id(postId)
                .content(content1)
                .postImages(postImages)
                .build();

        @Test
        @WithMockUser(username = "1")
        @DisplayName("Post 수정 성공")
        void updatePost(){
            //given
            given(postRepository.save(any())).willReturn(post);
            given(postRepository.findById(postId)).willReturn(Optional.of(post));
            given(imageRepository.save(any())).willReturn(image);

            MockMultipartFile imageFile = new MockMultipartFile("images", "serviceUpdate.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());
            multipartFiles.add(imageFile);

            System.out.println("originalfilename = " + multipartFiles.get(0).getOriginalFilename());

            System.out.println("Before-update content = " + post.getContent());

            //when
            postService.updatePost(postId, content2, multipartFiles);

            //then
            verify(postRepository).save(post);
            assertThat(post.getContent()).isEqualTo(content2);
            System.out.println("After-update content = " + post.getContent());

            System.out.println("postImages size = " + post.getPostImages().size());
            System.out.println("postImages id = " + post.getPostImages().get(0).getId());
            assertThat(post.getPostImages().get(0).getOriginalFilename()).isEqualTo("serviceUpdate.png");
            System.out.println("postImage originalFilename = "+ post.getPostImages().get(0).getOriginalFilename());

        }

        @Test
        @DisplayName("IllegalArgumentException 테스트")
        void exceptionTest(){

            //exception 테스트 (없는 id 넣기)
            assertThatCode(()->postService.updatePost(1L, content2, multipartFiles))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
