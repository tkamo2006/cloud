package Gureum_World.server.domain.member.controller;

import Gureum_World.server.domain.global.BaseException;
import Gureum_World.server.domain.global.BaseResponse;
import Gureum_World.server.domain.global.security.JWT.TokenDTO;
import Gureum_World.server.domain.member.dto.FollowReq;
import Gureum_World.server.domain.member.dto.FollowRes;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.entity.MemberFollow;
import Gureum_World.server.domain.member.repository.MemberRepository;
import Gureum_World.server.domain.member.service.MemberService;
import Gureum_World.server.domain.post.dto.PostRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) throws Exception {
        log.info("로그아웃 요청 ");
        String result = memberService.logout(request);
        return new BaseResponse<>(result);
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/{userId}")
    public BaseResponse<UserRes.UserInfoRole> getUserInfo(@PathVariable String userId, HttpServletRequest request) throws Exception {
        try{
            return new BaseResponse<>(memberService.getUserInfo(userId, request));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/board/{userId}")
    public BaseResponse<UserRes.UserGuestInfo> getGuestInfo(@PathVariable String userId, HttpServletRequest request) throws Exception {
        try{
            return new BaseResponse<>(memberService.getGuestInfo(userId, request));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("")
    public BaseResponse<String> updateMyInfo(@RequestBody MemberDTO user, HttpServletRequest request) throws Exception {
        memberService.updateMyInfo(user, request);
        return new BaseResponse<>("정보가 수정되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/edit")
    public BaseResponse<UserRes.UserGetInfo> getMyInfo(HttpServletRequest request) throws Exception {
        return new BaseResponse<>(memberService.getMyInfo(request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/upgrade")
    public BaseResponse<UserRes.UserLevel> GetLevel(HttpServletRequest request) throws Exception {
        return new BaseResponse<>(memberService.GetLevel(request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/upgrade")
    public BaseResponse<String> UpgradeLevel(HttpServletRequest request) throws Exception {

        return new BaseResponse<>(memberService.upgradeLevel(request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/reissue")
    public BaseResponse<List<TokenDTO>> reissue(HttpServletRequest request) throws Exception {
        return new BaseResponse<>(memberService.reissue(request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/bookmark")
    public BaseResponse<String> createBook(@RequestBody FollowReq.FollowKakaoIdReq userId, HttpServletRequest request) throws Exception {
        memberService.createbook(userId, request);
        return new BaseResponse<>("북마크 되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @DeleteMapping("/bookmark")
    public BaseResponse<String> deleteBook(@RequestBody FollowReq.FollowKakaoIdReq userId, HttpServletRequest request) throws Exception {
        memberService.deletebook(userId, request);
        return new BaseResponse<>("북마크가 삭제되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/bookmark")
    public BaseResponse<List<FollowRes.FollowListRes>> getAllMemberFollow(HttpServletRequest request) throws Exception {
        List<FollowRes.FollowListRes> memberFollow = memberService.getAllMemberFollow(request);
        return new BaseResponse<>(memberFollow);
    }

}
