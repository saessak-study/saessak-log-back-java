package saessak.log.user.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long id;
    private String profileId;
    private String password;
    private String name;
    private String email;

    @Builder
    public User(Long id, String profileId, String password, String name, String email) {
        this.id = id;
        this.profileId = profileId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    protected User(){}
}
