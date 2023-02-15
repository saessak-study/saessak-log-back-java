package saessak.log.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.domain.post.dto.PostSaveDto;
import saessak.log.domain.post_media.PostMedia;
import saessak.log.domain.post_media.dto.PostMediaSaveDto;
import saessak.log.repository.PostMediaRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostMediaServiceTest {

    @Autowired
    PostMediaRepository postMediaRepository;

    @Autowired
    PostMediaService postMediaService;

    @Autowired
    PostService postService;


    @Test
    public void savePostMedia() throws Exception {
        //given
        PostSaveDto postSaveDto = new PostSaveDto();
        Long savedPostId = postService.savePost(postSaveDto);

        PostMediaSaveDto postMediaSaveDto = new PostMediaSaveDto("image", "text");

        //when
        Long savedPostMediaId = postMediaService.savePostMedia(savedPostId, postMediaSaveDto);
        PostMedia postMedia = postMediaRepository.findById(savedPostMediaId).get();

        //then
        assertThat(postMedia.getId()).isEqualTo(savedPostMediaId);
    }
}