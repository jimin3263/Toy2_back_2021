package team2.study_project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.User;
import team2.study_project.repository.UserRepository;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입
     */
    public void join(User user) {

        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
    }

    private void validateDuplicateMember(User user) {


        userRepository.findByUsername(user.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

   /* public boolean checkEmailDuplicate(String username) {
        return userRepository.existsByEmail(username);
    }*/

    public String validateUsername(String username, User user) {
        if(userRepository.findByUsername(username) != null)
            return "failed";

        userRepository.save(user);
        return "success";
    }


}
