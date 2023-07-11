package com.simpllyrun.srcservice.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.simpllyrun.srcservice.api.feed.domain.*;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;

import com.simpllyrun.srcservice.api.feed.service.post.PostService;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.dto.UserDto;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    PostRepository postRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PostService postService;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();

    }


    @DisplayName("post 생성하기")
    @Test
    @WithMockUser(username = "1")
    void addPost() throws Exception {
        //given
        var user = User.builder().build();
        UserDto userDto = UserDto.of(user);

        //================== @RequestPart(value = "dto") @Valid PostDto postDto ======================
            //postDto 생성
        PostDto.PostRequestDto postDto = PostDto.PostRequestDto.builder()
                .title("title")
                .content("content")
                .category(Post.CategoryEnum.COMMUNITY)
                .user(userDto)
                .build();

            //postDto -> requestBody (json 형식으로 변환)
        String requestBody = objectMapper.writeValueAsString(postDto);

            //requestBody -> multipartFile 형식으로 변환  //MockMultipartFile의 name은 @RequestPart의 value 값과 같아야 함 (dto)
        MockMultipartFile dto = new MockMultipartFile("dto", "", MediaType.APPLICATION_JSON_VALUE, requestBody.getBytes(StandardCharsets.UTF_8));

        //===================== @RequestPart(value = "images") List<MultipartFile> multipartFiles =========================
            //이미지 파일 생성
            //MockMultipartFile의 name은 @RequestPart의 value 값과 같아야 함 (images)
        MockMultipartFile imageFile1 = new MockMultipartFile("multipartFiles", "고양이.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes()); //이 방식도 가능
        MockMultipartFile imageFile2 = new MockMultipartFile("multipartFiles", "호랑이.png", "image/png", "<<png data>>".getBytes());

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(imageFile1);
        multipartFiles.add(imageFile2);

        //===============================================================================

        given(postService.createPost(postDto, multipartFiles)).willReturn(1L);

        //when
        ResultActions result = mockMvc.perform(multipart("/api/posts")
                .file(imageFile1).file(imageFile2).file(dto)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .characterEncoding("UTF-8"));


        //then
        result.andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("Post 삭제하기")
    @Test
    @WithMockUser(username = "1")
    void deletePost() throws Exception {
        //given
        final String URL = "/api/posts/{postId}";
        final String content = "content";

        Post post = Post.builder()
                .id(1L)
                .content(content)
                .build();

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        System.out.println("postId = " + post.getId());

        //when
        ResultActions result = mockMvc.perform(delete(URL, 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("Post 수정하기")
    @Test
    @WithMockUser(username = "1")
    void updatePost() throws Exception {
        //given
        final String URL = "/api/posts/{id}";
        final String title = "title";
        final String updateTitle = "update title";
        final String content = "content";
        final String updateContent = "update content";
        final List<PostImage> postImages = new ArrayList<>();
        User user = User.builder().id(1L).build();

        Post post = Post.builder()
                .id(1L)
                .title(title)
                .content(content)
                .build();
        post.updateImage(postImages);


        PostDto.PostRequestDto postDto = PostDto.PostRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .user(UserDto.of(user))
                .category(Post.CategoryEnum.COMMUNITY)
                .build();

        String requestBody = objectMapper.writeValueAsString(postDto);
        MockMultipartFile dto = new MockMultipartFile("dto", "", MediaType.APPLICATION_JSON_VALUE, requestBody.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile imageFile = new MockMultipartFile("multipartFiles", "update.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());

        //when
        ResultActions result = mockMvc.perform(multipart(HttpMethod.PUT, URL, post.getId())
                .file(dto).file(imageFile));

        //then
        result.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("Post 단건 조회하기")
    @Test
    @WithMockUser(username = "1")
    void findPost() throws Exception {
        //given
        final String URL = "/api/posts/{postId}";

        User postUser = User.builder().id(1L).userId("ims98923").name("이민석").profileImageUrl("http://s3").build();

        User commentUser = User.builder().id(2L).userId("khw2531").name("김현우").profileImageUrl("http://s3").build();

        Post post = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .categoryType(Post.CategoryEnum.COMMUNITY)
                .user(postUser)
                .build();

        List<PostImage> postImages = new ArrayList<>();
        PostImage postImage = PostImage.builder().originalFilename("abc").build();
        postImage.setPost(post);
        postImages.add(postImage);
        post.updateImage(postImages);


        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().user(commentUser).content("좋아요").post(post).build());
        comments.add(Comment.builder().user(commentUser).content("멋져요").post(post).build());

        List<PostLike> postLikes = new ArrayList<>();
        postLikes.add(PostLike.builder().user(commentUser).post(post).build());
        postLikes.add(PostLike.builder().user(commentUser).post(post).build());

        post.updateImage(postImages);
        post.updateComments(comments);
        post.updatePostLikes(postLikes);

        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findById(any())).willReturn(Optional.of(post));
        given(postService.findPostById(any())).willReturn(PostDto.PostResponseDto.of(post));

        //when
        ResultActions result = mockMvc.perform(get(URL, 1L));

        //then
        result.andExpect(status().isOk())
                .andDo(print());

    }
    @DisplayName("userId로 post 전체 조회")
    @Test
    void findAllByUserId() throws Exception {
        //given
        final String URL = "/api/posts/list/{userId}";

        User user1 = User.builder().id(1L)
                .userId("user1").build();


        List<Post> postList = new ArrayList<>();
        Post post1 = Post.builder().id(1L).title("title1").content("content1").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        Post post2 = Post.builder().id(2L).title("title2").content("content2").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        Post post3 = Post.builder().id(3L).title("title3").content("content3").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<Post> postPageImpl = new PageImpl<>(postList, pageRequest, postList.size());

        given(userRepository.findByUserId(anyString())).willReturn(Optional.of(user1));
        given(postService.findAllByUserId(anyString(), eq(pageRequest))).willReturn(postPageImpl);

        //when
        ResultActions result = mockMvc.perform(get(URL, "user1", pageRequest));

        //then
        result.andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("post 전체 조회하기")
    @Test
    void findAllPost() throws Exception {
        //given
        final String URL = "/api/posts/list";

        User user1 = User.builder().id(1L)
                .userId("user1").build();

        List<Post> postList = new ArrayList<>();
        Post post1 = Post.builder().id(1L).title("title1").content("content1").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        Post post2 = Post.builder().id(2L).title("title2").content("content1").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        Post post3 = Post.builder().id(3L).title("title3").content("content1").categoryType(Post.CategoryEnum.COMMUNITY).user(user1).build();
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageImpl<Post> postPageImpl = new PageImpl<>(postList, pageRequest, postList.size());

        given(postService.findAll(any())).willReturn(postPageImpl);
        //when
        ResultActions result = mockMvc.perform(get(URL, pageRequest));

        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

}