package Gureum_World.server.domain.member.dto;


import Gureum_World.server.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserJoinRes{
        private Member member;
    }
}
