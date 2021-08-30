package team2.study_project.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow extends BasicClass{

    @Id @GeneratedValue
    @Column(name="follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Long followingId;

    @Builder
    public Follow(User user , Long followingId){
        this.user=user;
        this.followingId=followingId;
    }

}
