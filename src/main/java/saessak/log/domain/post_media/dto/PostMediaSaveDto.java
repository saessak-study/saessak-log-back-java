package saessak.log.domain.post_media.dto;

import lombok.Getter;
import lombok.Setter;
import saessak.log.domain.post.Post;
import saessak.log.domain.post_media.PostMedia;

@Getter
@Setter
public class PostMediaSaveDto {

    private Post post;

    private String imageFile;

    private String postText;

    public PostMediaSaveDto(String imageFile, String postText) {
        this.imageFile = imageFile;
        this.postText = postText;
    }

    public PostMedia toEntity() {
        return PostMedia.of(post, imageFile, postText);
    }
}
