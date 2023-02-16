package saessak.log.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.dto.PostSaveDto;
import saessak.log.post.service.PostService;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.service.PostMediaService;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    private final PostMediaService postMediaService;

    //위치는 얘기하고 바꾸기.
    @PostMapping("/posts/new")
    public ResponseEntity savePost(@RequestBody PostMediaSaveDto postMediaSaveDto) {
        //유저를 어떻게 가져와서 PostSaveDto에 저장? jwt 토큰 봐야할듯.
        PostSaveDto postSaveDto = new PostSaveDto();
//        postSaveDto.setUser();
        Long postId = postService.savePost(postSaveDto);
        postMediaService.savePostMedia(postId, postMediaSaveDto);
        return (ResponseEntity) ResponseEntity.ok();
    }

    @GetMapping("/")
    public ResponseEntity mainPosts() {
        PostResponseDto postResponseDto = postService.findAllPosts();
        return ResponseEntity.ok(postResponseDto);
    }
}
