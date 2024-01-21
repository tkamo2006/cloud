package Gureum_World.server.domain.member.dto;

import Gureum_World.server.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String level;

    private String color;

    private String background;

    private String upgrade;

    private String role;

    private char status;

    private LocalDateTime login_at;

    private Long login_cnt;
}
