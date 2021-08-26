package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team2.study_project.domain.User;
import team2.study_project.dto.UserDto;
import team2.study_project.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/user/signup")
    public String create(@RequestBody UserDto newUser) {

        String email = newUser.getEmail();
        String password = newUser.getPassword();
        String username = newUser.getUsername();

        if(username.equals("") || password.equals("") || username.equals(""))
            return "failed";

        User user = User.builder().username(username).email(email).password(password).build();

        return userService.validateEmail(user);
    }

}
