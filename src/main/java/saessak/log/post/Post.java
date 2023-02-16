package saessak.log.post;

import lombok.*;
import saessak.log.BaseTimeEntity;
import saessak.log.comment.Comment;
import saessak.log.post_media.PostMedia;
import saessak.log.user.User;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "post")
    private PostMedia postMedia;

    @OneToMany(mappedBy = "post")
    private Comment comment;

    private Post(User user) {
        this.user = user;
    }

    public static Post from(User user) {
        return new Post(user);
    }

}
