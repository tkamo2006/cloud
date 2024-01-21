package Gureum_World.server.domain.member.service;

import Gureum_World.server.domain.global.BaseException;
import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.UserReq;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static Gureum_World.server.domain.global.BaseResponseStatus.FAILED_TO_LOGIN;

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
    public UserRes.UserInfo getMyInfo(HttpServletRequest request) throws Exception {
            String kakaoId = jwtTokenProvider.getCurrentUser(request);
            Member user = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

            UserRes.UserInfo userInfo = new UserRes.UserInfo();
            userInfo.setNickname(user.getNickname());
            userInfo.setIntro(user.getIntro());
            userInfo.setLink(user.getLink());
            userInfo.setToday(user.getToday());
            userInfo.setTotal(user.getTotal());
            userInfo.setColor(user.getColor());
            userInfo.setBackground(user.getBackground());
            userInfo.setUpgrade(user.getUpgrade());
            userInfo.setLevel(user.getLevel());

            return userInfo;
    }

    // 유저 정보 수정
    public Optional<Member> updateMyInfo(MemberDTO user, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (userEntity == null) {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
        if (user.getNickname() != null) {
            userEntity.setNickname(user.getNickname());
        }
        if (user.getIntro() != null) {
            userEntity.setIntro(user.getIntro());
        }
        if (user.getColor() != null) {
            userEntity.setColor(user.getColor());
        }
        if (user.getBackground() != null) {
            userEntity.setBackground(user.getBackground());
        }
        userEntity.setLink("http://localhost:8080/"+user.getNickname());

        return Optional.of(memberRepository.saveAndFlush(userEntity));
    }


    public UserRes.UserLevel upgradeLevel(HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));


        long requiredPoints = 0;
        UserRes.UserLevel userLevel = new UserRes.UserLevel();

        if (userEntity.getLevel() < 5) {
            switch (userEntity.getLevel().intValue()) {
                case 1 -> requiredPoints = 100;
                case 2 -> requiredPoints = 200;
                case 3 -> requiredPoints = 500;
                case 4 -> requiredPoints = 1000;
            }

            if (userEntity.getUpgrade() >= requiredPoints) {
                // 충분한 포인트가 있을 경우 레벨 업그레이드
                userEntity.setLevel(userEntity.getLevel() + 1);
                userEntity.setUpgrade(userEntity.getUpgrade() - requiredPoints);

                memberRepository.save(userEntity);
                userEntity.setLevel(userEntity.getLevel());
            } else {
                // 필요한 추가 포인트 반환
                userLevel.setNeedPoint(requiredPoints - userEntity.getUpgrade());
            }
        }

        userLevel.setNickname(userEntity.getNickname());
        userLevel.setBackground(userEntity.getBackground());
        userLevel.setColor(userEntity.getColor());
        userLevel.setUpgrade(userEntity.getUpgrade());
        userLevel.setLevel(userEntity.getLevel());

        return userLevel;

    }
}