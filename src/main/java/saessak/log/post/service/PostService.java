package saessak.log.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import saessak.log.post.Post;
import saessak.log.post.dto.*;
import saessak.log.post.repository.PostRepository;
import saessak.log.post_media.PostMedia;
import saessak.log.post_media.repository.PostMediaRepository;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaRepository postMediaRepository;

    @Transactional
    public Long savePost(String profileId, String postText, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();
        String encodedImageFile = Base64.getEncoder().encodeToString(bytes);
        String imageFile = "data:" + contentType + ";base64," + encodedImageFile;

        String pythonApiUrl = "http://3.39.201.8:5000/file_upload";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("imageFile", imageFile);
        requestJson.put("fileName", originalFilename);

        ObjectMapper mapper = new ObjectMapper();
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(requestJson).toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);

        User user = userRepository.findByProfileId(profileId);
        Post post = Post.from(user);
        Post savedPost = postRepository.save(post);

        String imageFileName = response.getBody();
        log.info("imageFileName={}", imageFileName);

        // postMedia의 postText에 저장
        PostMedia postMedia = PostMedia.of(imageFileName, postText);
        postMedia.belongToPost(post);
        postMediaRepository.save(postMedia);

        return savedPost.getId();
    }

    public PostResponseDto findPost(Long postId) throws JsonProcessingException {
        PostMedia postMedia = postMediaRepository.findByPostId(postId);
        String imageFileName = postMedia.getImageFile();

        PostResponseDto postResponseDto = postRepository.findPostById(postId);
        postResponseDto.setPostText(postMedia.getPostText());
        postResponseDto.setImageFile("https://saessaklogfile.s3.ap-northeast-2.amazonaws.com/image/" + imageFileName);
        return postResponseDto;
    }

    public PostAllResponseDto findAllPostsByLikeCount(int limit, int page) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<PostMainDto> pagePostMainDto = postRepository.findAllPostMainDtoOrderByLikeCount(pageRequest);
        List<PostMainDto> postMainDtoList = pagePostMainDto.getContent();
        return new PostAllResponseDto(postMainDtoList);
    }

    public PostAllResponseDto findAllPostsByCommentsCount(int limit, int page) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<PostMainDto> pagePostMainDtoList = postRepository.findAllPostMainDtoOrderByCommentCount(pageRequest);
        List<PostMainDto> postMainDtoList = pagePostMainDtoList.getContent();
        return new PostAllResponseDto(postMainDtoList);
    }

    // 편의 기능 함수 추가 --아연
    public Long findUserIndexByPostId(Long postIndex) {
        Post foundedPost = postRepository.findById(postIndex).orElseThrow(() -> new IllegalArgumentException());
        return foundedPost.getUser().getId();
    }

    public MyActivitiesResponse getMyActivity(String profileId, Integer page, Integer limit) {
        User findUser = userRepository.findByProfileId(profileId);
        Long userId = findUser.getId();

        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<PostMyActivityDto> pageMyPost = postRepository.findMyPost(userId, pageRequest);
        List<PostMyActivityDto> myActivityPosts = pageMyPost.getContent();
        return new MyActivitiesResponse(myActivityPosts);
    }

    public SubscribePostResponse getSubscribedPosts(String profileId, int page, int limit) {
        User findUser = userRepository.findByProfileId(profileId);
        Long userId = findUser.getId();

        PageRequest pageRequest = PageRequest.of(page, limit);
        List<SubscribePostDto> subscribedPostList = postRepository.findSubscribedPosts(userId, pageRequest);
        return new SubscribePostResponse(subscribedPostList);
    }
}
