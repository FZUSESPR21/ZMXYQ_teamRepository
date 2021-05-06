package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.User;
import com.team.backend.service.impl.Base64ImageServiceImpl;
import com.team.backend.service.impl.PostServiceImpl;
import com.team.backend.util.Result;
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
@RequestMapping("/post")
public class PostController {

  User user;

  @Resource
  private Base64ImageServiceImpl imageService;

  @Resource
  private PostServiceImpl postService;

  @PostMapping("/image")
  public Result savePostImage(@RequestBody Map<String,Object> requestMap
      , HttpServletRequest request) {
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
   * @return the result
   */
  @PostMapping("/publish")
  public Result publishPost(@RequestBody Map<String,Object> requestMap
      ,HttpServletRequest request) {

    Number postThemeNumber = (Number) requestMap.get("postTheme");
    Long postTheme = postThemeNumber.longValue();
    String message = (String) requestMap.get("message");
    String imgUrls = (String) requestMap.get("imgUrls");
    ExceptionInfo exceptionInfo = postService.publishPost(user.getId(),postTheme,message,imgUrls);
    return Result.error(exceptionInfo.getCode(),exceptionInfo.getMessage());
  }


}

