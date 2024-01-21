package Gureum_World.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserChangeReq {
        private String nickname;
        private String intro;
        private String link;
        private String background;
        private String color;
    }

}
