package team2.study_project.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BasicClass implements UserDetails  {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Timer> timer = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Study> studyList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Follow> followList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
