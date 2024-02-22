package Gureum_World.server.domain.post.dto;

import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.post.entity.Post;
import Gureum_World.server.domain.reply.dto.ReplyRes;
import Gureum_World.server.domain.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class PostRes {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostCreateRes {
        private Long postIdx;
        private String context;
        private boolean secret;
        private List<Reply> reply;
        private String created;
        private UserRes.UserPostInfo userPostInfo;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostListRes {
        private Long postIdx;
        private String context;
        private boolean secret;
        private String created;
        private List<ReplyRes.ReplyNewRes> reply;
        private UserRes.UserPostInfo userPostInfo;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class PostListRess {
        private List<PostListRes> postListRes;
        private int TotalPages;
        public PostListRess(List<PostListRes> postListRes, int totalPages){
            this.postListRes = postListRes;
            this.TotalPages = totalPages;
        }
    }
}
