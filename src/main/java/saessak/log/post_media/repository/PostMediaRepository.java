package saessak.log.post_media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.post_media.PostMedia;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
}
