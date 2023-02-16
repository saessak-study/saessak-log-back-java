package saessak.log.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new saessak.log.post.dto.PostMainDto(pm.imageFile, count(c), count(r)) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "group by p.id")
    List<PostMainDto> findAllPostMainDto();
}
