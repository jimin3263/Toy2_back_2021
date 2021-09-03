package team2.study_project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team2.study_project.domain.Study;
import team2.study_project.domain.User;
import team2.study_project.dto.study.StudyRequestDto;
import team2.study_project.repository.StudyRepository;
import team2.study_project.repository.UserRepository;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudyServiceTest {

    @Autowired StudyRepository studyRepository;
    @Autowired StudyService studyService;
    @Autowired UserRepository userRepository;

    @Test
    public void save() throws Exception{
        //given
        StudyRequestDto study = createStudyDto("dd");
        User user = createUser("df@test.com", "df", "d");
        User savedUser = userRepository.save(user);

        //when
        Long storedId = studyService.saveStudy(savedUser.getId(), study);
        Optional<Study> findStudy = studyRepository.findById(storedId);

        //then
        assertThat(findStudy.get().getId()).isEqualTo(storedId);
    }
    
    @Test
    public void update() throws Exception{
        //given
        StudyRequestDto study = createStudyDto("dd");

        User user = createUser("dfd@test.com", "df", "d");
        User savedUser = userRepository.save(user);

        Long storedId = studyService.saveStudy(savedUser.getId(), study);
        
        //when
        StudyRequestDto updateRequestDto = StudyRequestDto.builder().content("hello").build();
        Long updatedId = studyService.updateContent(storedId, updateRequestDto);

        Optional<Study> updatedStudy = studyRepository.findById(updatedId);

        //then
        assertThat(updatedStudy.get().getContent()).isEqualTo("hello");
    }


    @Test
    public void 공부_상태_변경() throws Exception{
        //given
        StudyRequestDto dto = StudyRequestDto.builder()
                .content("공부 내용")
                .build();

        User user = createUser("ddd@test.com", "df", "d");
        User savedUser = userRepository.save(user);

        Long storedId = studyService.saveStudy(savedUser.getId(),dto);

        //when
        Long aLong = studyService.updateState(storedId, true); //true로 변경

        //then
        assertThat(studyRepository.findById(aLong).get().isStatus()).isTrue();
    }

    private User createUser(String email, String nickname, String password){
        User user = User.builder()
                .email(email)
                .username(nickname)
                .password(password)
                .build();
        return user;
    }

    private StudyRequestDto createStudyDto(String content){
        StudyRequestDto dto = StudyRequestDto.builder()
                .content(content)
                .build();

        return dto;
    }

}