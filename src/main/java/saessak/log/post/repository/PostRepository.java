package saessak.log.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import saessak.log.comment.Comment;
import saessak.log.post.Post;
import saessak.log.post.dto.PostMainDto;
import saessak.log.post.dto.PostMyActivityDto;
import saessak.log.post.dto.PostResponseDto;
import saessak.log.post.dto.SubscribePostDto;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new saessak.log.post.dto.PostMainDto(p.id, u.profileId, pm.imageFile, count(distinct c), count(distinct r) as likeCount) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "left join p.user u " +
        "group by p.id " +
        "order by likeCount desc")
    Page<PostMainDto> findAllPostMainDtoOrderByLikeCount(Pageable pageable);

    @Query("select new saessak.log.post.dto.PostMainDto(p.id, u.profileId, pm.imageFile, count(distinct c) as commentCount, count(distinct r)) " +
        "from Post p left join p.postMedia pm " +
        "left join p.comments c " +
        "left join p.reactions r " +
        "left join p.user u " +
        "group by p.id " +
        "order by commentCount desc")
    Page<PostMainDto> findAllPostMainDtoOrderByCommentCount(Pageable pageable);

    @Query("select new saessak.log.post.dto.PostResponseDto(u.profileId, count(r))" +
            " from Post p" +
            " left join p.user u" +
            " left join p.reactions r" +
            " where p.id = :postId")
    PostResponseDto findPostById(@Param("postId") Long postId);

    @Query("select new saessak.log.post.dto.PostMyActivityDto(p.id, pm.imageFile, count(distinct c), count(distinct r))" +
            " from Post p" +
            " left join p.user u" +
            " left join p.postMedia pm" +
            " left join p.comments c" +
            " left join p.reactions r" +
            " where u.id = :userId" +
            " group by p.id" +
            " order by p.createdDate desc")
    Page<PostMyActivityDto> findMyPost(@Param("userId") Long userId, Pageable pageable);

    @Query("select new saessak.log.post.dto.SubscribePostDto(p.id, u.profileId, pm.imageFile, count(distinct c), count(distinct r))" +
            " from Post p, Subscription s" +
            " left join p.user u" +
            " left join p.postMedia pm" +
            " left join p.comments c" +
            " left join p.reactions r" +
            " left join s.toUserId tu" +
            " left join s.fromUserId fu" +
            " where tu.id = u.id" +
            " and fu.id = :userId" +
            " group by p.id" +
            " order by p.createdDate desc")
    List<SubscribePostDto> findSubscribedPosts(@Param("userId") Long userId, Pageable pageable);

}
