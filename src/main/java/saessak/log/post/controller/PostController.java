package saessak.log.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import saessak.log.jwt.dto.TokenToUserDto;
import saessak.log.post.dto.PostAllResponseDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.service.PostService;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.service.PostMediaService;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 작성")
    @PostMapping("/new")
    public ResponseEntity savePost(@RequestBody PostMediaSaveDto postMediaSaveDto, Authentication authentication) {
        String profileId = authentication.getName();
        Long postId = postService.savePost(profileId, postMediaSaveDto);
        return ResponseEntity.ok().body(postId);
    }

    @ApiOperation(value = "메인 페이지 - 좋아요 순")
    @GetMapping("/likeCount")
    public ResponseEntity<PostAllResponseDto> mainPostsOrderByLikeCount(
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer page)
    {
        PostAllResponseDto postResponseDto = postService.findAllPostsByLikeCount();
        return ResponseEntity.ok().body(postResponseDto);
    }

    @ApiOperation(value = "메인 페이지 - 댓글 순")
    @GetMapping("/commentsCount")
    public ResponseEntity<PostAllResponseDto> mainPostsOrderByCommentsCount(
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "page", required = false) Integer page)
    {
        PostAllResponseDto postResponseDto = postService.findAllPostsByCommentsCount();
        return ResponseEntity.ok().body(postResponseDto);
    }

    @ApiOperation(value = "게시글 단건 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> post(@PathVariable("postId") Long postId) {
        PostResponseDto postResponseDto = postService.findPost(postId);
        return ResponseEntity.ok().body(postResponseDto);
    }
}
