package Gureum_World.server.domain.member.controller;


import Gureum_World.server.domain.global.BaseResponse;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.MemberReq;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "")
public class LoginController {

    @Autowired
    private LoginService loginService;


    // 회원가입
//    @PostMapping("/join")
//    public BaseResponse<MemberReq.UserJoinRes> join(@RequestBody MemberDTO user) throws Exception {
//
//        return new BaseResponse<>(loginService.join(user));
//    }

    // 회원가입
    @PostMapping("/join")
    public BaseResponse<MemberReq.UserJoinRes> join(@RequestBody MemberDTO user) throws Exception {

        return new BaseResponse<>(loginService.join(user));
    }

}
