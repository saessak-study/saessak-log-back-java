package saessak.log.post.dto;

import lombok.Getter;

@Getter
public class PostMyActivityDto {

    private String imageFile;
    private long commentCount;
    private long reactionCount;

    public PostMyActivityDto(String imageFile, long commentCount, long reactionCount) {
        this.imageFile = imageFile;
        this.commentCount = commentCount;
        this.reactionCount = reactionCount;
    }
}
