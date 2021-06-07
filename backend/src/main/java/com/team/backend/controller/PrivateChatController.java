package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.impl.PrivateChatServiceImpl;
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
@RequestMapping("${server.api-path}/message/chat")
public class PrivateChatController {

  User user = null;

  @Autowired
  PrivateChatServiceImpl privateChatService;

  @PostMapping("/list")
  public Result<List<Map<String, Object>>> listChat()
  {

    if (user == null) {
      Result<List<Map<String, Object>>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return privateChatService.listChat(user.getId());
  }
}

