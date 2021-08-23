package team2.study_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.study_project.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long>{
}
