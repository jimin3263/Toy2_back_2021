package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.Follow;
import team2.study_project.domain.User;
import team2.study_project.exception.*;
import team2.study_project.repository.FollowRepository;
import team2.study_project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        user.getFollowList().add(follow);

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
    public List<User> getFollowList(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new StudyException(ErrorEnum.USER_NOT_FOUND));

        //List<Follow> followList1 = user.getFollowList();
        List<Follow> followList = followRepository.findByUser(user);

        List<User> followingList = new ArrayList<>();
        for (Follow follow : followList) {
            Optional<User> followerUser = userRepository.findById(follow.getFollowingId());
            followingList.add(followerUser.get());
        }

        return followingList;
    }

    //친구 검색
    /*
    public List<User> findUser(String nickname){
        return userRepository.findByUsername(nickname);
    }
     */

}
