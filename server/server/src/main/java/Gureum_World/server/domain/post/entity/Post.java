package Gureum_World.server.domain.post.entity;

import Gureum_World.server.domain.BaseEntity;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member userId;

    private String context;
    private String status;
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "postId")
    private List<Reply> replies = new ArrayList<>();

}
