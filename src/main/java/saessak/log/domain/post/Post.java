package saessak.log.domain.post;

import lombok.*;
import saessak.log.domain.BaseTimeEntity;
import saessak.log.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_idx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;


}
