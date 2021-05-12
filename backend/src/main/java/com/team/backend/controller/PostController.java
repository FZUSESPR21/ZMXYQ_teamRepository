package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Post;
import com.team.backend.model.User;
import com.team.backend.service.impl.Base64ImageServiceImpl;
import com.team.backend.service.impl.PostServiceImpl;
import com.team.backend.util.Result;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/posts")
public class PostController {

  User user;

  @Resource
  private Base64ImageServiceImpl imageService;

  @Resource
  private PostServiceImpl postService;

  @PostMapping("/imgupload")
  public Result savePostImage(@RequestBody Map<String,Object> requestMap
      , HttpServletRequest request) {
        request.getSession();
        String base64Source = (String)requestMap.get("base64Str");
        String fileName = (String)requestMap.get("filename");
        ExceptionInfo info = imageService.saveImage(base64Source,fileName);
        Result result;
        if (info.equals(ExceptionInfo.OK)) {
          result = Result.success(fileName);
        }else {
          result = Result.error(info.getCode(), info.getMessage());
        }
        return result;
  }

  /**
   * 发帖接口
   *
   * 接口：/api/post/publish
   *
   * json示例：
   * {
   *     "userId": 1,
   *     "postTheme": 1,
   *     "message":"想睡觉",
   *     "imgUrls":""
   * }
   *
   * @param requestMap the request map
   * @param request    the request
   * @return the resul
   */
  @PostMapping("/publish")
  public Result publishPost(@RequestBody Map<String,Object> requestMap
      ,HttpServletRequest request) {

    Number postThemeNumber = (Number) requestMap.get("postTheme");
    Long postTheme = postThemeNumber.longValue();
    String message = (String) requestMap.get("message");
    String imgUrls = (String) requestMap.get("imgUrls");
    Number userIdNum = (Number) requestMap.get("userId");
    Long userId = userIdNum.longValue();
    ExceptionInfo exceptionInfo = postService.publishPost(userId,postTheme,message,imgUrls);
    return Result.error(exceptionInfo.getCode(),exceptionInfo.getMessage());
  }

  @PostMapping("/all")
  public Result listPostPage(@RequestBody Map<String,Object> requestMap
          ,HttpServletRequest servletRequest) {
      Number pageNum = (Number) requestMap.get("pageNum");
      Number pageSize = (Number) requestMap.get("pageSize");
      Long userId = user.getId();
      Result result;
      if (pageNum != null && pageSize !=null && userId != null && pageNum.longValue() > 0L
              && pageSize.longValue() > 0L && userId > 0) {
          List<Map<String,Object>> posts = postService.listPostPageOrderByGmtCreateIdDesc(pageNum.longValue(),pageSize.longValue(),userId);
          result = Result.success(posts);
      }else {
          result = Result.error(ExceptionInfo.POST_LIST_INFO_LOST.getCode()
                  ,ExceptionInfo.POST_LIST_INFO_LOST.getMessage());
      }
      return result;
  }

  @PostMapping("/search")
    public Result searchPost(@RequestBody Map<String,Object> requestMap
          ,HttpServletRequest servletRequest) {
      Integer pageNum = (Integer) requestMap.get("pageNum");
      Integer pageSize = (Integer) requestMap.get("pageSize");
      String content = (String) requestMap.get("content");
      Result result;
      Long userId = user.getId();
      if(pageNum != null && pageSize != null && content != null) {
          result = Result.success(postService.fuzzyListPostPageOrderByGmtCreateDesc(content,pageNum,pageSize,userId));
      }else {
          result = Result.error(ExceptionInfo.POST_LIST_SEARCH_INFO_LOST.getCode()
                  ,ExceptionInfo.POST_LIST_SEARCH_INFO_LOST.getMessage());
      }
      return result;
  }

  @PostMapping("/changetheme")
  public Result listPostPageByTypeId(@RequestBody Map<String,Object> requestMap
          ,HttpServletRequest servletRequest) {
      Integer pageNum = (Integer) requestMap.get("pageNum");
      Integer pageSize = (Integer) requestMap.get("pageSize");
      Number typeIdNum = (Number) requestMap.get("typeId");
      Long userId = user.getId();
      Result result;
      if (pageNum != null && pageSize != null && typeIdNum !=null) {
          Long typeId = typeIdNum.longValue();
          try {
              List<Map<String, Object>> postPage = postService.listPostPageByTypeId(typeId, userId, pageNum, pageSize);
              result = Result.success(postPage);
          }catch (Exception e) {
              e.printStackTrace();
              result = Result.error(ExceptionInfo.POST_LIST_QUERY_FAIL.getCode()
                      ,ExceptionInfo.POST_LIST_QUERY_FAIL.getMessage());
          }

      }else {
          result = Result.error(ExceptionInfo.POST_LIST_TYPE_INFO_LOST.getCode()
                  ,ExceptionInfo.POST_LIST_TYPE_INFO_LOST.getMessage());
      }
      return result;
  }

}

