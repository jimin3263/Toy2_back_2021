package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team2.study_project.domain.User;
import team2.study_project.repository.UserRepository;
import team2.study_project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {


    /*UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public String createForm() {
        return "user/signup";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/user/signup")
    @ResponseBody
    public String create(@RequestBody User newUser) {

        String username = newUser.getUsername();
        String password = newUser.getPassword();
        String nickname = newUser.getNickname();

        if(username.equals("") || password.equals("") || nickname.equals(""))
            return "failed";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);

        return userService.validateUsername(username, user);
    }




    /*@GetMapping("/users/{username}/email-check")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkEmailDuplicate(username));
    }*/

}
