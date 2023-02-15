package saessak.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
