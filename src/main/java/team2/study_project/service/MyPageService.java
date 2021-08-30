package team2.study_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team2.study_project.domain.User;
import team2.study_project.dto.mypage.MyPageRequestDto;
import team2.study_project.dto.mypage.MyPageResponseDto;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.FollowRepository;
import team2.study_project.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    //프로필 조회
    public MyPageResponseDto getMyPage(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new StudyException(ErrorEnum.USER_NOT_FOUND));

        int followerCount = getFollowerCount(userId);
        int followingCount = user.getFollowList().size();

        MyPageResponseDto dto = MyPageResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getUsername())
                .follower(followerCount)
                .following(followingCount)
                .build();

        return dto;
    }

    //팔로워 수 카운트
    public int getFollowerCount(Long userId){
        return followRepository.countByFollowingId(userId);
    }

    //프로필 수정
    @Transactional
    public void updateMyPage(long userId, MyPageRequestDto myPageRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new StudyException(ErrorEnum.USER_NOT_FOUND));

        user.update(myPageRequestDto.getNickname());
    }

}
