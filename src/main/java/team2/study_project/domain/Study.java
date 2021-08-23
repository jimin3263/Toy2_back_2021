package team2.study_project.domain;



import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Study extends BasicClass {

    @Id @GeneratedValue
    @Column(name = "study_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String content;

    @Column
    private boolean status;

}
