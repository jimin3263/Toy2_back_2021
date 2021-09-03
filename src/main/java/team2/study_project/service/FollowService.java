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
import team2.study_project.repository.TimerRepository;
import team2.study_project.repository.UserRepository;

import java.time.LocalDate;
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
    private final TimerRepository timerRepository;

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

    private FollowResponseDto getFollowResponseDto(Follow follow) {
        Optional<User> followingUser = userRepository.findById(follow.getFollowingId());
        User fUser = followingUser.get();
        Optional<Timer> userTime = getTimer(fUser);

        String accumulatedTime = "00:00:00";
        if(userTime.isPresent()){
            accumulatedTime = userTime.get().getTime();
        }

        FollowResponseDto dto = FollowResponseDto.builder()
                .status(fUser.getStudyStatus())
                .username(fUser.getUsername())
                .userId(fUser.getId())
                .accumulatedTime(accumulatedTime)
                .achievementRate(calculateRate(fUser.getStudyList()))
                .build();
        return dto;
    }

    private Optional<Timer> getTimer(User fUser) {
        LocalDate today = LocalDate.now();
        Optional<Timer> accumulatedTime = timerRepository.findByUserIdAndCreatedDate(fUser.getId(), today);
        return accumulatedTime;
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
