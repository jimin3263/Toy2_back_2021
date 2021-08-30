package team2.study_project.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team2.study_project.domain.Study;
import team2.study_project.domain.User;
import team2.study_project.dto.study.StudyRequestDto;
import team2.study_project.dto.study.StudyResponseDto;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.StudyRepository;

import org.springframework.transaction.annotation.Transactional;
import team2.study_project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {

    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    //공부 목록 생성
    @Transactional
    public Long saveStudy(Long userId, StudyRequestDto dto){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        Study study = Study.builder()
                .status(false)
                .content(dto.getContent())
                .user(user)
                .build();

        Study savedStudy = studyRepository.save(study);

        return savedStudy.getId();
    }

    //조회
    public List<StudyResponseDto> getStudyList(Long userId){
        userRepository.findById(userId)
                .orElseThrow(()->new StudyException(ErrorEnum.USER_NOT_FOUND));

        List<StudyResponseDto> studyResponse =  new ArrayList<>();

        List<Study> studyList = studyRepository.findByUserId(userId);
        for (Study study : studyList) {
            StudyResponseDto dto = StudyResponseDto.builder()
                    .status(study.isStatus())
                    .content(study.getContent())
                    .studyId(study.getId())
                    .build();
            studyResponse.add(dto);
        }
        return studyResponse;
    }

    //공부 내용 수정
    @Transactional
    public Long updateContent(Long studyId, StudyRequestDto updateDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        study.update(updateDto.getContent());
        return studyId;
    }

    //공부 상태 변경
    @Transactional
    public Long updateState(Long studyId, Boolean status) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        study.statusUpdate(status);
        return studyId;
    }

    //삭제
    @Transactional
    public void delete(Long studyId){
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        studyRepository.delete(study);
    }

}
