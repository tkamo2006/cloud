package Gureum_World.server.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ReplyRes {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReplyNewRes {
        private Long replyIdx;
        private String kakaoId;
        private String context;
        private String nickname;
        private String link;
        private String created;
    }
}
