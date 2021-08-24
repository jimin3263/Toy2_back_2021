package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Study;
import team2.study_project.domain.User;

public interface UserRepository  extends JpaRepository<User, Long> {
}
