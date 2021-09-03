package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team2.study_project.domain.User;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.jwt.JwtTokenProvider;
import team2.study_project.jwt.TokenDto;
import team2.study_project.repository.UserRepository;
import team2.study_project.service.UserService;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<ResponseMessage> join(@RequestBody Map<String, String> user) {
        userService.signUp(user);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

    // 로그인
    @PostMapping("/user/signin")
    public TokenDto login(@RequestBody Map<String, String> user) {
        return userService.getTokenDto(user);
    }

}
