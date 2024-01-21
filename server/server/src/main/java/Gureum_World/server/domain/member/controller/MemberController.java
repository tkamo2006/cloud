package Gureum_World.server.domain.member.controller;

import Gureum_World.server.domain.global.BaseResponse;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import Gureum_World.server.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResponse<Optional<Member>> getMyInfo(HttpServletRequest request) throws Exception {
        Optional<Member> user = memberService.getMyInfo(request);
        return new BaseResponse<>(user);
    }
}
