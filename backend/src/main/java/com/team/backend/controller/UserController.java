package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.*;
import com.team.backend.service.impl.TreeHoleServiceImpl;
import com.team.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@RestController
@RequestMapping("${server.api-path}/user")
public class UserController {

  User user = null;
  @Autowired
  UserServiceImpl userService;

  @Autowired
  TreeHoleServiceImpl treeHoleService;

  @PostMapping("/login")
  public Result<String> login(HttpServletRequest request, @RequestBody String code) {

    HttpSession session = request.getSession();
    Map<String, Object> map = userService.login(code);
    User user = (User) map.get("user");
    Result<String> result = (Result<String>) map.get("result");

    session.setAttribute("user", user);
    return result;
  }

  // 无需鉴权
  @PostMapping("/upload/img")
  public Result<String> uploadImg(File file) throws IOException {

    return userService.identifyImg(file);
  }

  @PostMapping("/identify/submit")
  public Result<Integer> identifySubmit(@RequestBody User user1) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    user1.setId(user.getId());
    return userService.identifyUser(user1);
  }

  @PostMapping("/identify/status")
  public Result<Integer> identifyStatus() {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.identifyStatus(user.getId());
  }

  @GetMapping("/data/select")
  public Result<Map<String, Object>> queryUser() {

    Result<Map<String, Object>> result = new Result<>();
    Map<String, Object> resultMap = new HashMap<>();
    Map<String, Integer> map = new HashMap<>();

    if (user == null) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    resultMap.put("user", userService.queryUser(user.getId()).getData());

    Result<List<Post>> postResult = userService.postList(user.getId());
    map.put("postNumber", postResult.getData().size());
    Result<List<PostComment>> postCommentResult = userService.PostCommentList(user.getId());
    map.put("postCommentNumber", postCommentResult.getData().size());
    Result<List<PartyComment>> partyCommentResult = userService.PartyCommentList(user.getId());
    map.put("partyCommentNumber", partyCommentResult.getData().size());
    Result<List<PersonalCollection>> collectionResult = userService.listCollection(user.getId());
    map.put("collectionNumber", collectionResult.getData().size());
    resultMap.put("numberList", map);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(resultMap);
    return result;
  }

  @PostMapping("/data/update")
  public Result<Integer> updateUser(@RequestBody User user1) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    user1.setId(user.getId());
    return userService.updateUser(user1);
  }

  @GetMapping("/collect/list")
  public Result<List<PersonalCollection>> listCollection() {

    if (user == null) {
      Result<List<PersonalCollection>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.listCollection(user.getId());
  }

  @PostMapping("/collect/deleted")
  public Result<Integer> deleteCollection(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.deleteCollection(user.getId(), id);
  }

  @GetMapping("/post/list")
  public Result<List<Post>> postList() {

    if (user == null) {
      Result<List<Post>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.postList(user.getId());
  }

  @PostMapping("/post/deleted")
  public Result<Integer> postDeleted(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.postDeleted(user.getId(), id);
  }

  @GetMapping("/postcomment/list")
  public Result<List<PostComment>> PostCommentList() {

    if (user == null) {
      Result<List<PostComment>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PostCommentList(user.getId());
  }

  @GetMapping("/partycomment/list")
  public Result<List<PartyComment>> PartyCommentList() {

    if (user == null) {
      Result<List<PartyComment>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PartyCommentList(user.getId());
  }

  @PostMapping("/postcomment/deleted")
  public Result<Integer> PostCommentDeleted(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PostCommentDeleted(user.getId(), id);
  }

  @PostMapping("/partycomment/deleted")
  public Result<Integer> PartyCommentDeleted(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PartyCommentDeleted(user.getId(), id);
  }

  @GetMapping("/black/list")
  public Result<List<PersonalBlackItem>> listBlack() {

    if (user == null) {
      Result<List<PersonalBlackItem>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.listBlack(user.getId());
  }

  @PostMapping("/black/deleted")
  public Result<Integer> deleteBlack(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.deleteBlack(user.getId(), id);
  }

  @GetMapping("/treehole/content")
  public Result<List<TreeHole>> queryTreeHole() {

    if (user == null) {
      Result<List<TreeHole>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.queryTreeHole(user.getId());
  }

  @PostMapping("/treehole/update")
  public Result<Integer> updateTreeHole(@RequestBody TreeHole treeHole) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }
    treeHole.setFromId(user.getId());

    return treeHoleService.updateTreeHole(treeHole);
  }

  @PostMapping("/treehole/deleted")
  public Result<Integer> deleteTreeHole(@RequestBody Long id) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.deleteTreeHole(user.getId(), id);
  }

  @PostMapping("/treehole/new")
  public Result<Integer> insertTreeHole(@RequestBody String content) {

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.insertTreeHole(user.getId(), content);
  }

}

