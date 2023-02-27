package saessak.log.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostMainDto {

    private Long postId;
    private String imageFile;
    private long commentCount;
    private long reactionCount;

    public PostMainDto(long postId, String imageFile, long commentCount, long reactionCount) {
        this.postId = postId;
        this.imageFile = imageFile;
        this.commentCount = commentCount;
        this.reactionCount = reactionCount;
    }
}
