package com.simpllyrun.srcservice.api.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.feed.domain.Image;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.PostDto;
import com.simpllyrun.srcservice.api.feed.repository.PostRepository;
import com.simpllyrun.srcservice.api.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        postRepository.deleteAll();

    }


    @DisplayName("post 생성하기")
    @Test
    @WithMockUser(username = "1")
    @Transactional
    void addPost() throws Exception {
        //given
        final String url = "/api/posts";

        userRepository.save(User.builder().build());

        //================== @RequestPart(value = "dto") @Valid PostDto postDto ======================

            //postDto 생성
        final String content = "content";

        PostDto postDto = PostDto.builder()
                .content(content)
                .build();

            //postDto -> requestBody (json 형식으로 변환)
        String requestBody = objectMapper.writeValueAsString(postDto);

            //requestBody -> multipartFile 형식으로 변환  //MockMultipartFile의 name은 @RequestPart의 value 값과 같아야 함 (dto)
        MockMultipartFile dto = new MockMultipartFile("dto", "", MediaType.APPLICATION_JSON_VALUE, requestBody.getBytes(StandardCharsets.UTF_8));

        //===================== @RequestPart(value = "images") List<MultipartFile> multipartFiles =========================
            //이미지 파일 생성
        final String fileName1 = "고양이";
        final String fileName2 = "호랑이";

        final String contentType = "png"; //확장자

            //MockMultipartFile의 name은 @RequestPart의 value 값과 같아야 함 (images)
        MockMultipartFile imageFile1 = new MockMultipartFile("images", fileName1 + "." + contentType, MediaType.IMAGE_PNG_VALUE, "images".getBytes()); //이 방식도 가능
        MockMultipartFile imageFile2 = new MockMultipartFile("images", fileName2 + "." + contentType, "image/png", "<<png data>>".getBytes());


        //when
        ResultActions result = mockMvc.perform(multipart(url)
                .file(imageFile1).file(imageFile2).file(dto)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .characterEncoding("UTF-8"));


        //then
        result.andExpect(status().isOk());

        //verify
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(1); // post 전체 개수

        System.out.println("=============================== post 검증 ================================");
        assertThat(posts.get(0).getId()).isEqualTo(1); // post의 id값 확인
        assertThat(posts.get(0).getContent()).isEqualTo(content); //postDto content 확인

        System.out.println("post에 저장된 image의 개수 = "+ posts.get(0).getPostImages().size());

        assertThat(posts.get(0).getPostImages().get(0).getOriginalFilename()).isEqualTo(fileName1+"."+contentType); //fileName1 확인
        System.out.println("post에 저장된 첫번째 image의 originalFilename = " + posts.get(0).getPostImages().get(0).getOriginalFilename());
        System.out.println("post에 저장된 첫번째 image의 storeFilename = " + posts.get(0).getPostImages().get(0).getStoreFilename());

        assertThat(posts.get(0).getPostImages().get(1).getOriginalFilename()).isEqualTo(fileName2+"."+contentType); //fileName2 확인
        System.out.println("post에 저장된 두번째 image의 originalFilename = " + posts.get(0).getPostImages().get(1).getOriginalFilename());
        System.out.println("post에 저장된 두번째 image의 storeFilename = " + posts.get(0).getPostImages().get(1).getStoreFilename());

    }

    @DisplayName("Post 삭제하기")
    @Test
    @WithMockUser(username = "1")
    void deletePost() throws Exception {
        //given
        final String url = "/api/posts/{postId}";
        final String content = "content";

        Post post = Post.builder()
                .content(content)
                .build();

        postRepository.save(post);
        System.out.println("postId = " + post.getId());

        //when
        ResultActions result = mockMvc.perform(delete(url, post.getId()));

        //then
        result.andExpect(status().isOk());

        //verify
        Optional<Post> findPost = postRepository.findById(post.getId());

        System.out.println("findPost = " + findPost);
        assertThat(findPost).isEmpty();

        assertThat(postRepository.existsById(post.getId())).isFalse();
    }

    @DisplayName("Post 수정하기")
    @Test
    @WithMockUser(username = "1")
    @Transactional
    void updatePost() throws Exception {
        //given
        final String url = "/api/posts/{id}";
        final String content = "content";
        final String updateContent = "update content";
        final List<Image> postImages = new ArrayList<>();

        Post post1 = Post.builder()
                .content(content)
                .postImages(postImages)
                .build();

        Post post2 = Post.builder()
                .content(content)
                .postImages(postImages)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        PostDto postDto = PostDto.builder()
                .content(updateContent)
                .build();

        String requestBody = objectMapper.writeValueAsString(postDto);
        MockMultipartFile dto = new MockMultipartFile("dto", "", MediaType.APPLICATION_JSON_VALUE, requestBody.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile imageFile = new MockMultipartFile("images", "update.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());

        //when
            //content만 변경
        ResultActions result = mockMvc.perform(multipart(HttpMethod.PATCH, url,post1.getId())
                .file(dto));

            //content 변경, imageFile 추가
        ResultActions result2 = mockMvc.perform(multipart(HttpMethod.PATCH, url,post2.getId())
                .file(dto).file(imageFile));

        //then
        result.andExpect(status().isOk());
        result2.andExpect(status().isOk());

        //verify
        Post findPost1 = postRepository.findById(post1.getId()).get();
        System.out.println("findPost1 content = " + findPost1.getContent());
        assertThat(findPost1.getContent()).isEqualTo(updateContent);

        Post findPost2 = postRepository.findById(post2.getId()).get();
        System.out.println("findPost2 content = " + findPost2.getContent());
        assertThat(findPost2.getContent()).isEqualTo(updateContent);
        System.out.println("postImage originalFilename = "+findPost2.getPostImages().get(0).getOriginalFilename());
        assertThat(findPost2.getPostImages().get(0).getOriginalFilename()).isEqualTo("update.png");
    }
}