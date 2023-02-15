package saessak.log.domain.post_media;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saessak.log.domain.post.Post;

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

    @Builder
    private PostMedia(Post post, String imageFile, String postText) {
        this.post = post;
        this.imageFile=imageFile;
        this.postText = postText;
    }

    public static PostMedia of(Post post, String imageFile, String postText) {
        return PostMedia.builder()
            .post(post)
            .imageFile(imageFile)
            .postText(postText)
            .build();
    }
}
