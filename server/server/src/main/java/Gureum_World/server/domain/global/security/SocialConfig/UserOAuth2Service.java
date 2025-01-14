package Gureum_World.server.domain.global.security.SocialConfig;


import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOAuth2Service extends DefaultOAuth2UserService {
    // 사용자의 정보를 기반으로 가입 및 정보 수정, 세션 저장 등의 기능 지원

    @Autowired
    private LoginService loginService;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");
        String kakaoId = oAuth2User.getName();
        MemberDTO user = new MemberDTO();
        user.setName(nickname);
        user.setKakaoId(kakaoId);
        loginService.join(user);
        // 소셜 로그인으로 만들 예정 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");

    }
}