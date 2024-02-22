package Gureum_World.server.domain.post.repository;

import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.post.entity.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByPostId(Long postId);

    List<Post> findAllByUserIdOrderByPostIdDesc(Member userId);

//    @Query("SELECT new Gureum_World.server.domain.post.entity.PostDto(p.postId, p.context, p.status, p.created, m.nickname) FROM Post p JOIN p.userId m WHERE m.userId = :userId ORDER BY p.postId DESC")
//    List<Post> findAllByUserIdOrderByPostIdDesc(@Param("userId") Long userId);

    Long countByUserId(Member user);

    Post findByKakaoIdAndPostIdx(String kakaoId, Long postIdx);

    Post findByUserIdAndPostIdx(Member user, Long postIdx);

    List<Post> findByUserIdOrderByPostIdxAsc(Member user);

    Page<Post> findAllByUserIdOrderByPostIdDesc(Member userId, PageRequest pageable);
}
