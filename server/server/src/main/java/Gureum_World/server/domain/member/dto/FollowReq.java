package Gureum_World.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FollowReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class FollowKakaoIdReq {
        private String kakaoId;
    }
}
