package Gureum_World.server.domain.member.service;

import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisTemplate redisTemplate;

    // 유저정보 조회
    public Optional<Member> getMyInfo(HttpServletRequest request) throws Exception {
        return memberRepository.findByKakaoId(jwtTokenProvider.getCurrentUser(request));
    }


}
