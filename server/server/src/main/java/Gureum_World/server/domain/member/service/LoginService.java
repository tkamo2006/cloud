package Gureum_World.server.domain.member.service;


import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;

import Gureum_World.server.domain.global.security.SocialConfig.RoleType;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.MemberReq;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public MemberReq.UserJoinRes join(MemberDTO user) throws Exception {
        Optional<Member> userEntityOptional = memberRepository.findByKakaoId(user.getKakaoId());

        Member newUser = Member.builder()
                .name(user.getName())
                .kakaoId(user.getKakaoId())
                .status('A')
                .role(String.valueOf(RoleType.USER))
                .login_cnt(0L)
                .background("black")
                .intro("intro")
                .today(0L)
                .total(0L)
                .level("1")
                .link("link")
                .color("color")
                .upgrade("upgrade")
                .nickname("nickname")
                .build();
        return new MemberReq.UserJoinRes(memberRepository.saveAndFlush(newUser));
    }
}
