package saessak.log.comment.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saessak.log.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}