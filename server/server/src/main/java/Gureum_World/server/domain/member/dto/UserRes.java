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
        private String name;
        private String nickname;
        private String intro;
        private String link;
        private Long today;
        private Long total;
        private String color;
        private String background;
        private Long upgrade;
        private Long level;
        private Long percent;
        private String image;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserInfoRole {
        private String name;
        private String nickname;
        private String intro;
        private String link;
        private Long today;
        private Long total;
        private String color;
        private String background;
        private Long upgrade;
        private Long level;
        private Long percent;
        private String image;
        private String status;
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserPostInfo {
        private String nickname;
        private String link;
        private String color;
        private String background;
        private Long level;
        private String kakaoId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserGuestInfo {
        private String houseName;
        private String color;
        private String background;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserGetInfo {
        private String nickname;
        private String intro;
        private String color;
        private String background;
    }
}
