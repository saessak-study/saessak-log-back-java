package saessak.log.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saessak.log.jwt.TokenParser;
import saessak.log.jwt.dto.TokenToUserDto;
import saessak.log.post.dto.PostAllResponseDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.service.PostService;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.service.PostMediaService;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    private final PostMediaService postMediaService;

    private final TokenParser tokenParser;

    //위치는 얘기하고 바꾸기.
    @PostMapping("/new")
    public ResponseEntity savePost(@RequestBody PostMediaSaveDto postMediaSaveDto,
                                   @RequestHeader(value = "accessToken") String accessToken) {

        //유저를 어떻게 가져와서 PostSaveDto에 저장? jwt 토큰 봐야할듯.
        TokenToUserDto tokenToUserDto = tokenParser.parseToken(accessToken);
        Long postId = postService.savePost(tokenToUserDto);
        postMediaService.savePostMedia(postId, postMediaSaveDto);
        return ResponseEntity.ok().build();
    }

    //댓글 순으로 하는거는 요청 url이 달라지는지. 확인해봐야할듯.
    @GetMapping("/likeCount")
    public ResponseEntity mainPostsOrderByLikeCount() {
        PostAllResponseDto postResponseDto = postService.findAllPosts();
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity post(@PathVariable("postId") Long postId) {
        PostResponseDto postResponseDto = postService.findPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }
}
