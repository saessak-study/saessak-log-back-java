package saessak.log.post_media.dto;

import lombok.Getter;
import lombok.Setter;
import saessak.log.post.Post;
import saessak.log.post_media.PostMedia;

@Getter
public class PostMediaSaveDto {

    private String imageFile;

    private String postText;

    public PostMediaSaveDto(String imageFile, String postText) {
        this.imageFile = imageFile;
        this.postText = postText;
    }

    public PostMedia toEntity(Post post) {
        return PostMedia.of(post, imageFile, postText);
    }
}
