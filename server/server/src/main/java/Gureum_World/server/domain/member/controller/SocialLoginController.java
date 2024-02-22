package Gureum_World.server.domain.member.controller;

import Gureum_World.server.domain.member.dto.*;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.service.SocialLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
public class SocialLoginController {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authType;

    @Autowired
    private SocialLoginService socialLoginService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientPw;

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @RequestMapping(value="/google/oauth", method = RequestMethod.POST)
    public SocialDTO.socialRes loginGoogle(@RequestBody String jwtToken){
        RestTemplate restTemplate = new RestTemplate();
//        GoogleRequest googleOAuthRequestParam = GoogleRequest
//                .builder()
//                .clientId(googleClientId)
//                .clientSecret(googleClientPw)
//                .code(authCode)
//                .redirectUri("http://localhost:5173/google/oauth")
//                .grantType("authorization_code").build();
//        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
//                googleOAuthRequestParam, GoogleResponse.class);
//        String jwtToken=resultEntity.getBody().getId_token();

        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfResponse.class);

//        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo").queryParam("id_token",jwtToken).toUriString();
//        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        
        String name = resultEntity2.getBody().getName();
        String kakaoId = resultEntity2.getBody().getSub();
        String image = resultEntity2.getBody().getPicture();
        log.info("구글 로그인 요청 ");
        return socialLoginService.SocialLogin(name, kakaoId, image);
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/oauth")
    public SocialDTO.socialRes KakaoCallback(@RequestBody String code) throws ParseException {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", authType);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
//        System.out.println("code :  " + code);
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        System.out.println("카카오 액세스 토큰 : " + oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = new HttpEntity<>(null, headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );


        JSONObject profileJson = new JSONObject(response2.getBody());
        Long kakaoId = profileJson.getLong("id");
        JSONObject kakaoAccount = profileJson.getJSONObject("kakao_account");
        String name = kakaoAccount.getJSONObject("profile").getString("nickname");
        String image = kakaoAccount.getJSONObject("profile").getString("profile_image_url");
        log.info("카카오 로그인 요청 ");
        return socialLoginService.SocialLogin(name, String.valueOf(kakaoId), image);

    }
}
