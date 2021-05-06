package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PostLike;
import com.team.backend.model.User;
import com.team.backend.service.impl.PostLikeServiceImpl;
import com.team.backend.util.Result;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 帖子点赞控制器
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/alumnicycle/posts/like")
public class PostLikeController {

  User user;

  @Resource
  private PostLikeServiceImpl postLikeService;

  /**
   * 帖子点赞接口Controller
   *
   * 接口：/api/alumnicycle/posts/like
   *
   * 请求JSON示例：
   * {
   *     "postId": 4
   * }
   *
   * @param requestMap the request map
   * @param request    the request
   * @return the result
   */
  @PostMapping
  public Result likePost(@RequestBody Map<String,Object> requestMap, HttpServletRequest request) {
    Number postIdNumber = (Number) requestMap.get("postId");
    Long postId = postIdNumber.longValue();
    //设置点赞信息
    PostLike postLike = new PostLike();
    postLike.setPostId(postId);
    postLike.setIdFrom(user.getId());
    
    Result result = Result.error(ExceptionInfo.POST_LIKE_POST_FAIL.getCode()
        ,ExceptionInfo.POST_LIKE_POST_FAIL.getMessage());
    try {
      if (postLikeService.likePost(postLike)) {
        result = Result.success();
      }else {
        result = Result.error(ExceptionInfo.POST_LIKE_ALREADY.getCode()
            ,ExceptionInfo.POST_LIKE_ALREADY.getMessage());
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }


}

