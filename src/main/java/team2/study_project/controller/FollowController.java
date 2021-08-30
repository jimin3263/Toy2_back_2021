package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.dto.follow.FollowListResponseDto;
import team2.study_project.dto.follow.FollowResponseDto;
import team2.study_project.dto.user.FindUserDto;
import team2.study_project.dto.user.FindUserListDto;
import team2.study_project.service.FollowService;
import team2.study_project.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getFollowList() {
        Optional<Long> userId = userService.getUserId();

        List<FollowResponseDto> followList = followService.getFollowList(userId.get());

        FollowListResponseDto dto = FollowListResponseDto.builder()
                .followList(followList)
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<?> saveStudy(@PathVariable Long memberId) {
        Optional<Long> userId = userService.getUserId();
        followService.follow(userId.get(), memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long memberId){
        Optional<Long> userId = userService.getUserId();
        followService.deleteFollow(userId.get(), memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

    @GetMapping("/search")
    public ResponseEntity searchUser(@RequestParam String name){
        List<FindUserDto> userList = followService.findUser(name);
        FindUserListDto dto = FindUserListDto.builder()
                .userList(userList)
                .build();

        return ResponseEntity.ok(dto);
    }
}