package saessak.log.post_media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saessak.log.post_media.PostMedia;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    @Query("select pm from PostMedia pm" +
        " left join pm.post p" +
        " where p.id = :postId")
    PostMedia findByPostId(@Param("postId") Long postId);
}
