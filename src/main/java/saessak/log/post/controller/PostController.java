package saessak.log.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saessak.log.post.dto.MyActivitiesResponse;
import saessak.log.post.dto.PostAllResponseDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.dto.PostSaveResponseDto;
import saessak.log.post.service.PostService;
import saessak.log.post_media.dto.PostMediaSaveDto;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 저장")
    @PostMapping("/new")
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestParam String postText,
                                                        @RequestParam MultipartFile file, Authentication authentication) throws IOException {
        String profileId = authentication.getName();
        Long savedPostId = postService.savePost(profileId, postText, file);
        PostSaveResponseDto PostSaveResponseDto = new PostSaveResponseDto(savedPostId);

        return ResponseEntity.ok().body(PostSaveResponseDto);
    }

    @ApiOperation(value = "메인 페이지 - 좋아요 순")
    @GetMapping("/likeCount")
    public ResponseEntity<PostAllResponseDto> mainPostsOrderByLikeCount(
        @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page)
    {
        PostAllResponseDto postResponseDto = postService.findAllPostsByLikeCount(limit, page);
        return ResponseEntity.ok().body(postResponseDto);
    }

    @ApiOperation(value = "메인 페이지 - 댓글 순")
    @GetMapping("/commentsCount")
    public ResponseEntity<PostAllResponseDto> mainPostsOrderByCommentsCount(
        @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page)
    {
        PostAllResponseDto postResponseDto = postService.findAllPostsByCommentsCount(limit, page);
        return ResponseEntity.ok().body(postResponseDto);
    }

    @ApiOperation(value = "게시글 단건 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> post(@PathVariable("postId") Long postId) throws JsonProcessingException {
        PostResponseDto postResponseDto = postService.findPost(postId);
        return ResponseEntity.ok().body(postResponseDto);
    }


    @ApiOperation(value = "내 활동")
    @GetMapping("/myActivity")
    public ResponseEntity<MyActivitiesResponse> userActivity(
        @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        Authentication authentication) {

        String profileId = authentication.getName();
        MyActivitiesResponse myActivityPosts = postService.getMyActivity(profileId, page, limit);
        return ResponseEntity.ok().body(myActivityPosts);
    }
}
