package com.team.backend.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.service.impl.Base64ImageService;
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

  @Resource
  private Base64ImageService imageService;

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

}

