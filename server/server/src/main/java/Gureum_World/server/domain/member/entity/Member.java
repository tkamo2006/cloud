package Gureum_World.server.domain.member.entity;

import Gureum_World.server.domain.BaseEntity;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.post.entity.Post;
import Gureum_World.server.domain.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Member")
@Table(name = "Member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "kakaoId", nullable = false)
    private String kakaoId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "intro", nullable = true)
    private String intro;

    @Column(name = "link", nullable = true)
    private String link;

    @Column(name = "today", nullable = true)
    private Long today;

    @Column(name = "total", nullable = true)
    private Long total;

    @Column(name = "level", nullable = true)
    private Long level;

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "background", nullable = true)
    private String background;

    @Column(name = "upgrade", nullable = true)
    private Long upgrade;

    @Column(name = "login_at", nullable = true)
    private LocalDateTime login_at;

    @Column(name = "login_cnt", nullable = true)
    @ColumnDefault("0")
    private Long login_cnt;

    @Column(name = "status", nullable = true)
    @ColumnDefault("'A'") // A: 활성 유저 D: 탈퇴 유저
    private char status;

    @Column(name = "role", nullable = true) // User
    private String role;

    @OneToMany(mappedBy = "userId")
    private List<Post> posts = new ArrayList<>();
//    @OneToMany(mappedBy = "userId")
//    private List<Reply> replies = new ArrayList<>();

    private LocalDateTime created_at;


    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .name(name)
                .kakaoId(kakaoId)
                .background(background)
                .intro(intro)
                .today(today)
                .total(total)
                .level(level)
                .link(link)
                .color(color)
                .upgrade(upgrade)
                .nickname(nickname)
                .role(role)
                .status(status)
                .login_at(login_at)
                .login_cnt(login_cnt)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
