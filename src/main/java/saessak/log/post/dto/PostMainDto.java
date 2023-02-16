package saessak.log.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostMainDto {

    private String imageFile;

    private long commentCount;

    private long reactionCount;

    public PostMainDto(String imageFile, long commentCount, long reactionCount) {
        this.imageFile = imageFile;
        this.commentCount = commentCount;
        this.reactionCount = reactionCount;
    }
}
