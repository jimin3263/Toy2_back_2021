package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Study;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long>{

    List<Study> findByUserId(Long UserId);
}
