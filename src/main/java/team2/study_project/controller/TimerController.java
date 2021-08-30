package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.dto.timer.TimerRequestDto;
import team2.study_project.service.TimerService;
import team2.study_project.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class TimerController {

    private final TimerService timerService;
    private final UserService userService;

    @PutMapping
    public ResponseEntity<?> updateStatus(@RequestParam boolean status){
        Optional<Long> userId = userService.getUserId();
        timerService.updateTimeState(userId.get(),status);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @PostMapping
    public ResponseEntity<?> saveTimer(@RequestBody TimerRequestDto dto){
        Optional<Long> userId = userService.getUserId();
        timerService.saveTime(userId.get(),dto.getTime());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

}
