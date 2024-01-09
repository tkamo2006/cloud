package Gureum_World.server.domain.reply.entity;

import Gureum_World.server.domain.BaseEntity;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "reply")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member userId;


    private String context;
    private String status;
    private LocalDateTime created_at;
}