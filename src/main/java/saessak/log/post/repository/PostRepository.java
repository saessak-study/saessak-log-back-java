package saessak.log.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saessak.log.comment.Comment;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;
import saessak.log.post.dto.PostResponseDto;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new saessak.log.post.dto.PostMainDto(pm.imageFile, count(distinct c), count(distinct r) as likeCount) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "group by p.id " +
        "order by likeCount desc")
    List<PostMainDto> findAllPostMainDtoOrderByLikeCount();

    @Query("select new saessak.log.post.dto.PostMainDto(pm.imageFile, count(distinct c) as commentCount, count(distinct r)) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "group by p.id " +
        "order by commentCount desc")
    List<PostMainDto> findAllPostMainDtoOrderByCommentCount();

    @Query("select new saessak.log.post.dto.PostResponseDto(u.profileId, pm.imageFile," +
        " pm.postText, count(r))" +
        " from Post p" +
        " left join p.user u" +
        " left join p.postMedia pm" +
        " left join p.reactions r" +
        " where p.id = :postId")
    PostResponseDto findPostById(@Param("postId") Long postId);

}
