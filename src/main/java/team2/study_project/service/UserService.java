package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.User;
import team2.study_project.jwt.JwtTokenProvider;
import team2.study_project.jwt.TokenDto;
import team2.study_project.repository.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 이메일 검증
     */
    @Transactional
    public String validateEmail(User user) {

        String email = user.getEmail();

        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent())
            return "failed";

        userRepository.save(user);
        return "success";
    }

    @Transactional
    public void signUp(Map<String, String> user) {
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
                .studyStatus(false)
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
    }

    public TokenDto getTokenDto(Map<String, String> user) {
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
