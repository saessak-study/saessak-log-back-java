package saessak.log.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.comment.repository.CommentRepository;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;
import saessak.log.post.dto.PostAllResponseDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.repository.PostRepository;
import saessak.log.post_media.PostMedia;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.repository.PostMediaRepository;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;

import java.util.List;

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

}
