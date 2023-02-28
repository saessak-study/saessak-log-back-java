package saessak.log.post.dto;

import lombok.Getter;
import lombok.Setter;
import saessak.log.comment.Comment;
import saessak.log.post.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class PostResponseDto {

    private String profileId;
    private String imageFile;
    private String postText;
    private long likeCount;

    public PostResponseDto(String profileId, long likeCount) {
        this.profileId = profileId;
        this.likeCount = likeCount;
    }
}
