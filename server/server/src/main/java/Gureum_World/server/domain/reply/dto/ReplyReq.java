package Gureum_World.server.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReplyReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReplyNewReq {
        private String userId;
        private Long postIdx;
        private String context;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReplyUpdateReq {
        private String userId;
        private Long postIdx;
        private Long replyIdx;
        private String context;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReplyDeleteReq {
        private String userId;
        private Long postIdx;
        private Long replyIdx;
    }
}
