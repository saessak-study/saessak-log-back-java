package saessak.log.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {

    List<PostMainDto> postMainDtoList;

    public PostResponseDto(List<PostMainDto> postMainDtoList) {
        this.postMainDtoList = postMainDtoList;
    }
}
