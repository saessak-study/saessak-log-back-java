package saessak.log.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saessak.log.post.dto.MyActivitiesResponse;
import saessak.log.post.dto.PostAllResponseDto;
import saessak.log.post.dto.PostResponseDto;
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

    @ApiOperation(value = "게시글 작성")
    @PostMapping("/new")
    public ResponseEntity savePost(@RequestBody PostMediaSaveDto postMediaSaveDto, Authentication authentication) {
        String profileId = authentication.getName();
        Long postId = postService.savePost(profileId, postMediaSaveDto);
        return ResponseEntity.ok().body(postId);
    }

    //string으로 인코딩된 이미지 파일을 받아서 이거를 파이썬에 바로 넘기거나, 혹은 다시 디코딩해서 넘기기?.
    //아마 인코딩 상태에서 넘기고 파이썬에서 디코딩해서 저장하는게 오류안나긴 할듯.
    //저장하면 s3에서 주는 키값을 스프링에서 반환받아서 이 키값을 DB에 저장하기.
    //게시글 찾을 때는 postId 값을 프론트에서 받는다? 그리고 이거로 DB에서 저장된 키값을 찾음 -> 키값을 파이썬에 넘겨서 파이썬에서 이미지 파일을 받아옴.
    // 프론트에 줄때 인코딩할지 안할지는 물어보기.
    @ApiOperation(value = "게시글 작성")
    @PostMapping("/new2")
    public ResponseEntity savePost2(@RequestBody PostMediaSaveDto postMediaSaveDto, Authentication authentication) {
        String profileId = authentication.getName();
        ResponseEntity<String> stringResponseEntity = postService.savePost2(profileId, postMediaSaveDto);
        String imageFileData = postMediaSaveDto.getImageFile().split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(imageFileData);
        return ResponseEntity.ok().body(stringResponseEntity);
    }

    @PostMapping("/new3")
    public ResponseEntity savePost3(@RequestParam String postText,
                                    @RequestParam MultipartFile file, Authentication authentication) throws IOException {
        String profileId = authentication.getName();
        ResponseEntity<String> stringResponseEntity = postService.savePost3(profileId, postText, file);

        return ResponseEntity.ok().body(stringResponseEntity);
    }

    @PostMapping("/new4")
    public ResponseEntity savePost4(@RequestParam String postText,
                                    @RequestParam MultipartFile file, Authentication authentication) throws IOException {
        String profileId = authentication.getName();
        ResponseEntity<String> stringResponseEntity = postService.savePost4(profileId, postText, file);

        return ResponseEntity.ok().body(stringResponseEntity);
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
    public ResponseEntity<PostResponseDto> post(@PathVariable("postId") Long postId) {
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
