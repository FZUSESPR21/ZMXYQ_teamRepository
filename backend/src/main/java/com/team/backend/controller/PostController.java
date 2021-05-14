package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Post;
import com.team.backend.model.User;
import com.team.backend.model.vo.UserInfoToShowInVO;
import com.team.backend.service.impl.Base64ImageServiceImpl;
import com.team.backend.service.impl.PostServiceImpl;
import com.team.backend.util.DateUtil;
import com.team.backend.util.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("${server.api-path}/posts")
public class PostController {

  User user;

  @Resource
  private Base64ImageServiceImpl imageService;

  @Resource
  private PostServiceImpl postService;

  @PostMapping("/publishermsg")
  public Result getUserInfoToShowInPost(@RequestBody UserInfoToShowInVO userInfoToShowInVO) {
      Number idNum = userInfoToShowInVO.getPublisherId();
      Result result;
      User publisher;
      if (idNum != null) {
          try {
              publisher = postService.getPostPublisher(idNum.longValue());
              if (publisher == null) {
                  result = Result.error(ExceptionInfo.POST_PUBLISHER_INFO_INVALID.getCode()
                          ,ExceptionInfo.POST_PUBLISHER_INFO_INVALID.getMessage());
              }else {
                  Map<String,Object> responseMap = new HashMap<>();
                  responseMap.put("userName",publisher.getUsername());
                  responseMap.put("age", DateUtil.getAge(publisher.getBirthday()));
                  responseMap.put("isGraduated",publisher.getIsGraduated());
                  responseMap.put("province",publisher.getProvince());
                  responseMap.put("city",publisher.getCity());
                  responseMap.put("university",publisher.getSchool());
                  responseMap.put("iconUrl",publisher.getUserIconUrl());
                  result = Result.success(responseMap);
              }
          }catch (Exception e) {
              e.printStackTrace();
              result = Result.error(ExceptionInfo.POST_PUBLISHER_INFO_INVALID.getCode()
                      ,ExceptionInfo.POST_PUBLISHER_INFO_INVALID.getMessage());
          }
      }else {
          result = Result.error(ExceptionInfo.POST_PUBLISHER_INFO_LOST.getCode()
                  ,ExceptionInfo.POST_PUBLISHER_INFO_LOST.getMessage());
      }
    return result;
  }

  @PostMapping("/imgupload")
  public Result savePostImage(@RequestBody Map<String,Object> requestMap
      , HttpServletRequest request) {
      String base64Source = (String)requestMap.get("base64Str");
      String fileName = (String)requestMap.get("filename");
      Result result;
      if (base64Source != null && fileName != null) {
          Pair<ExceptionInfo,String> info = imageService.saveImage(base64Source, fileName);
          if (info.getKey().equals(ExceptionInfo.OK)) {
              result = Result.success(info.getValue());
          } else {
              result = Result.error(info.getKey().getCode(), info.getKey().getMessage());
          }
      }else {
          result = Result.error(ExceptionInfo.POST_IMAGE_CONTENT_EMPTY.getCode()
                  ,ExceptionInfo.POST_IMAGE_CONTENT_EMPTY.getMessage());
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
    ExceptionInfo exceptionInfo;
    try {
        exceptionInfo = postService.publishPost(userId,postTheme,message,imgUrls);
    }catch (Exception e) {
        e.printStackTrace();
        exceptionInfo = ExceptionInfo.POST_PUBLISH_INSERT_FAIL;
    }
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

  @PostMapping("/heatposts")
  public Result selectTopTenLikePost(@RequestBody Map<String,Object> requestMap,HttpServletRequest servletRequest) {
      Result result;
      Number userId = (Number) requestMap.get("userId");
      if (userId != null) {
          Long id = userId.longValue();
          try {
              List<Map<String, Object>> postList = postService.selectTopTenLikePost(id);
              result = Result.success(postList);
          }catch (Exception e) {
              result = Result.error(ExceptionInfo.POST_LIST_QUERY_FAIL.getCode()
                      ,ExceptionInfo.POST_LIST_QUERY_FAIL.getMessage());
          }
      }else {
          result = Result.error(ExceptionInfo.POST_LIST_TOP_TEN_INFO_LOST.getCode()
                  ,ExceptionInfo.POST_LIST_TOP_TEN_INFO_LOST.getMessage());
      }
      return result;
  }
}

