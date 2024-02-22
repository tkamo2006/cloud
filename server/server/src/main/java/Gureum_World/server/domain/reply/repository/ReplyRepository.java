package Gureum_World.server.domain.reply.repository;

import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.post.entity.Post;
import Gureum_World.server.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Long countByPostId(Post post);


    Reply findByPostIdAndReplyIdx(Post post, Long replyIdx);

    List<Reply> findByPostIdOrderByReplyIdxAsc(Post post);

    List<Reply> findByPostIdIn(List<Long> postIds);
}
