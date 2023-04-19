package com.simpllyrun.srcservice.api.dto.follow;

import com.querydsl.core.annotations.QueryProjection;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "사용자 정보 DTO")
public class FollowerDto {
    private UserDto user;
    private boolean isFollowing;
    private boolean isFollower;
    private boolean isMe;

    @Builder
    public FollowerDto(UserDto user, boolean isFollowing, boolean isFollower, boolean isMe) {
        this.user = user;
        this.isFollowing = isFollowing;
        this.isFollower = isFollower;
        this.isMe = isMe;
    }


    @QueryProjection
    public FollowerDto(User user, boolean isFollowing, boolean isFollower, boolean isMe) {
        this.user = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
        this.isFollowing = isFollowing;
        this.isFollower = isFollower;
        this.isMe = isMe;
    }
}
