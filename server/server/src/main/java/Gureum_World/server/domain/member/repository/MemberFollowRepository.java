package Gureum_World.server.domain.member.repository;

import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.entity.MemberFollow;
import Gureum_World.server.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberFollowRepository extends JpaRepository<MemberFollow, Long> {

    Long countByUserId(Member userEntity);

    MemberFollow findByKakaoId(String kakaoId);


    MemberFollow findByUserIdAndKakaoId(Member userEntity, String kakaoId);

    List<MemberFollow> findByUserId(Member userEntity);

    boolean existsByUserIdAndKakaoId(Member userEntity, String kakaoId);
}
