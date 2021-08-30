package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.Follow;
import team2.study_project.domain.Study;
import team2.study_project.domain.Timer;
import team2.study_project.domain.User;
import team2.study_project.dto.follow.FollowResponseDto;
import team2.study_project.dto.user.FindUserDto;
import team2.study_project.exception.*;
import team2.study_project.repository.FollowRepository;
import team2.study_project.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    //팔로우
    @Transactional
    public Long follow(Long userId, Long followingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        userRepository.findById(followingId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        if(userId.equals(followingId)){
            throw new StudyException(ErrorEnum.REJECTED_FOLLOW);
        }

        Optional<Follow> followStatus = followRepository.findByUserAndFollowingId(user, followingId);
        if (followStatus.isPresent()){
            throw new StudyException(ErrorEnum.ALREADY_FOLLOW);
        }

        Follow follow = Follow.builder()
                .user(user)
                .followingId(followingId)
                .build();

        Follow followId = followRepository.save(follow);

        return followId.getId();
    }

    //언팔
    @Transactional
    public void deleteFollow(Long userId, Long followingId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        followRepository.findByUserAndFollowingId(user, followingId)
               .orElseThrow(()-> new StudyException(ErrorEnum.FOLLOW_NOT_FOUND));

        followRepository.deleteByUserAndFollowingId(user, followingId);
    }

    //팔로우 리스트 확인
    public List<FollowResponseDto> getFollowList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        List<FollowResponseDto> followingList = new ArrayList<>();
        List<Follow> followList = user.getFollowList();

        for (Follow follow : followList) {
            FollowResponseDto dto = getFollowResponseDto(follow);
            followingList.add(dto);
        }

        return followingList;
    }

    private FollowResponseDto getFollowResponseDto(Follow follow) {
        Optional<User> followingUser = userRepository.findById(follow.getFollowingId());
        User fUser = followingUser.get();
        FollowResponseDto dto = FollowResponseDto.builder()
                .status(fUser.getStudyStatus())
                .username(fUser.getUsername())
                .userId(fUser.getId())
                .accumulatedTime(calculateTime(fUser.getTimer()))
                .achievementRate(calculateRate(fUser.getStudyList()))
                .build();
        return dto;
    }

    //친구 검색
    public List<FindUserDto> findUser(String username){
        List<User> users = userRepository.findByUsernameContaining(username);
        List<FindUserDto> userList = new ArrayList<>();

        for (User user : users) {
            FindUserDto dto = FindUserDto.builder()
                    .username(user.getUsername())
                    .userId(user.getId())
                    .email(user.getEmail())
                    .build();

            userList.add(dto);
        }
        return userList;
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

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month  = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<Integer> stringToInt = new ArrayList<>();

        for (Timer timer : timerList) {
            String time = timer.getTime();
            LocalDateTime createdTime = timer.getCreatedTime();
            if (createdTime.getYear() == year && createdTime.getMonthValue() == month && createdTime.getDayOfMonth() == day){
                String[] split = time.split(":");
                for (String s : split) {
                    Integer integer = Integer.valueOf(s);
                    stringToInt.add(integer);
                }
            }
        }
        return stringToInt;
    }

    private double calculateRate(List<Study> studyList){
        int done = 0;
        for (Study study : studyList) {
            if(study.isStatus()){
                done+=1;
            }
        }
        if(studyList.size()==0){
            return 0;
        }
        double rate = ((double) done/studyList.size())*100;
        rate = Math.round(rate*100)/100.0;
        return rate;
    }

}
