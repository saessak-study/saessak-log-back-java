package saessak.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.domain.post.Post;
import saessak.log.domain.post_media.PostMedia;
import saessak.log.domain.post_media.dto.PostMediaSaveDto;
import saessak.log.repository.PostMediaRepository;
import saessak.log.repository.PostRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostMediaService {

    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long savePostMedia(Long postId, PostMediaSaveDto postMediaSaveDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
        postMediaSaveDto.setPost(post);
        PostMedia postMedia = postMediaSaveDto.toEntity();
        PostMedia savedPostMedia = postMediaRepository.save(postMedia);
        return savedPostMedia.getId();
    }
}
