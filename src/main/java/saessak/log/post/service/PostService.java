package saessak.log.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.dto.PostSaveDto;
import saessak.log.post.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(PostSaveDto postSaveDto) {
        Post post = postSaveDto.toEntity();
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public PostResponseDto findAllPosts() {
        List<PostMainDto> postMainDtoList = postRepository.findAllPostMainDto();
        return new PostResponseDto(postMainDtoList);
    }
}
