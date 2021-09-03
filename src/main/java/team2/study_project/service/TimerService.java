package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.Timer;
import team2.study_project.domain.User;
import team2.study_project.dto.timer.TimerResponseDto;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.TimerRepository;
import team2.study_project.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimerService {

    private final UserRepository userRepository;
    private final TimerRepository timerRepository;

    //누적 시간 가져오기
    public TimerResponseDto getTime(Long userId){

        LocalDate today = LocalDate.now();
        Optional<Timer> userTimer = timerRepository.findByUserIdAndCreatedDate(userId, today);

        String accumulatedTime = "00:00:00";
        if(userTimer.isPresent()){
            accumulatedTime = userTimer.get().getTime();
        }

        TimerResponseDto dto = TimerResponseDto
                .builder()
                .time(accumulatedTime)
                .build();

        return dto;
    }

    //타이머 누적 시간 저장
    @Transactional
    public void saveTime(Long userId, String time){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        Timer timer = Timer.builder()
                .user(user)
                .time(time)
                .build();

        Optional<Timer> userTimer = timerRepository.findByUserIdAndCreatedDate(userId, LocalDate.now());
        if(userTimer.isPresent()){
            List<Timer> timerList = new ArrayList<>();
            timerList.add(userTimer.get());
            timerList.add(timer);
            String calculateTime = calculateTime(timerList);
            userTimer.get().timerUpdate(calculateTime);
        } else {
            timerRepository.save(timer);
        }

    }

    //타이머 시작, 끝 상태 저장
    @Transactional
    public void updateTimeState(Long userId, boolean timeStatus){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        user.updateTimeStatue(timeStatus);
    }


    private String calculateTime(List<Timer> timerList) {

        List<Integer> stringToInt = getIntegers(timerList);
        Integer hour = 0; Integer min = 0; Integer sec = 0;

        for(int i =0; i< stringToInt.size(); i++){
            if(i%3 == 0) {
                hour += stringToInt.get(i);
            }
            else if(i%3 ==1 ){
                min += stringToInt.get(i);
                if(min > 59){
                    hour +=1; min = 0;
                }
            }
            else {
                sec += stringToInt.get(i);
                if(sec > 59){
                    min +=1; sec = 0;
                }
            }
        }
        return String.format("%1$02d:%2$02d:%3$02d",hour,min,sec);
    }

    private List<Integer> getIntegers(List<Timer> timerList) {

        List<Integer> stringToInt = new ArrayList<>();

        for (Timer timer : timerList) {
            String time = timer.getTime();
            String[] split = time.split(":");
            for (String s : split) {
                Integer integer = Integer.valueOf(s);
                stringToInt.add(integer);
            }

        }
        return stringToInt;
    }

}
