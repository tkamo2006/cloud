package Gureum_World.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRes {


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserInfo {
        private String nickname;
        private String intro;
        private String link;
        private Long today;
        private Long total;
        private String color;
        private String background;
        private Long upgrade;
        private Long level;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLevel {
        private String nickname;
        private String color;
        private String background;
        private Long upgrade;
        private Long level;
        private Long needPoint;
    }


}
