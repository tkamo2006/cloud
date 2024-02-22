package Gureum_World.server.domain.post.controller;

import Gureum_World.server.domain.global.BaseException;
import Gureum_World.server.domain.global.BaseResponse;
import Gureum_World.server.domain.member.dto.MemberDTO;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.service.MemberService;
import Gureum_World.server.domain.post.dto.PostReq;
import Gureum_World.server.domain.post.dto.PostRes;
import Gureum_World.server.domain.post.entity.Post;
import Gureum_World.server.domain.post.repository.PostRepository;
import Gureum_World.server.domain.post.service.PostService;
import Gureum_World.server.domain.reply.dto.ReplyReq;
import Gureum_World.server.domain.reply.dto.ReplyRes;
import Gureum_World.server.domain.reply.entity.Reply;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static Gureum_World.server.domain.global.BaseResponseStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class PostController {
    @Autowired
    private PostService postService;

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("/{userId}")
    public BaseResponse<List<PostRes.PostListRes>> getAllPosts(@PathVariable String userId,HttpServletRequest request) throws Exception {
        List<PostRes.PostListRes> posts = postService.getAllPosts(userId, request);
        return new BaseResponse<>(posts);
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @GetMapping("page/{userId}")
    public BaseResponse<PostRes.PostListRess> getAllPostss(@PathVariable String userId,HttpServletRequest request, @RequestParam int page) throws Exception {
        try {
            if(page < 1){
                throw new BaseException(BAD_REQUEST);
            }
            Page<PostRes.PostListRes> posts = postService.getAllPostss(userId, request, page - 1);
            PostRes.PostListRess postListRess = new PostRes.PostListRess(posts.getContent(), posts.getTotalPages());
            return new BaseResponse<>(postListRess);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 게시판 생성
    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/create")
    public BaseResponse<PostRes.PostCreateRes> createPost(@RequestBody PostReq.PostNewReq context, HttpServletRequest request) throws Exception {
        return new BaseResponse<>(postService.createBoard(context, request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/update")
    public BaseResponse<String> updatePost(@RequestBody PostReq.PostUpdateReq context, HttpServletRequest request) throws Exception {
        postService.updatePost(context, request);
        return new BaseResponse<>("방명록이 수정되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @DeleteMapping("/delete")
    public BaseResponse<String> deletePost(@RequestBody PostReq.PostDeleteReq context, HttpServletRequest request) throws Exception {
        postService.deletePost(context, request);
        return new BaseResponse<>("방명록이 삭제되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/replies")
    public BaseResponse<ReplyRes.ReplyNewRes> addReply(@RequestBody ReplyReq.ReplyNewReq context, HttpServletRequest request) throws Exception {
        return new BaseResponse<>(postService.addReply(context, request));
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @PostMapping("/replies/update")
    public BaseResponse<String> updateReply(@RequestBody ReplyReq.ReplyUpdateReq context, HttpServletRequest request) throws Exception {
        postService.updateReply(context, request);
        return new BaseResponse<>("댓글이 수정되었습니다.");
    }

    @CrossOrigin(origins = {"https://cloudworld.vercel.app", "http://localhost:5173"})
    @DeleteMapping("/replies/delete")
    public BaseResponse<String> deleteReply(@RequestBody ReplyReq.ReplyDeleteReq context, HttpServletRequest request) throws Exception {
        postService.deleteReply(context, request);
        return new BaseResponse<>("댓글이 삭제되었습니다.");
    }

}
