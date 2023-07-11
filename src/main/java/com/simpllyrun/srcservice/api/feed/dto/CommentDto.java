package com.simpllyrun.srcservice.api.feed.dto;

import com.simpllyrun.srcservice.api.feed.domain.Comment;
import com.simpllyrun.srcservice.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "댓글 DTO")
public class CommentDto {

    @Schema(description = "댓글 아이디(번호)")
    private Long id;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "댓글 생성한 사용자")
    private UserDto user;

    @Schema(description = "댓글 생성일")
    private LocalDateTime dateCreated;

    @Schema(description = "댓글 수정일")
    private LocalDateTime dateUpdated;

    public static CommentDto of(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(UserDto.of(comment.getUser()))
                .dateCreated(comment.getDateCreated())
                .dateUpdated(comment.getDateUpdated())
                .build();
    }
}
