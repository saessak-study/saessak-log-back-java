package saessak.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.domain.post_media.PostMedia;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
}
