package saessak.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.domain.post.Post;
import saessak.log.domain.post.dto.PostSaveDto;
import saessak.log.repository.PostRepository;

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

    public List<Post> findPosts() {
        return postRepository.findAll();
    }
}
