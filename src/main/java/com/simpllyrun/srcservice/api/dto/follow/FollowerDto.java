package com.simpllyrun.srcservice.api.dto.follow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import com.simpllyrun.srcservice.api.domain.User;
import com.simpllyrun.srcservice.api.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "팔로워 정보 DTO")
public class FollowerDto {
    private UserDto user;
    @JsonProperty(value="isFollowing")
    @Schema(description = "팔로잉 여부")
    private boolean isFollowing;
    @JsonProperty(value="isFollower")
    @Schema(description = "팔로워 여부")
    private boolean isFollower;
    @JsonProperty(value="isMe")
    @Schema(description = "자신 여부")
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
