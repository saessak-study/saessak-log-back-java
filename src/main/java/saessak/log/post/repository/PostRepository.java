package saessak.log.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new saessak.log.post.dto.PostMainDto(pm.imageFile, count(distinct c), count(distinct r) as likeCount) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "group by p.id " +
        "order by likeCount desc")
    List<PostMainDto> findAllPostMainDtoOrderByLikeCount();

    @Query("select new saessak.log.post.dto.PostMainDto(pm.imageFile, count(distinct c), count(distinct r)) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "group by p.id " +
        "order by count(c) desc")
    List<PostMainDto> findAllPostMainDtoOrderByCommentCount();
}
