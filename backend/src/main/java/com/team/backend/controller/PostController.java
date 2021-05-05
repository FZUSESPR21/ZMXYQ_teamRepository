package com.team.backend.controller;


import com.team.backend.util.Result;
import java.util.Map;
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

  @PostMapping("/image")
  public Result savePostImage(@RequestBody Map<String,Object> requestMap
      , HttpServletRequest request) {

  }

}

