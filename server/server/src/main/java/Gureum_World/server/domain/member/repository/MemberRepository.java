package Gureum_World.server.domain.member.repository;

import Gureum_World.server.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByKakaoId(String kakaoId);

    List<Member> findByKakaoIdIn(List<String> kakaoIds);
}
