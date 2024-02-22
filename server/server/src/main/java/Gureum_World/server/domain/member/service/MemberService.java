package Gureum_World.server.domain.member.service;

import Gureum_World.server.domain.global.BaseException;
import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;
import Gureum_World.server.domain.global.security.JWT.TokenDTO;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import Gureum_World.server.domain.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static Gureum_World.server.domain.global.BaseResponseStatus.*;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisTemplate redisTemplate;

    public String logout(HttpServletRequest request) {
        try {
            // token 으로 user 정보 받음
            String kakaoId = jwtTokenProvider.getCurrentUser(request);
            Member user = memberRepository.findByKakaoId(kakaoId)
                    .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

            user.setLogin_cnt(0L);
            memberRepository.saveAndFlush(user);

            // Redis 에서 해당 User ID 로 저장된 token 이 있는지 확인 후 있는 경우 삭제
            Object token = redisTemplate.opsForValue().get("RT:" + user.getKakaoId());
            if (token != null) {
                redisTemplate.delete("RT:"+user.getKakaoId());
            }

            Long expire = jwtTokenProvider.getExpireTime((String) token).getTime();
            redisTemplate.opsForValue().set(token, "logout", expire, TimeUnit.MILLISECONDS);

            return "로그아웃 성공";
        } catch (Exception exception) {
//            return exception.getMessage();
            return "유효하지 않은 토큰입니다.";
        }
    }

    public UserRes.UserInfoRole getUserInfo(String userId, HttpServletRequest request) throws Exception {
        String jwtId;
        if(jwtTokenProvider.resolveToken(request) == null){
            jwtId = "null";
        }else{
            jwtId = jwtTokenProvider.getCurrentUser(request);
        }
        // 토큰은 접속한 사람의 Id, userId는 home/userId 의 주인

        Member user = memberRepository.findByKakaoId(userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        UserRes.UserInfoRole userInfo = new UserRes.UserInfoRole();
        if(jwtId.equals(userId)){
            userInfo.setStatus("master");
        }else{
            userInfo.setStatus("guest");
        }


        Long PostCnt = postRepository.countByUserId(user);

        if(!jwtId.equals(userId) && !jwtId.equals("null")){
            user.setToday(user.getToday()+1L);
            user.setTotal(user.getTotal()+1L);
        }

        Long requiredPoints = 0L;
        user.setUpgrade(user.getTotal() + PostCnt*3);

        switch (user.getLevel().intValue()) {
            case 1 -> requiredPoints = 100L;
            case 2 -> requiredPoints = 300L;
        }
        Long percent = (long) (((double) user.getUpgrade() / requiredPoints) * 100);
        user.setPercent(percent > 100 ? 100 : percent);
        memberRepository.saveAndFlush(user);


        userInfo.setName(user.getName());
        userInfo.setNickname(user.getNickname());
        userInfo.setIntro(user.getIntro());
        userInfo.setLink(user.getLink());
        userInfo.setToday(user.getToday());
        userInfo.setTotal(user.getTotal());
        userInfo.setColor(user.getColor());
        userInfo.setBackground(user.getBackground());
        userInfo.setUpgrade(user.getUpgrade());
        userInfo.setLevel(user.getLevel());
        userInfo.setImage(user.getImage());
        userInfo.setPercent(user.getPercent());

        return userInfo;
    }

    public UserRes.UserGuestInfo getGuestInfo(String userId, HttpServletRequest request) throws Exception {
        Member user = memberRepository.findByKakaoId(userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(INVALID_USER_JWT));

        UserRes.UserGuestInfo userGuestInfo = new UserRes.UserGuestInfo();

        userGuestInfo.setHouseName(user.getName());
        userGuestInfo.setBackground(userEntity.getBackground());
        userGuestInfo.setColor(userEntity.getColor());
        return userGuestInfo;
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
        userEntity.setLink("https://cloudworld.vercel.app/home/"+kakaoId);

        return Optional.of(memberRepository.saveAndFlush(userEntity));
    }

    public UserRes.UserGetInfo getMyInfo(HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (userEntity == null) {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }

        UserRes.UserGetInfo userGetInfo = new UserRes.UserGetInfo();

        userGetInfo.setNickname(userEntity.getNickname());
        userGetInfo.setBackground(userEntity.getBackground());
        userGetInfo.setColor(userEntity.getColor());
        userGetInfo.setIntro(userEntity.getIntro());

        return userGetInfo;
    }

    public UserRes.UserLevel GetLevel(HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        long requiredPoints = 0;
        UserRes.UserLevel userLevel = new UserRes.UserLevel();

        if (userEntity.getLevel() < 5) {
            switch (userEntity.getLevel().intValue()) {
                case 1 -> requiredPoints = 100;
                case 2 -> requiredPoints = 300;
            }

            if (userEntity.getUpgrade() < requiredPoints)
                userLevel.setNeedPoint(requiredPoints - userEntity.getUpgrade());
        }

        userLevel.setNickname(userEntity.getNickname());
        userLevel.setBackground(userEntity.getBackground());
        userLevel.setColor(userEntity.getColor());
        userLevel.setUpgrade(userEntity.getUpgrade());
        userLevel.setLevel(userEntity.getLevel());

        return userLevel;

    }

    public String upgradeLevel(HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        String result = "null";

        long requiredPoints = 0;

        if (userEntity.getLevel() < 5) {
            switch (userEntity.getLevel().intValue()) {
                case 1 -> requiredPoints = 100;
                case 2 -> requiredPoints = 300;
            }

            if (userEntity.getUpgrade() >= requiredPoints) {
                // 충분한 포인트가 있을 경우 레벨 업그레이드
                String oldCh = String.valueOf(userEntity.getLevel());
                String newCh = String.valueOf(userEntity.getLevel()+1);
                userEntity.setColor(userEntity.getColor().replace(oldCh, newCh));
                userEntity.setLevel(userEntity.getLevel() + 1);
                memberRepository.save(userEntity);
                result = "레벨업 성공";
            }else{
                result = "레벨업 실패";
            }

        }
        return result;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void resetTodayField() {
        // 매일 자정에 실행되는 작업
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            member.setToday(0L);
        }
        memberRepository.saveAll(members);
    }

    public List<TokenDTO> reissue(HttpServletRequest request) throws BaseException {
        String rtk = request.getHeader("rtk");
        System.out.println("reissue함수실행 rtk: "+rtk);

        // refresh token 유효성 검증
        if (!jwtTokenProvider.validateToken(rtk))
            throw new BaseException(INVALID_JWT);

        String kakaoId = jwtTokenProvider.getUserPk(rtk);

        // Redis에서 email 기반으로 저장된 refresh token 값 가져오기
//        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + kakaoId);
//        if(!refreshToken.equals(rtk)) {
//            throw new BaseException(RTK_INCORRECT);
//        }

        // refresh token 유효할 경우 새로운 토큰 생성
        List<TokenDTO> tokenDTOList = new ArrayList<>();
        TokenDTO newRefreshToken = jwtTokenProvider.createRefreshToken(kakaoId);
        TokenDTO newAccessToken = jwtTokenProvider.createAccessToken(kakaoId, "User");
        tokenDTOList.add(newRefreshToken);
        tokenDTOList.add(newAccessToken);

        // Redis에 refresh token 업데이트
        redisTemplate.opsForValue().set("RT:"+kakaoId,newRefreshToken.getToken(),newRefreshToken.getTokenExpiresTime().getTime(), TimeUnit.MILLISECONDS);

        return tokenDTOList;
    }


}