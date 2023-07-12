package com.simpllyrun.srcservice.api.feed.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.feed.domain.PostImage;
import com.simpllyrun.srcservice.api.user.domain.User;
import com.simpllyrun.srcservice.api.user.dto.UserDto;
import com.simpllyrun.srcservice.api.feed.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "게시글 전체 정보 DTO")
public class PostDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Schema(description = "게시글 생성에 필요한 내용")
    public static class PostRequestDto {

        @NotNull
        @Schema(description = "게시글 제목", requiredMode = Schema.RequiredMode.REQUIRED)
        private String title;

        @NotNull
        @Schema(description = "게시글 내용", requiredMode = Schema.RequiredMode.REQUIRED)
        private String content;
        @NotNull
        @Schema(description = "게시글 카테고리", requiredMode = Schema.RequiredMode.REQUIRED)
        private Post.CategoryEnum category;

        @Schema(description = "사진이나 동영상")
        private List<MultipartFile> multipartFiles;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PostResponseDto {
        @Schema(description = "게시글 아이디(번호)", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long postId;

        @NotNull
        @Schema(description = "게시글 작성자", requiredMode = Schema.RequiredMode.REQUIRED)
        private UserDto user;

        @NotNull
        @Schema(description = "게시글 제목", requiredMode = Schema.RequiredMode.REQUIRED)
        private String title;

        @NotNull
        @Schema(description = "게시글 내용", requiredMode = Schema.RequiredMode.REQUIRED)
        private String content;

        @NotNull
        @Schema(description = "게시글 카테고리", requiredMode = Schema.RequiredMode.REQUIRED)
        private Post.CategoryEnum category;

        @Schema(description = "게시글 생성일", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime dateCreated;

        @Schema(description = "게시글 수정일", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime dateUpdated;

        @Schema(description = "게시글 이미지", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<PostImageDto> postImages;

        @Schema(description = "게시글 댓글", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<CommentDto> recentComments;

        @Schema(description = "게시글 댓글 수", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long commentsCount;

        @Schema(description = "게시글 좋아요 수", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long postLikesCount;

        @QueryProjection
        public PostResponseDto(Long postId, User user, String title, String content, Post.CategoryEnum category,
                               LocalDateTime dateCreated, LocalDateTime dateUpdated, Integer commentsCount,
                               Integer postLikesCount) {
            this.postId = postId;
            this.user = UserDto.of(user);
            this.title = title;
            this.content = content;
            this.category = category;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
            this.commentsCount = commentsCount.longValue();
            this.postLikesCount = postLikesCount.longValue();
        }

        public static PostResponseDto of(Post post) {

            PostResponseDto postDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .category(post.getCategoryType())
                    .user(UserDto.of(post.getUser()))
                    .build();

            List<PostImage> postImages = post.getPostImages();
            List<PostImageDto> postImageList = postImages.stream().map(PostImageDto::of).collect(Collectors.toList());

            List<Comment> comments = post.getComments();
            List<CommentDto> commentDtoList = comments.stream().map(CommentDto::of).collect(Collectors.toList());

            postDto.setRecentComments(commentDtoList);
            postDto.setPostImages(postImageList);
            postDto.setCommentsCount((long) comments.size());
            postDto.setPostLikesCount((long) post.getPostLikes().size());
            postDto.setDateCreated(post.getDateCreated());
            postDto.setDateUpdated(post.getDateUpdated());
            return postDto;

        }
    }
}
