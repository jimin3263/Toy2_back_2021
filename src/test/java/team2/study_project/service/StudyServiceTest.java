package team2.study_project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import team2.study_project.domain.Study;
import team2.study_project.dto.StudyUpdateRequestDto;
import team2.study_project.repository.StudyRepository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyServiceTest {

    @Autowired StudyRepository studyRepository;
    @Autowired StudyService studyService;
    @Autowired EntityManager em;

    @Test
    public void save() throws Exception{
        //given
        Study study = Study.builder().
                status(false)
                .content("테스트")
                .build();

        //when
        Long storedStudyId = studyService.saveStudy(study);
        Optional<Study> findStudy = studyRepository.findById(storedStudyId);

        //then
        assertThat(findStudy.get().getId()).isEqualTo(study.getId());
    }
    
    @Test
    @Rollback(value = false)
    public void update() throws Exception{
        //given
        Study study = Study.builder().
                status(false)
                .content("수정 테스트")
                .build();

        Long storedId = studyService.saveStudy(study);
        
        //when

        StudyUpdateRequestDto updateRequestDto = StudyUpdateRequestDto.builder().content("hello").build();
        Long updatedId = studyService.updateContent(storedId, updateRequestDto);

        Optional<Study> updatedStudy = studyRepository.findById(updatedId);

        System.out.println(storedId);

        //then
        assertThat(updatedStudy.get().getContent()).isEqualTo("hello");
    }



}