package saessak.log.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saessak.log.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
