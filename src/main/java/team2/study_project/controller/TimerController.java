package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.dto.timer.TimerRequestDto;
import team2.study_project.dto.timer.TimerResponseDto;
import team2.study_project.jwt.SecurityUtil;
import team2.study_project.service.TimerService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class TimerController {

    private final TimerService timerService;

    @PatchMapping
    public ResponseEntity<?> updateStatus(@RequestParam boolean status){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        timerService.updateTimeState(userId.get(),status);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @PostMapping
    public ResponseEntity<?> saveTimer(@RequestBody TimerRequestDto dto){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        timerService.saveTime(userId.get(),dto.getTime());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @GetMapping
    public ResponseEntity<?> getTimer(){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        TimerResponseDto time = timerService.getTime(userId.get());
        return ResponseEntity.ok(time);
    }
}
