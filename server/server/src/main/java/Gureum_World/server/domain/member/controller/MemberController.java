package Gureum_World.server.domain.member.controller;

import Gureum_World.server.domain.global.BaseResponse;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import Gureum_World.server.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 유저 정보 조회
    @GetMapping("")
    public BaseResponse<UserRes.UserInfo> getMyInfo(HttpServletRequest request) throws Exception {
        return new BaseResponse<>(memberService.getMyInfo(request));
    }

    @PostMapping("")
    public BaseResponse<String> updateMyInfo(@RequestBody MemberDTO user, HttpServletRequest request) throws Exception {
        memberService.updateMyInfo(user, request);
        return new BaseResponse<>("정보가 수정되었습니다.");
    }

    @PostMapping("/upgrade")
    public BaseResponse<UserRes.UserLevel> UpgradeLevel(HttpServletRequest request) throws Exception {
        return new BaseResponse<>(memberService.upgradeLevel(request));
    }

}
