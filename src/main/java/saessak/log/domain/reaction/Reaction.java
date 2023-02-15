package saessak.log.domain.reaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saessak.log.domain.post.Post;
import saessak.log.domain.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reaction {

    @Id
    @GeneratedValue
    @Column(name = "reaction_idx")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private Post post;
}
