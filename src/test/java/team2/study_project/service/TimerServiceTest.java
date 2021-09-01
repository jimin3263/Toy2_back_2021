package team2.study_project.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team2.study_project.domain.Timer;
import team2.study_project.domain.User;
import team2.study_project.repository.StudyRepository;
import team2.study_project.repository.TimerRepository;
import team2.study_project.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TimerServiceTest {

    @Autowired StudyRepository studyRepository;
    @Autowired StudyService studyService;
    @Autowired UserRepository userRepository;
    @Autowired TimerService timerService;
    @Autowired
    TimerRepository timerRepository;


    @Test
    public void 시간계산테스트() throws Exception{
        //given
        User dd = createMember("time@test.com", "dd", "3443");
        //when
        timerService.saveTime(dd.getId(),"00:10:01");
        timerService.saveTime(dd.getId(), "00:33:02");
        LocalDate today = LocalDate.now();
        Optional<Timer> byUserIdAndCreatedDate = timerRepository.findByUserIdAndCreatedDate(dd.getId(), today);

        //then
        Assertions.assertThat(byUserIdAndCreatedDate.get().getTime()).isEqualTo("00:43:03");
    }

    private User createMember(String email, String nickname, String password){
        User user = User.builder()
                .email(email)
                .username(nickname)
                .password(password)
                .build();

        User save = userRepository.save(user);
        return save;
    }

}