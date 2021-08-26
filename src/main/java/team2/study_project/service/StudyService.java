package team2.study_project.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team2.study_project.domain.Study;
import team2.study_project.domain.User;
import team2.study_project.dto.StudyUpdateRequestDto;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.StudyRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    //공부 목록 생성
    public Long saveStudy(Study study){
        studyRepository.save(study);
        return study.getId();
    }

    //조회
    public List<Study> getStudyList(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        List<Study> studyList = studyRepository.findByUserId(userId);
        return studyList;
    }

    //공부 내용 수정
    @Transactional
    public Long updateContent(Long studyId, StudyUpdateRequestDto updateDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        study.update(updateDto.getContent());

        return studyId;
    }

    //공부 상태 변경
    @Transactional
    public Study updateState(Long studyId, Boolean status) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        study.statusUpdate(status);
        return studyRepository.save(study);
    }

    //삭제
    @Transactional
    public void delete(Long studyId){
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new StudyException(ErrorEnum.STUDY_NOT_FOUND));

        studyRepository.delete(study);
    }



}
