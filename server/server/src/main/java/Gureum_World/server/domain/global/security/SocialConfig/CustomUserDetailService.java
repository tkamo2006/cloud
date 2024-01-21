package Gureum_World.server.domain.global.security.SocialConfig;

import Gureum_World.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        return (UserDetails) memberRepository.findByName(name)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//    }


    // 유저의 이름으로 찾기
    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        return memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
