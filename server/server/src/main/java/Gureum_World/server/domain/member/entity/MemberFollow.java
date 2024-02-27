package Gureum_World.server.domain.member.entity;

import Gureum_World.server.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member_follow",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "kakao_id"}))
public class MemberFollow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberFollow_id")
    private Long MemberFollowId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member userId;

    private Long followIdx;
    private String kakaoId;
    private boolean bookMark;

}
