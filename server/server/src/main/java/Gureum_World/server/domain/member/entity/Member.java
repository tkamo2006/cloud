package Gureum_World.server.domain.member.entity;

import Gureum_World.server.domain.BaseEntity;
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
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "intro", nullable = false)
    private String intro;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "today", nullable = false)
    private Long today;

    @Column(name = "total", nullable = false)
    private Long total;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "background", nullable = false)
    private String background;

    @Column(name = "upgrade", nullable = false)
    private String upgrade;

    @Column(name = "role", nullable = false) // User
    private String role;

    @OneToMany(mappedBy = "userId")
    private List<Post> posts = new ArrayList<>();
//    @OneToMany(mappedBy = "userId")
//    private List<Reply> replies = new ArrayList<>();

    private LocalDateTime created_at;

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
