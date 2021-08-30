package team2.study_project.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import team2.study_project.domain.User;
import org.springframework.test.annotation.Rollback;
import team2.study_project.domain.Study;


import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class StudyRepositoryTest {

    @Autowired
    StudyRepository studyRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    public void 공부목록조회() throws Exception{
        //given
        User user = User.builder()
                .password("dd")
                .email("dd")
                .username("zz")
                .build();

        userRepository.save(user);

        Study study1 = Study.builder()
                .user(user)
                .status(false)
                .content("테스트111")
                .build();

        studyRepository.save(study1);

        Study study2 = Study.builder()
                .user(user)
                .status(false)
                .content("테스트222")
                .build();

        studyRepository.save(study2);

        //when
        List<Study> studyList = studyRepository.findByUserId(user.getId());

        //then
        assertThat(studyList.size()).isEqualTo(2);
    }

    @Test
    public void delete() throws Exception{
        //given
        Study study1 = Study.builder().
                status(false)
                .content("삭")
                .build();

        Study study2 = Study.builder().
                status(false)
                .content("제")
                .build();
        
        //when
        Study storedStudy1 = studyRepository.save(study1);
        Study storedStudy2 = studyRepository.save(study2);
        Long id = storedStudy1.getId();
        studyRepository.delete(storedStudy1);

        //then
        assertThat(studyRepository.findById(id)).isEmpty();
    }
}