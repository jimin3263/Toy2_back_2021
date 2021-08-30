package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Follow;
import team2.study_project.domain.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FollowRepository  extends JpaRepository<Follow, Long> {
    List<Follow> findByUser(User user);

    Optional<Follow> findByUserAndFollowingId(User user, Long followingId);

    int countByFollowingId(Long followingId);

    @Transactional
    void deleteByUserAndFollowingId(User user, Long followingId);
}
