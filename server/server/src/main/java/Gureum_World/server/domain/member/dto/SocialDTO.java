package Gureum_World.server.domain.member.dto;

import Gureum_World.server.domain.global.security.JWT.TokenDTO;
import Gureum_World.server.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class SocialDTO {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class socialRes{
        private String userId;
        private List<TokenDTO> token;
        private int status;
    }
}
