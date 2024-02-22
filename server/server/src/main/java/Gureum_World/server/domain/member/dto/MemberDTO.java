package Gureum_World.server.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long userId;

    private String kakaoId;

    private String name;

    private String nickname;

    private String intro;

    private String link;

    private Long today;

    private Long total;

    private Long level;

    private String color;

    private String background;

    private Long upgrade;

    private String role;

    private String image;

    private Long percent;

    private char status;

    private LocalDateTime login_at;

    private Long login_cnt;
}
