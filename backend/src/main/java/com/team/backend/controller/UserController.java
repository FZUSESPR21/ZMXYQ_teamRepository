package com.team.backend.controller;


import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.PartyComment;
import com.team.backend.model.PersonalBlackItem;
import com.team.backend.model.PersonalCollection;
import com.team.backend.model.Post;
import com.team.backend.model.PostComment;
import com.team.backend.model.Result;
import com.team.backend.model.TreeHole;
import com.team.backend.model.User;
import com.team.backend.service.impl.TreeHoleServiceImpl;
import com.team.backend.service.impl.UserServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserServiceImpl userService;

  @Autowired
  TreeHoleServiceImpl treeHoleService;

  // 无需鉴权
  @PostMapping("/upload/img")
  public Result<String> uploadImg(File file) throws IOException {

    return userService.identifyImg(file);
  }

  // 无需鉴权
  @PostMapping("/identify/submit")
  public Result<Integer> identifySubmit(User user) {

    return userService.identifyUser(user);
  }

  @PostMapping("/identify/status")
  public Result<Integer> identifyStatus() {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.identifyStatus(user.getId());
  }

  @GetMapping("/data/select")
  public Result<User> queryUser() {

    User user = null;

    if (user == null) {
      Result<User> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.queryUser(user.getId());
  }

  @PostMapping("/data/update")
  public Result<Integer> updateUser(User user) {

    return userService.updateUser(user);
  }

  @GetMapping("/collect/list")
  public Result<List<PersonalCollection>> listCollection() {

    User user = null;

    if (user == null) {
      Result<List<PersonalCollection>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.listCollection(user.getId());
  }

  @PostMapping("/collect/deleted")
  public Result<Integer> deleteCollection(Long id) {

    User user = null;

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

    User user = null;

    if (user == null) {
      Result<List<Post>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.postList(user.getId());
  }

  @PostMapping("/post/deleted")
  public Result<Integer> postDeleted(Long id) {

    User user = null;

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

    User user = null;

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

    User user = null;

    if (user == null) {
      Result<List<PartyComment>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PartyCommentList(user.getId());
  }

  @PostMapping("/postcomment/deleted")
  public Result<Integer> PostCommentDeleted(Long id) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.PostCommentDeleted(user.getId(), id);
  }

  @PostMapping("/partycomment/deleted")
  public Result<Integer> PartyCommentDeleted(Long id) {

    User user = null;

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

    User user = null;

    if (user == null) {
      Result<List<PersonalBlackItem>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return userService.listBlack(user.getId());
  }

  @PostMapping("/black/deleted")
  public Result<Integer> deleteBlack(Long id) {

    User user = null;

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

    User user = null;

    if (user == null) {
      Result<List<TreeHole>> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.queryTreeHole(user.getId());
  }

  @PostMapping("/treehole/update")
  public Result<Integer> updateTreeHole(TreeHole treeHole) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.updateTreeHole(treeHole);
  }

  @PostMapping("/treehole/deleted")
  public Result<Integer> deleteTreeHole(Long id) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.deleteTreeHole(user.getId(), id);
  }

  @PostMapping("/treehole/new")
  public Result<Integer> insertTreeHole(String content) {

    User user = null;

    if (user == null) {
      Result<Integer> result = new Result<>();
      result.setCode(ExceptionInfo.valueOf("USER_NOT_LOGIN").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_LOGIN").getMessage());
      return result;
    }

    return treeHoleService.insertTreeHole(user.getId(), content);
  }
}

