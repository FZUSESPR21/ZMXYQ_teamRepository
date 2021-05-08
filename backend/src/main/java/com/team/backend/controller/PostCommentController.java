package com.team.backend.controller;


import com.team.backend.model.Result;
import com.team.backend.service.impl.PostCommentServiceImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/alumnicycle/posts")
public class PostCommentController {

  @Autowired
  PostCommentServiceImpl postCommentService;

  @PostMapping("/commentlist")
  public Result<List<Map<String, Object>>> listComment(Long postId){

    return postCommentService.listComment(postId);
  }


}

