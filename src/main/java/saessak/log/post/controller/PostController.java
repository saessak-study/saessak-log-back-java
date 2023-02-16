package saessak.log.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saessak.log.DefaultRes;
import saessak.log.post.dto.PostSaveDto;
import saessak.log.post.service.PostService;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.service.PostMediaService;
import saessak.log.user.User;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    private final PostMediaService postMediaService;

    //위치는 얘기하고 바꾸기.
    @PostMapping("/posts/new")
    public ResponseEntity<DefaultRes<Object>> savePost(@RequestBody PostMediaSaveDto postMediaSaveDto) {
        //유저를 어떻게 가져와서 PostSaveDto에 저장? jwt 토큰 봐야할듯.
        PostSaveDto postSaveDto = new PostSaveDto();
//        postSaveDto.setUser();
        Long postId = postService.savePost(postSaveDto);
        postMediaService.savePostMedia(postId, postMediaSaveDto);
        return new ResponseEntity<>(DefaultRes.res(200, "게시물 저장"), HttpStatus.OK);
    }
}
