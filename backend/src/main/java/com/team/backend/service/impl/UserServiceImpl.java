package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.mapper.PostCommentMapper;
import com.team.backend.mapper.PostEyeOnMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.model.PartyComment;
import com.team.backend.model.PersonalCollection;
import com.team.backend.model.Post;
import com.team.backend.model.PostComment;
import com.team.backend.model.PostEyeOn;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.mapper.UserMapper;
import com.team.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.UserLegal;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  UserMapper userMapper;

  @Autowired
  PostMapper postMapper;

  @Autowired
  PostCommentMapper postCommentMapper;

  @Autowired
  PartyCommentMapper partyCommentMapper;

  @Autowired
  PostEyeOnMapper postEyeOnMapper;

  // 用户上传图片
  public Result<String> identifyImg(File file) throws IOException {

    Result<String> result = new Result<>();
    UserLegal userLegal = new UserLegal();
    String msg = userLegal.imgLegal(file);
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData("");
      return result;
    }

    // 上传图片至服务器，返回图片地址  等待后续until类中处理图片方法写好后再做修改
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData("图片的url");
    return result;
  }

  // 提交用户验证信息
  public Result<Integer> identifyUser(User user) {

    Result<Integer> result = new Result<>();
    UserLegal userLegal = new UserLegal();
    String msg = userLegal.idLegal(user.getId());

    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户是否已存在
    User sqlUser = userMapper.selectById(user.getId());
    if (sqlUser.getId().equals(user.getId())) {
      result.setCode(ExceptionInfo.valueOf("USER_ID_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_ID_EXISTED").getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户名是否合法
    msg = userLegal.usernameLegal(user.getUsername());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    msg = userLegal.urlLegal(user.getUserIconUrl());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断学校名是否合法
    msg = userLegal.schoolLegal(user.getSchool());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户性别合法性
    msg = userLegal.sexLegal(user.getSex());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户出生日期合法性
    msg = userLegal.birthdayLegal(user.getBirthday());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    user.setStatus(0);// 用户状态默认为未审核
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(userMapper.insert(user));
    return result;
  }

  // 获取用户审核状态
  public Result<Integer> identifyStatus(Long id) {

    Result<Integer> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    User user = userMapper.selectById(id);
    result.setData(user.getStatus());
    return result;
  }


  // 删除个人帖文
  public Result<Integer> postDeleted(Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_POST_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_POST_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条Post
    Post post = postMapper.selectById(id);
    if (post == null) {
      result.setCode(ExceptionInfo.valueOf("USER_POST_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_POST_DELETED").getMessage());
      result.setData(0);
      return result;
    }
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postMapper.deleteById(id));
    return result;
  }

  // 查询个人帖文
  public Result<List<Post>> postList(Long id) {

    Result<List<Post>> result = new Result<>();

    QueryWrapper<Post> wrapper = new QueryWrapper<>();
    wrapper.eq("publisher_id", id);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postMapper.selectList(wrapper));
    return result;
  }

  // 查询个人帖子评论
  public Result<List<PostComment>> PostCommentList(Long id) {

    Result<List<PostComment>> result = new Result<>();

    QueryWrapper<PostComment> wrapper = new QueryWrapper<>();
    wrapper.eq("id_from", id);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postCommentMapper.selectList(wrapper));
    return result;
  }

  // 查询个人组局评论
  public Result<List<PartyComment>> PartyCommentList(Long id) {

    Result<List<PartyComment>> result = new Result<>();

    QueryWrapper<PartyComment> wrapper = new QueryWrapper<>();
    wrapper.eq("id_from", id);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyCommentMapper.selectList(wrapper));
    return result;
  }

  // 删除个人帖子评论
  public Result<Integer> PostCommentDeleted(Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_POST_COMMENT_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_POST_COMMENT_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条评论
    PostComment postComment = postCommentMapper.selectById(id);
    if (postComment == null) {
      result.setCode(ExceptionInfo.valueOf("USER_POST_COMMENT_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_POST_COMMENT_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postCommentMapper.deleteById(id));
    return result;
  }

  // 删除个人组局评论
  public Result<Integer> PartyCommentDeleted(Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_PARTY_COMMENT_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PARTY_COMMENT_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条评论
    PartyComment partyComment = partyCommentMapper.selectById(id);
    if (partyComment == null) {
      result.setCode(ExceptionInfo.valueOf("USER_PARTY_COMMENT_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PARTY_COMMENT_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyCommentMapper.deleteById(id));
    return result;
  }

  // 查询个人信息
  public Result<User> queryUser(Long id) {

    Result<User> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    User user = userMapper.selectById(id);
    result.setData(user);
    return result;
  }

  // 修改个人信息
  public Result<Integer> updateUser(User user) {

    Result<Integer> result = new Result<>();
    UserLegal userLegal = new UserLegal();
    String msg = userLegal.idLegal(user.getId());

    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户是否不存在
    User sqlUser = userMapper.selectById(user.getId());
    if (sqlUser == null) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户名是否合法
    msg = userLegal.usernameLegal(user.getUsername());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    msg = userLegal.urlLegal(user.getUserIconUrl());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断学校名是否合法
    msg = userLegal.schoolLegal(user.getSchool());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户性别合法性
    msg = userLegal.sexLegal(user.getSex());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    // 判断用户出生日期合法性
    msg = userLegal.birthdayLegal(user.getBirthday());
    if (!msg.equals("OK")) {
      result.setCode(ExceptionInfo.valueOf(msg).getCode());
      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(userMapper.updateById(user));
    return result;
  }

  // 查询个人收藏
  public Result<List<PersonalCollection>> listCollection(Long id) {

    Result<List<PersonalCollection>> result = new Result<>();
    List<PersonalCollection> collectionList = new LinkedList<>();

    QueryWrapper<PostEyeOn> wrapper = new QueryWrapper<>();

    wrapper.eq("id_from", id);
    List<PostEyeOn> postEyeOns = postEyeOnMapper.selectList(wrapper);
    for (PostEyeOn postEyeOn : postEyeOns) {
      PersonalCollection collection = new PersonalCollection();
      collection.setId(postEyeOn.getId());
      collection.setPostId(postEyeOn.getPostId());
      Post post = postMapper.selectById(postEyeOn.getPostId());
      User user = userMapper.selectById(post.getPublisherId());
      collection.setNickName(user.getUsername());
      collection.setIconUrl(user.getUserIconUrl());
      collection.setPostContent(post.getMessage());
      collection.setPostTime(post.getGmtCreate());
      collectionList.add(collection);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(collectionList);
    return result;
  }

}
