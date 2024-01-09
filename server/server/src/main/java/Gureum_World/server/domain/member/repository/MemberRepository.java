package Gureum_World.server.domain.member.repository;

import Gureum_World.server.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Object> findByName(String name);
}
