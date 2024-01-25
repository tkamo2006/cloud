package Gureum_World.server.domain.member.service;


import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;

import Gureum_World.server.domain.global.security.JWT.TokenDTO;
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


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
                .level(1L)
                .link("link")
                .color("color")
                .upgrade(0L)
                .nickname("nickname")
                .build();
        return new MemberReq.UserJoinRes(memberRepository.saveAndFlush(newUser));
    }

    public List<TokenDTO> login(MemberDTO user) throws Exception {
        Member member = memberRepository.findByKakaoId(user.getKakaoId())
                .orElseThrow(() -> new Exception("가입되지 않은 회원입니다."));

        member.setLogin_at(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        member.setLogin_cnt(member.getLogin_cnt()+1);
        memberRepository.saveAndFlush(member);

        // token 발급
        TokenDTO refreshToken = jwtTokenProvider.createRefreshToken();
        TokenDTO accessToken = jwtTokenProvider.createAccessToken(member.getKakaoId(), member.getRole());

        // login 시 Redis 에 RT: user@email.com(key) : ----token-----(value) 형태로 token 저장
        redisTemplate.opsForValue().set("RT:"+member.getKakaoId(), accessToken.getToken(), accessToken.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);
//        redisTemplate.opsForValue().set("RT:"+userEntity.getEmail(), refreshToken.getToken(), refreshToken.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);

        List<TokenDTO> tokenDTOList = new ArrayList<>();
        tokenDTOList.add(refreshToken);
        tokenDTOList.add(accessToken);
        return tokenDTOList;
    }
}
