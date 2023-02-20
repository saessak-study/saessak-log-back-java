package saessak.log.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.post.service.PostService;
import saessak.log.post_media.PostMedia;
import saessak.log.post_media.dto.PostMediaSaveDto;
import saessak.log.post_media.repository.PostMediaRepository;
import saessak.log.post_media.service.PostMediaService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
class PostMediaServiceTest {

    @Autowired
    PostMediaRepository postMediaRepository;

    @Autowired
    PostMediaService postMediaService;

    @Autowired
    PostService postService;


//    @Test
//    public void savePostMedia() throws Exception {
//        //given
//        PostSaveDto postSaveDto = new PostSaveDto();
//        Long savedPostId = postService.savePost(postSaveDto);
//
//        PostMediaSaveDto postMediaSaveDto = new PostMediaSaveDto("image", "text");
//
//        //when
//        Long savedPostMediaId = postMediaService.savePostMedia(savedPostId, postMediaSaveDto);
//        PostMedia postMedia = postMediaRepository.findById(savedPostMediaId).orElseThrow();
//
//        //then
//        assertThat(postMedia.getId()).isEqualTo(savedPostMediaId);
//    }
}