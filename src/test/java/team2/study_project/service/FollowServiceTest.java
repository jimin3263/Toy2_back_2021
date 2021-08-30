package team2.study_project.service;

import com.mysql.cj.jdbc.MysqlParameterMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team2.study_project.domain.Follow;
import team2.study_project.domain.User;
import team2.study_project.exception.ErrorEnum;
import team2.study_project.exception.StudyException;
import team2.study_project.repository.FollowRepository;
import team2.study_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowServiceTest {

    @Autowired FollowService followService;
    @Autowired FollowRepository followRepository;
    @Autowired UserRepository userRepository;
    @Autowired MyPageService myPageService;

    @Test
    public void 팔로우_추가() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12df");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        Long followId = followService.follow(user1.getId(),user2.getId()); //user2를 팔로우함
        Optional<Follow> byId = followRepository.findById(followId);

        //then
        assertThat(byId.get().getFollowingId()).isEqualTo(user2.getId());
        assertThat(byId.get().getUser().getId()).isEqualTo(user1.getId());
    }

    @Test
    public void 팔로우추가_예외() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12df");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        followService.follow(user1.getId(),user2.getId());

        //then
        StudyException thrown = assertThrows(StudyException.class, () -> followService.follow(user1.getId(),user2.getId()));
        assertEquals(ErrorEnum.ALREADY_FOLLOW, thrown.getErrorEnum());
       // fail("이미 팔로우되어 있다고 예외 발생해야 함");
    }

    @Test
    public void 팔로우리스트확인() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12df");
        User user3 = createMember("test3@test.com","녕녕","12d34");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        followService.follow(user1.getId(),user2.getId());
        followService.follow(user1.getId(),user3.getId());
        List<User> followList = followService.getFollowList(user1.getId());

        //then
        assertThat(followList.size()).isEqualTo(2);
    }
    @Test
    public void 팔로워수_확인() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12df");
        User user3 = createMember("test3@test.com","녕녕","12d34");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        followService.follow(user1.getId(),user2.getId());
        followService.follow(user3.getId(),user2.getId());

        //when
        int user1Follower = myPageService.getFollowerCount(user1.getId());
        int user2Follower = myPageService.getFollowerCount(user2.getId());

        //then
        assertThat(user1Follower).isEqualTo(0);
        assertThat(user2Follower).isEqualTo(2);
    }

    @Test
    public void 팔로우_취소_예외() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12334");

        userRepository.save(user1);
        userRepository.save(user2);

        //then
        StudyException thrown = assertThrows(StudyException.class, () -> followService.deleteFollow(user1.getId(), user2.getId()));
        assertEquals(ErrorEnum.FOLLOW_NOT_FOUND, thrown.getErrorEnum());
        //fail("팔로우가 되어 있지 않다는 예외 발생해야함");
    }

    @Test
    public void 팔로우_취소() throws Exception{
        //given
        User user1 = createMember("test1@test.com","안녕","1234");
        User user2 = createMember("test2@test.com","녕녕","12334");

        userRepository.save(user1);
        userRepository.save(user2);

        Long followId = followService.follow(user1.getId(), user2.getId());

        //when
        followService.deleteFollow(user1.getId(), user2.getId());

        //then
        assertThat(followRepository.findById(followId)).isEmpty();
    }

    private User createMember(String email, String nickname, String password){
        User user = User.builder()
                .email(email)
                .username(nickname)
                .password(password)
                .build();
        return user;
    }
}