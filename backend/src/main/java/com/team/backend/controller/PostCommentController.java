package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PostComment;
import com.team.backend.model.Result;
import com.team.backend.service.impl.PostCommentServiceImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("${server.api-path}/alumnicycle/posts")
public class PostCommentController {

  @Autowired
  PostCommentServiceImpl postCommentService;

  @PostMapping("/commentlist")
  public Result<List<Map<String, Object>>> listComment(@RequestBody Long postId){

    return postCommentService.listComment(postId);
  }

  @PostMapping("/comment")
  public com.team.backend.util.Result commentPost(@RequestBody Map<String,Object> requestMap
          , HttpServletRequest servletRequest) {
    PostComment postComment = new PostComment();

    String message = (String) requestMap.get("message");
    Number postIdNum = (Number) requestMap.get("postId");
    Number preIdNum = (Number) requestMap.get("preId");
    Number idToNum = (Number) requestMap.get("idTo");
    Long postId = postIdNum.longValue();
    Long preId = preIdNum.longValue();
    Long idTo = idToNum.longValue();

    postComment.setStatus(0);
    postComment.setMessage(message);
    postComment.setPostId(postId);
    postComment.setPreId(preId);
    postComment.setIdTo(idTo);

    postComment.setIdFrom(123456L);

    boolean isSuccess = false;

    com.team.backend.util.Result result;
    try {
      isSuccess = postCommentService.commentPost(postComment);
      if (isSuccess) {
        result = com.team.backend.util.Result.success();
      }else {
        result = com.team.backend.util.Result.error(ExceptionInfo.POST_COMMENT_INFO_LOST.getCode()
                ,ExceptionInfo.POST_COMMENT_INFO_LOST.getMessage());
      }
    }catch (Exception e) {
      e.printStackTrace();
      result = com.team.backend.util.Result.error(ExceptionInfo.POST_COMMENT_POST_FAIL.getCode()
              ,ExceptionInfo.POST_COMMENT_POST_FAIL.getMessage());
    }
    return result;
  }
}

