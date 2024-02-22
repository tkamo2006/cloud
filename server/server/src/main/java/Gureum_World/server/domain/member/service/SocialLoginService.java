package Gureum_World.server.domain.member.service;

import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;
import Gureum_World.server.domain.global.security.JWT.TokenDTO;
import Gureum_World.server.domain.global.security.SocialConfig.RoleType;
import Gureum_World.server.domain.member.dto.SocialDTO;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialLoginService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisTemplate redisTemplate;
    public String socialLogin(String name, Long id) {
        Optional<Member> optionalUserEntity = memberRepository.findByKakaoId(String.valueOf(id));
        if (optionalUserEntity.isPresent()) {
            Member userEntity = optionalUserEntity.get();
            userEntity.setLogin_at(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            userEntity.setLogin_cnt(userEntity.getLogin_cnt()+1);
            memberRepository.saveAndFlush(userEntity);

            // token 발급
            TokenDTO token = jwtTokenProvider.createAccessToken(String.valueOf(id), "USER");

            // Redis 에 RTL user@email.com(key) : ----token-----(value) 형태로 token 저장
            redisTemplate.opsForValue().set("RT:"+id, token.getToken(), token.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);
            return token.getToken();
//            return ResponseEntity.ok(token.getToken());
        }
        else {
            Member newUser = Member.builder()
                    .name(name)
                    .kakaoId(String.valueOf(id))
                    .status('A')
                    .role(String.valueOf(RoleType.USER))
                    .login_cnt(0L)
                    .background("background")
                    .intro("intro")
                    .today(0L)
                    .total(0L)
                    .level(1L)
                    .link("link")
                    .color("color")
                    .upgrade(0L)
                    .nickname("nickname")
                    .build();
            memberRepository.saveAndFlush(newUser);
            return String.valueOf(HttpStatus.CREATED.value());
//            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(newUser.getUserId()));
        }
    }

    public SocialDTO.socialRes SocialLogin(String name, String id, String image) {
        Optional<Member> optionalUserEntity = memberRepository.findByKakaoId(id);
        if (optionalUserEntity.isPresent()) {
            Member userEntity = optionalUserEntity.get();
            userEntity.setLogin_at(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            userEntity.setLogin_cnt(userEntity.getLogin_cnt()+1);
            memberRepository.saveAndFlush(userEntity);
            SocialDTO.socialRes socialRes = new SocialDTO.socialRes();
            socialRes.setUserId(userEntity.getKakaoId());


            // token 발급
            TokenDTO refreshToken = jwtTokenProvider.createRefreshToken(id);
            TokenDTO accessToken = jwtTokenProvider.createAccessToken(id, "USER");

            List<TokenDTO> tokenDTOList = new ArrayList<>();
            tokenDTOList.add(refreshToken);
            tokenDTOList.add(accessToken);

            socialRes.setToken(tokenDTOList);
            socialRes.setStatus(200);
            // Redis 에 RTL user@email.com(key) : ----token-----(value) 형태로 token 저장
            redisTemplate.opsForValue().set("RT:"+id, refreshToken.getToken(), refreshToken.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);
            return socialRes;
//            return ResponseEntity.ok(token.getToken());
        }
        else {
            Member newUser = Member.builder()
                    .name(name)
                    .kakaoId(id)
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
                    .percent(0L)
                    .image(image)
                    .nickname("nickname")
                    .build();
            memberRepository.saveAndFlush(newUser);
            SocialDTO.socialRes socialRes = new SocialDTO.socialRes();
            socialRes.setUserId(newUser.getKakaoId());

            // token 발급
            TokenDTO refreshToken = jwtTokenProvider.createRefreshToken(id);
            TokenDTO accessToken = jwtTokenProvider.createAccessToken(id, "USER");

            List<TokenDTO> tokenDTOList = new ArrayList<>();
            tokenDTOList.add(refreshToken);
            tokenDTOList.add(accessToken);

            socialRes.setToken(tokenDTOList);
            socialRes.setStatus(201);
            // Redis 에 RTL user@email.com(key) : ----token-----(value) 형태로 token 저장
            redisTemplate.opsForValue().set("RT:"+id, refreshToken.getToken(), refreshToken.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);
            return socialRes;
//            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(newUser.getUserId()));
        }
    }


}
