package saessak.log.post_media.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.post.Post;
import saessak.log.post_media.PostMedia;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.repository.PostMediaRepository;
import saessak.log.post.repository.PostRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostMediaService {

    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long savePostMedia(Long postId, PostMediaSaveDto postMediaSaveDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
        PostMedia postMedia = postMediaSaveDto.toEntity();
        postMedia.belongToPost(post);
        PostMedia savedPostMedia = postMediaRepository.save(postMedia);
        return savedPostMedia.getId();
    }
}
