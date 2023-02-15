package saessak.log.domain.user;

import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_idx")
    private Long id;

    private String profileId;

    private String password;

    private String name;

    private String email;

}
