package team2.study_project.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import team2.study_project.domain.User;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Optional<Long> getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보 없음");
            return Optional.empty();
        }

        Long userId = null;

        if (authentication.getPrincipal() instanceof User) {
            User springSecurityUser = (User) authentication.getPrincipal();
            userId = springSecurityUser.getId();
        } else if (authentication.getPrincipal() instanceof Long) {
            userId = (Long) authentication.getPrincipal();
        }

        return Optional.ofNullable(userId);
    }
}
