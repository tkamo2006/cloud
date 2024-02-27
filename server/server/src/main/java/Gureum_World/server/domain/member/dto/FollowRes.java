package Gureum_World.server.domain.member.dto;

import Gureum_World.server.domain.reply.dto.ReplyRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class FollowRes {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class FollowListRes {
        private String name;
        private String link;
        private String image;
    }
}
