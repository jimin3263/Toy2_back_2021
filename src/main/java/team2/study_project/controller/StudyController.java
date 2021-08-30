package team2.study_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.study_project.dto.ResponseMessage;
import team2.study_project.dto.study.StudyListResponseDto;
import team2.study_project.dto.study.StudyRequestDto;
import team2.study_project.dto.study.StudyResponseDto;
import team2.study_project.service.StudyService;
import team2.study_project.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getStudyList() {
        Optional<Long> userId = userService.getUserId();
        List<StudyResponseDto> studyList = studyService.getStudyList(userId.get());

        StudyListResponseDto dto = StudyListResponseDto.builder()
                .studyList(studyList)
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> saveStudy(@RequestBody StudyRequestDto requestDto) {
        Optional<Long> userId = userService.getUserId();
        studyService.saveStudy(userId.get(), requestDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @PatchMapping("/{studyId}")
    public ResponseEntity<?> updateStudy(@PathVariable Long studyId,
                                         @RequestBody StudyRequestDto requestDto){
        studyService.updateContent(studyId, requestDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long studyId,
                                          @RequestParam boolean status){
        studyService.updateState(studyId,status);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<?> deleteStudy(@PathVariable Long studyId){
        Optional<Long> userId = userService.getUserId();
        studyService.delete(studyId,userId.get());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK,"ok"));
    }

}