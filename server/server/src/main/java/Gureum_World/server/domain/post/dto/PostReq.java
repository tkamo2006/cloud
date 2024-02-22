package Gureum_World.server.domain.post.dto;

import Gureum_World.server.domain.member.dto.UserRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class PostReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostCreateReq {
        private String nickname;
        private String background;
        private String color;
        private String level;
        private String content;
        private boolean secret;
        private LocalDateTime created_at;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostNewReq {
        private String userId;
        private String context;
        private boolean secret;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostUpdateReq {
        private String userId;
        private Long postIdx;
        private String context;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostDeleteReq {
        private String userId;
        private Long postIdx;
    }

}
