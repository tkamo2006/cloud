package Gureum_World.server.domain.post.entity;

import Gureum_World.server.domain.BaseEntity;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.reply.entity.Reply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member userId;

    private Long postIdx;
    private String kakaoId;
    private String context;
    private boolean secret;
    private String created;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

}
