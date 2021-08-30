package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.Timer;
import team2.study_project.domain.User;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.TimerRepository;
import team2.study_project.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimerService {

    private final UserRepository userRepository;
    private final TimerRepository timerRepository;

    //타이머 시간 저장
    @Transactional
    public void saveTime(Long userId, String time){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        Timer timer = Timer.builder()
                .user(user)
                .time(time)
                .build();

        timerRepository.save(timer);
    }

    //타이머 시작, 끝 상태 저장
    @Transactional
    public void updateTimeState(Long userId, boolean timeStatus){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        user.updateTimeStatue(timeStatus);
    }

}
