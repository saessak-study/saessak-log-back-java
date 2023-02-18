package saessak.log.post_media;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saessak.log.post.Post;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostMedia {

    @Id
    @GeneratedValue
    @Column(name = "media_idx")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private Post post;

    private String imageFile;

    private String postText;


}
