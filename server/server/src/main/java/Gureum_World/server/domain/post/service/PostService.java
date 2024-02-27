package Gureum_World.server.domain.post.service;

import Gureum_World.server.domain.global.BaseException;
import Gureum_World.server.domain.global.security.JWT.JwtTokenProvider;
import Gureum_World.server.domain.member.dto.UserRes;
import Gureum_World.server.domain.member.entity.Member;
import Gureum_World.server.domain.member.repository.MemberRepository;
import Gureum_World.server.domain.post.dto.PostReq;
import Gureum_World.server.domain.post.dto.PostRes;
import Gureum_World.server.domain.post.entity.Post;
import Gureum_World.server.domain.post.repository.PostRepository;
import Gureum_World.server.domain.reply.dto.ReplyReq;
import Gureum_World.server.domain.reply.dto.ReplyRes;
import Gureum_World.server.domain.reply.entity.Reply;
import Gureum_World.server.domain.reply.repository.ReplyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static Gureum_World.server.domain.global.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public PostRes.PostCreateRes createBoard(PostReq.PostNewReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new Exception("가입되지 않은 회원입니다."));
        Member MasterUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new Exception("가입되지 않은 회원입니다."));

        Post post1 = new Post();

        if (userEntity == null) {
            throw new Exception("사용자를 찾을 수 없습니다.");
        }
        PostRes.PostCreateRes post = new PostRes.PostCreateRes();
        post.setPostIdx(postRepository.countByUserId(MasterUser)+1);
        post.setContext(context.getContext());
        post.setCreated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        post.setSecret(context.isSecret());
        post.setReply(post1.getReplies());
        UserRes.UserPostInfo userPostInfo = new UserRes.UserPostInfo();
        userPostInfo.setLink(userEntity.getLink());
        userPostInfo.setNickname(userEntity.getNickname());
        userPostInfo.setLevel(userEntity.getLevel());
        userPostInfo.setKakaoId(userEntity.getKakaoId());
        userPostInfo.setColor(userEntity.getColor());
        userPostInfo.setBackground(userEntity.getBackground());

        post.setUserPostInfo(userPostInfo);

//        System.out.println(postRepository.countByUserId(MasterUser));
        post1.setPostIdx(post.getPostIdx());
        post1.setKakaoId(kakaoId);
        post1.setContext(context.getContext());
        post1.setCreated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        post1.setSecret(context.isSecret());
        post1.setUserId(MasterUser);
//        post1.setKakaoId(kakaoId);
        postRepository.saveAndFlush(post1);

        return post;
    }

    public List<PostRes.PostListRes> getAllPosts(String userId, HttpServletRequest request) throws Exception {
        Member userEntity = memberRepository.findByKakaoId(userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        List<Post> posts = postRepository.findAllByUserIdOrderByPostIdDesc(userEntity);

        return posts.stream()
                .map(post -> {
                    // 중간에 추가할 문장들...
                    try {
                        Member writer = memberRepository.findByKakaoId(post.getKakaoId())
                                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

                        List<Reply> replies = replyRepository.findByPostIdOrderByReplyIdxAsc(post);

                        // 댓글 리스트를 스트림으로 변환하여 필요한 정보만 가져오기
                        List<ReplyRes.ReplyNewRes> replyList = replies.stream()
                                .map(reply -> {
                                    try {
                                        Member R_writer = memberRepository.findByKakaoId(reply.getKakaoId())
                                                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

                                        return new ReplyRes.ReplyNewRes(
                                                reply.getReplyIdx(),
                                                reply.getKakaoId(),
                                                reply.getContext(),
                                                R_writer.getNickname(),
                                                reply.getLink(),
                                                reply.getCreated()
                                        );
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .toList();
                        return new PostRes.PostListRes(
                                post.getPostIdx(),
                                post.getContext(),
                                post.isSecret(),
                                post.getCreated(),
                                replyList,
                                new UserRes.UserPostInfo(
                                        writer.getNickname(),
                                        writer.getLink(),
                                        writer.getColor(),
                                        writer.getBackground(),
                                        writer.getLevel(),
                                        writer.getKakaoId()
                                )
                        );
                    } catch (BaseException e) {
                        throw new RuntimeException(e);
                    }

                })
                .collect(Collectors.toList());
    }

    public Page<PostRes.PostListRes> getAllPostss(String userId, HttpServletRequest request, int page) throws Exception {
        Member userEntity = memberRepository.findByKakaoId(userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        // Pageable 객체를 생성하여 사용할 페이지 번호와 사이즈를 지정
        PageRequest pageable = PageRequest.of(page, 5);

        // Pageable을 이용하여 데이터를 검색
        Page<Post> postPage = postRepository.findAllByUserIdOrderByPostIdDesc(userEntity, pageable);

        int currentPage = postPage.getNumber();
        int totalPages = postPage.getTotalPages();
        // 페이지 번호가 total 페이지를 초과하면 수정
        if (currentPage >= totalPages) {
            // 혹은 원하는 로직으로 수정
            pageable = PageRequest.of(totalPages - 1, 5);
        }
        postPage = postRepository.findAllByUserIdOrderByPostIdDesc(userEntity, pageable);


        return postPage.map(post -> {
            try {
                Member writer = memberRepository.findByKakaoId(post.getKakaoId())
                        .orElseThrow(() -> new BaseException(INVALID_USER_JWT));

                List<Reply> replies = replyRepository.findByPostIdOrderByReplyIdxAsc(post);

                // 댓글 리스트를 스트림으로 변환하여 필요한 정보만 가져오기
                List<ReplyRes.ReplyNewRes> replyList = replies.stream()
                        .map(reply -> {
                            try {
                                Member R_writer = memberRepository.findByKakaoId(reply.getKakaoId())
                                        .orElseThrow(() -> new BaseException(INVALID_USER_JWT));

                                return new ReplyRes.ReplyNewRes(
                                        reply.getReplyIdx(),
                                        reply.getKakaoId(),
                                        reply.getContext(),
                                        R_writer.getNickname(),
                                        reply.getLink(),
                                        reply.getCreated()
                                );
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                return new PostRes.PostListRes(
                        post.getPostIdx(),
                        post.getContext(),
                        post.isSecret(),
                        post.getCreated(),
                        replyList,
                        new UserRes.UserPostInfo(
                                writer.getNickname(),
                                writer.getLink(),
                                writer.getColor(),
                                writer.getBackground(),
                                writer.getLevel(),
                                writer.getKakaoId()
                        )
                );
            } catch (BaseException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public ReplyRes.ReplyNewRes addReply(ReplyReq.ReplyNewReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member userEntity = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));
        Member homeUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));


        Post post = postRepository.findByUserIdAndPostIdx(homeUser, context.getPostIdx());
        if (post == null) {
            throw new Exception("게시물을 찾을 수 없습니다.");
        }
        Reply reply = new Reply();

        reply.setPostId(post);
        reply.setReplyIdx(replyRepository.countByPostId(post)+1);
        reply.setContext(context.getContext());
        reply.setStatus("A");
        reply.setCreated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        reply.setKakaoId(kakaoId);
        reply.setLink(userEntity.getLink());
        reply.setNickname(userEntity.getNickname());

        replyRepository.saveAndFlush(reply);

        ReplyRes.ReplyNewRes replyNewRes = new ReplyRes.ReplyNewRes();
        replyNewRes.setReplyIdx(reply.getReplyIdx());
        replyNewRes.setContext(context.getContext());
        replyNewRes.setCreated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        replyNewRes.setLink(userEntity.getLink());
        replyNewRes.setNickname(userEntity.getNickname());
        replyNewRes.setKakaoId(kakaoId);
        return replyNewRes;
    }

    public Optional<Post> updatePost(PostReq.PostUpdateReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member homeUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        Post post = postRepository.findByUserIdAndPostIdx(homeUser, context.getPostIdx());

        if(post.getKakaoId().equals(kakaoId) || context.getUserId().equals(kakaoId)){
            post.setContext(context.getContext());
        }else {
            throw new Exception("잘못된 요청입니다.");
        }
        return Optional.of(postRepository.saveAndFlush(post));

    }

    @Transactional
    public void deletePost(PostReq.PostDeleteReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);
        Member homeUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        Post post = postRepository.findByUserIdAndPostIdx(homeUser, context.getPostIdx());

        if(post.getKakaoId().equals(kakaoId) || context.getUserId().equals(kakaoId)){
            postRepository.delete(post);
            List<Post> posts = postRepository.findByUserIdOrderByPostIdxAsc(homeUser);
            long count = 1L;
            for (Post a: posts) {
                a.setPostIdx(count++);
            }
            postRepository.saveAll(posts);
        }else {
            throw new Exception("삭제할 수 없습니다.");
        }
    }


    public Optional<Reply> updateReply(ReplyReq.ReplyUpdateReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);

        Member homeUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        Post post = postRepository.findByUserIdAndPostIdx(homeUser, context.getPostIdx());
        Reply reply = replyRepository.findByPostIdAndReplyIdx(post, context.getReplyIdx());

        if(reply.getKakaoId().equals(kakaoId) || context.getUserId().equals(kakaoId)){
            reply.setContext(context.getContext());
        }else {
            throw new Exception("잘못된 요청입니다.");
        }
        return Optional.of(replyRepository.saveAndFlush(reply));

    }

    @Transactional
    public void deleteReply(ReplyReq.ReplyDeleteReq context, HttpServletRequest request) throws Exception {
        String kakaoId = jwtTokenProvider.getCurrentUser(request);

        Member homeUser = memberRepository.findByKakaoId(context.getUserId())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        Post post = postRepository.findByUserIdAndPostIdx(homeUser, context.getPostIdx());

        Reply reply = replyRepository.findByPostIdAndReplyIdx(post, context.getReplyIdx());

        if(reply.getKakaoId().equals(kakaoId) || context.getUserId().equals(kakaoId)){
            replyRepository.delete(reply);
            List<Reply> replies = replyRepository.findByPostIdOrderByReplyIdxAsc(post);
            long count = 1L;
            for (Reply a: replies) {
                a.setReplyIdx(count++);
            }
            replyRepository.saveAll(replies);
        }else {
            throw new Exception("삭제할 수 없습니다.");
        }

    }
}
