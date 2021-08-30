package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.dto.mypage.MyPageRequestDto;
import team2.study_project.dto.mypage.MyPageResponseDto;
import team2.study_project.service.MyPageService;
import team2.study_project.service.UserService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getMyPage() {
        Optional<Long> userId = userService.getUserId();
        MyPageResponseDto myPage = myPageService.getMyPage(userId.get());
        return ResponseEntity.ok(myPage);
    }

    @PatchMapping
    public ResponseEntity<?> updateMyPage(@RequestBody MyPageRequestDto myPageRequestDto){
        Optional<Long> userId = userService.getUserId();
        myPageService.updateMyPage(userId.get(), myPageRequestDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

}
