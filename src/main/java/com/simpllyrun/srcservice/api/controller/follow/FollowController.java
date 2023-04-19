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
@Tag(name = "user-follows", description = "사용자 팔로우 API")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followUserId}")
    public ResponseEntity<Long> follow(@PathVariable Long followUserId) {
        var followId = followService.follow(followUserId);

        if (followId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(followId);
        }
    }

    @DeleteMapping("/{followId}")
    @Operation(summary = "언팔로우", description = "언팔로우")
    public ResponseEntity<Void> unfollow(@PathVariable @Validated @Schema(description = "내용")
                                             Long followId) {
        followService.unfollow(followId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{targetUserId}/followings")
    @Operation(summary = "팔로잉 목록 조회", description = "팔로잉 목록 조회")
    public ResponseEntity<List<FollowerDto>> getFollowings(@PathVariable @Validated
                                                  @Schema(description = "팔로잉 목록 조회할 사용자 ID")
                                                  String targetUserId) {
        return ResponseEntity.ok(followService.getFollowings(targetUserId));
    }

    @GetMapping("/{targetUserId}/followers")
    @Operation(summary = "팔로워 목록 조회", description = "팔로워 목록 조회")
    public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable @Validated @Schema(description = "내용")
                                              String targetUserId) {
        return ResponseEntity.ok(followService.getFollowers(targetUserId));
    }
}
