package com.simpllyrun.srcservice.api.feed.service.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import com.simpllyrun.srcservice.api.feed.dto.CommentDto;
import com.simpllyrun.srcservice.api.feed.repository.CommentRepository;
import com.simpllyrun.srcservice.api.feed.repository.post.PostRepository;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentService commentService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private PostRepository postRepository;

    @Test
    @WithMockUser(username = "1")
    @DisplayName("댓글 생성 테스트")
    void createComment(){
        //given
        var user = User.builder().id(1L).userId("khw4756").build();
        var post = Post.builder().id(1L).user(user).content("content").build();
        var comment = Comment.builder().id(1L).content("comment").build();
        var commentDto = CommentDto.builder().content("commentDto").build();

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(commentRepository.save(any())).willReturn(comment);

        //when
        Long commentId = commentService.addComment(post.getId(), commentDto);

        //then
        assertThat(commentId).isNotNull();
        assertThat(commentId).isEqualTo(1L);

    }
    @Nested
    @DisplayName("댓글 삭제 테스트")
    class deleteTest{
        @Test
        @WithMockUser(username = "1")
        @DisplayName("댓글 삭제 성공")
        void deleteComment(){
            //given
            var comment = Comment.builder().id(1L).content("comment").build();
            given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
            //when
            commentService.deleteComment(comment.getId());
            //then
            verify(commentRepository).delete(comment);

        }
        @Test
        @WithMockUser(username = "1")
        @DisplayName("댓글 삭제 NoSuchElementException 테스트")
        void exceptionTest(){
            given(commentRepository.findById(1L)).willReturn(Optional.empty());
            //exception 테스트 (없는 id 넣기)
            assertThatThrownBy(() -> commentService.deleteComment(1L))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Nested
    @DisplayName("댓글 수정 테스트")
    class updateTest {
        Comment comment = Comment.builder().id(1L).content("comment").build();
        CommentDto commentDto = CommentDto.builder().content("commentDto").build();

        @Test
        @WithMockUser(username = "1")
        @DisplayName("댓글 수정 성공")
        void updateComment(){
            //given
            given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

            //when
            commentService.updateComment(comment.getId(), commentDto);
            //then
            assertThat(comment.getContent()).isEqualTo("commentDto");
            System.out.println("comment content = " + comment.getContent());

        }
        @Test
        @DisplayName("댓글 수정 NoSuchElementException 테스트")
        void exceptionTest(){
            System.out.println("commentId = " + comment.getId());
            given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

            //exception 테스트 (없는 id 넣기)
            assertThatCode(()->commentService.updateComment(2L, commentDto))
                    .isInstanceOf(NoSuchElementException.class);
        }

    }

    @Nested
    @DisplayName("댓글 단건 조회 테스트")
    class 댓글조회테스트{
        Comment comment = Comment.builder().id(1L).content("comment").build();
        @Test
        @WithMockUser(username = "1")
        @DisplayName("댓글 조회 테스트")
        void findComment() throws JsonProcessingException {
            //given
            given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

            //when
            CommentDto commentDto = commentService.findCommentById(anyLong());
            //then
            assertThat(commentDto.getId()).isEqualTo(1L);
            assertThat(commentDto.getContent()).isEqualTo("comment");
            System.out.println(objectMapper.writeValueAsString(commentDto));
        }

        @Test
        @DisplayName("NoSuchElementException 테스트")
        void exceptionTest(){
            given(commentRepository.findById(1L)).willReturn(Optional.empty());
            assertThatCode(()->commentService.findCommentById(1L))
                    .isInstanceOf(NoSuchElementException.class);

        }

    }

    @Nested
    @DisplayName("댓글 전체 조회 테스트")
    class 댓글_전체_조회{

        User user = User.builder().id(1L).userId("khw").build();
        Post post = Post.builder().id(1L).content("content").build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Comment comment1 = Comment.builder().id(1L).content("comment1").user(user).post(post).build();
        Comment comment2 = Comment.builder().id(2L).content("comment2").user(user).post(post).build();
        Comment comment3 = Comment.builder().id(3L).content("comment3").user(user).post(post).build();
        List<Comment> commentList = new ArrayList<>();

        @Test
        @WithMockUser(username = "1")
        @DisplayName("postId 게시글의 전체 댓글 조회 테스트")
        void findAllComment() throws JsonProcessingException {
            //given
            commentList.add(comment1);
            commentList.add(comment2);
            commentList.add(comment3);

            PageImpl<Comment> commentPageImpl = new PageImpl<>(commentList, pageRequest, commentList.size());
            given(commentRepository.findAllByPostId(1L, pageRequest)).willReturn(commentPageImpl);
            given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

            //when
            Page<Comment> commentPage = commentService.findAllByPostId(1L, pageRequest);
            //then
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commentPage));
            assertThat(commentPage.getTotalElements()).isEqualTo(commentPageImpl.getTotalElements());
        }

        @Test
        @DisplayName("postId 게시글 전체 댓글 조회 NoSuchElementException 테스트")
        void postIdExceptionTest(){
            given(postRepository.findById(1L)).willReturn(Optional.empty());
            assertThatCode(()->commentService.findAllByPostId(1L, pageRequest))
                    .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @WithMockUser(username = "1")
        @DisplayName("userId 유저가 작성한 댓글 전체 조회 테스트")
        void findAllCommentByUserId() throws JsonProcessingException {
            //given
            commentList.add(comment1);
            commentList.add(comment2);
            commentList.add(comment3);

            PageImpl<Comment> commentPageImpl = new PageImpl<>(commentList, pageRequest, commentList.size());
            given(commentRepository.findAllByUserId("khw", pageRequest)).willReturn(commentPageImpl);
            given(userRepository.findByUserId(anyString())).willReturn(Optional.of(user));

            //when
            Page<Comment> commentPage = commentService.findAllByUserId("khw", pageRequest);
            //then
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(commentPage));
            assertThat(commentPage.getTotalElements()).isEqualTo(commentPageImpl.getTotalElements());
        }

        @Test
        @DisplayName("userId 유저의 전체 댓글 조회 NoSuchElementException 테스트")
        void userIdExceptionTest(){
            given(userRepository.findByUserId("khw")).willReturn(Optional.empty());
            assertThatCode(()->commentService.findAllByUserId("khw", pageRequest))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

}