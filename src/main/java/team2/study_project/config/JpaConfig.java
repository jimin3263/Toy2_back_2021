package team2.study_project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import team2.study_project.repository.UserRepository;
import team2.study_project.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    private final UserRepository userRepository;

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of(UUID.randomUUID().toString());
    }


}
