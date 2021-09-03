package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Timer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimerRepository extends JpaRepository<Timer, Long> {

    Optional<Timer> findByUserIdAndCreatedDate(Long UserId, LocalDate today);
}
