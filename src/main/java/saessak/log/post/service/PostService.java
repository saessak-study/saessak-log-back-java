package saessak.log.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import saessak.log.post.Post;
import saessak.log.post.dto.*;
import saessak.log.post.repository.PostRepository;
import saessak.log.post_media.PostMedia;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.repository.PostMediaRepository;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMediaRepository postMediaRepository;

    @Transactional
    public Long savePost(String profileId, PostMediaSaveDto postMediaSaveDto) {
        User user = userRepository.findByProfileId(profileId);
        Post post = Post.from(user);
        Post savedPost = postRepository.save(post);

        PostMedia postMedia = postMediaSaveDto.toEntity();
        postMedia.belongToPost(post);
        postMediaRepository.save(postMedia);

        return savedPost.getId();
    }

    @Transactional
    public ResponseEntity<String> savePost2(String profileId, PostMediaSaveDto postMediaSaveDto) {
        String imageFile = postMediaSaveDto.getImageFile().split(",")[1];
        String pythonApiUrl = "http://192.168.0.176:5000/file_upload";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> imageFileMap = new HashMap<>();
        imageFileMap.put("imageFile", imageFile);
        log.info("imageFileMap={}", imageFileMap);

        HttpEntity<String> request = new HttpEntity<>(imageFileMap.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);
        log.info("response={}", response);

        User user = userRepository.findByProfileId(profileId);
        Post post = Post.from(user);
        Post savedPost = postRepository.save(post);

        //get body가 맞는지는 고쳐야함.
        String imageFileName = response.getBody();// 이미지에 관련된 무언가를 postMedia의 imageFile에다가 저장.
        log.info("responsebody={}", imageFileName);
        String postText = postMediaSaveDto.getPostText();// postMedia의 postText에 저장
        PostMedia postMedia = PostMedia.of(imageFileName, postText);
        postMedia.belongToPost(post);
        postMediaRepository.save(postMedia);

        return response;
    }

    public ResponseEntity<String> savePost3(String profileId, String postText, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();
        String imageFile = Base64.getEncoder().encodeToString(bytes);


        String pythonApiUrl = "http://192.168.0.176:5000/file_upload";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("imageFile", imageFile);
        requestJson.put("fileName", originalFilename);
        requestJson.put("contentType", contentType);

        log.info("requestJson={}", requestJson);
        log.info("headers={}", headers);

        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);
        log.info("request={}", request);

        ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);
        log.info("response={}", response);

        User user = userRepository.findByProfileId(profileId);
        Post post = Post.from(user);
        Post savedPost = postRepository.save(post);

        String imageFileName = response.getBody();// 이미지에 관련된 무언가를 postMedia의 imageFile에다가 저장.
        log.info("responsebody={}", imageFileName);
        // postMedia의 postText에 저장
        PostMedia postMedia = PostMedia.of(imageFileName, postText);
        postMedia.belongToPost(post);
        postMediaRepository.save(postMedia);

        return response;
    }

    public ResponseEntity<String> savePost4(String profileId, String postText, MultipartFile file) throws IOException {

        String pythonApiUrl = "http://192.168.0.176:5000/file_upload";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        log.info("file={}", file);

        InputStream inputStream = file.getInputStream();
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        log.info("inputStream={}", inputStream);
        log.info("inputStreamResource={}", inputStreamResource);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", inputStreamResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        log.info("requestEntity={}", requestEntity);

        ResponseEntity<String> response = restTemplate.exchange(pythonApiUrl, HttpMethod.POST, requestEntity, String.class);
        log.info("response={}", response);

        User user = userRepository.findByProfileId(profileId);
        Post post = Post.from(user);
        Post savedPost = postRepository.save(post);

        String imageFileName = response.getBody();// 이미지에 관련된 무언가를 postMedia의 imageFile에다가 저장.
        log.info("responsebody={}", imageFileName);
        // postMedia의 postText에 저장
        PostMedia postMedia = PostMedia.of(imageFileName, postText);
        postMedia.belongToPost(post);
        postMediaRepository.save(postMedia);

        return response;
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

    public PostResponseDto findPost(Long postId) {
        return postRepository.findPostById(postId);
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
}
