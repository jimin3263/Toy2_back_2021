package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Timer;

public interface TimerRepository extends JpaRepository<Timer, Long> {
}
