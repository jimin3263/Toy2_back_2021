package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team2.study_project.domain.User;
import team2.study_project.dto.user.UserDto;
import team2.study_project.jwt.JwtTokenProvider;
import team2.study_project.jwt.TokenDto;
import team2.study_project.repository.UserRepository;
import team2.study_project.service.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/user/signup")
    public String join(@RequestBody Map<String, String> user) {

        String email = user.get("email");
        String password = user.get("password");
        String username = user.get("username");


        if(email.equals("") || password.equals("") || username.equals("")) {
            throw new IllegalArgumentException("빈칸 없이 정확한 값을 입력해주세요.");
        }

        userRepository.findByEmail(user.get("email"))
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });


        userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .username((user.get("username")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
        return "계정생성을 성공했습니다.";
    }

    // 로그인
    @PostMapping("/user/signin")
    public TokenDto login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
        TokenDto tokenDto = TokenDto.builder()
                .token(token)
                .build();

        return tokenDto;
    }

}
