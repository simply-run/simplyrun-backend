package com.simpllyrun.srcservice.api.controller.follow;

import com.simpllyrun.srcservice.api.dto.follow.FollowerDto;
import com.simpllyrun.srcservice.api.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
@Tag(name = "follows", description = "사용자 팔로우 API")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followUserId}")
    @Operation(summary = "팔로우", description = "userId가 아닌 seq로 사용하는 id를 url path로 받습니다.")
    public ResponseEntity<Long> follow(@PathVariable @Schema(description = "팔로우 할 사용자 seq") Long followUserId) {
        var followId = followService.follow(followUserId);

        if (followId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(followId);
        }
    }

    @DeleteMapping("/{followId}")
    @Operation(summary = "언팔로우", description = "userId가 아닌 seq로 사용하는 id를 url path로 받습니다.")
    public ResponseEntity<Void> unfollow(@PathVariable @Validated @Schema(description = "언팔로우 할 사용자 seq")
                                         Long followId) {
        followService.unfollow(followId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{targetUserId}/followings")
    @Operation(summary = "팔로잉 목록 조회", description = "자기 자신은 팔로잉, 팔로워에 포함되지 않습니다.")
    public ResponseEntity<List<FollowerDto>> getFollowings(@PathVariable @Validated
                                                           @Schema(description = "팔로잉 목록 조회할 사용자 ID")
                                                           String targetUserId) {
        return ResponseEntity.ok(followService.getFollowings(targetUserId));
    }

    @GetMapping("/{targetUserId}/followers")
    @Operation(summary = "팔로워 목록 조회", description = "자기 자신은 팔로잉, 팔로워에 포함되지 않습니다.")
    public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable @Validated
                                                          @Schema(description = "팔로워 목록 조회할 사용자 ID")
                                                          String targetUserId) {
        return ResponseEntity.ok(followService.getFollowers(targetUserId));
    }
}
