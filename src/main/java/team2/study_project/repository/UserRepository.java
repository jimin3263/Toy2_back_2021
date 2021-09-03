package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByUsernameContaining(String username);
}
