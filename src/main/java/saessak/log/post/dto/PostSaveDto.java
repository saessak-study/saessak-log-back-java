package saessak.log.post.dto;

import lombok.Getter;
import lombok.Setter;
import saessak.log.post.Post;
import saessak.log.user.User;

@Getter @Setter
public class PostSaveDto {

    //유저값을 DTO에 담는지 생각해봐야할듯. 유저 값을 어떻게 확인하는지에 대해
    private User user;

    public Post toEntity() {
        return Post.from(user);
    }
}
