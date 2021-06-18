package com.team.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.BlackListMapper;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.mapper.PostCommentMapper;
import com.team.backend.mapper.PostEyeOnMapper;
import com.team.backend.mapper.PostLikeMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.model.BlackList;
import com.team.backend.model.PartyComment;
import com.team.backend.model.PersonalBlackItem;
import com.team.backend.model.PersonalCollection;
import com.team.backend.model.Post;
import com.team.backend.model.PostComment;
import com.team.backend.model.PostEyeOn;
import com.team.backend.model.PostLike;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.mapper.UserMapper;
import com.team.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.util.HttpRequestUtil;
import com.team.backend.util.UserLegal;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  BlackListMapper blackListMapper;

  @Autowired
  PostLikeMapper postLikeMapper;


  @Value("${wxMiniReal.appId}")
  private String appId;

  @Value("${wxMiniReal.secret}")
  private String secret;

  // 用户登录验证
  public Result<User> login(String code) {

    Result<User> result = new Result<>();

    if (code == null || code == "") {
      result.setCode(ExceptionInfo.valueOf("USER_CODE_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_CODE_NULL").getMessage());
      result.setData(new User());
      return result;
    }
    String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret
        + "&js_code=" + code + "&grant_type=authorization_code";
    String wxResult = HttpRequestUtil.httpRequest(url, "GET", null);
    JSONObject jsonObject = JSONObject.parseObject(wxResult);
    String openId = jsonObject.getString("openid");

    if (openId == null) {
      result.setCode(ExceptionInfo.valueOf("USER_OPEN_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_OPEN_ID_NULL").getMessage());
      result.setData(new User());
      return result;
    }

    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("open_id", openId);
    wrapper.last(" limit 1");

    User user = userMapper.selectOne(wrapper);
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    // 当前用户为新用户
    if (user == null) {
      User newUser = new User();
      newUser.setOpenId(openId);
      newUser.setStatus(0);// 用户状态默认为未审核
      newUser.setSex(2);// 性别默认为无
      newUser.setRpValue(100L);// 初始人品值为100
      userMapper.insert(newUser);
      user = userMapper.selectOne(wrapper);
      result.setData(user);
      return result;
    }

    // 当前用户已存在
    result.setData(user);
    return result;
  }

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

//    // 判断头像url是否合法
//    msg = userLegal.iconUrlLegal(user.getUserIconUrl());
//    if (!msg.equals("OK")) {
//      result.setCode(ExceptionInfo.valueOf(msg).getCode());
//      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
//      result.setData(0);
//      return result;
//    }

//    // 判断证件照url是否合法
//    msg = userLegal.imgUrlLegal(user.getCertificateImageUrl());
//    if (!msg.equals("OK")) {
//      result.setCode(ExceptionInfo.valueOf(msg).getCode());
//      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
//      result.setData(0);
//      return result;
//    }

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

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(userMapper.updateById(user));
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
  public Result<Integer> postDeleted(Long userId, Long id) {

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

    if (!post.getPublisherId().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
      result.setData(0);
      return result;
    }

    QueryWrapper<PostEyeOn> postEyeOnWrapper = new QueryWrapper<>();
    postEyeOnWrapper.eq("post_id", id);
    postEyeOnMapper.delete(postEyeOnWrapper);

    QueryWrapper<PostComment> postCommentWrapper = new QueryWrapper<>();
    postCommentWrapper.eq("post_id", id);
    postCommentMapper.delete(postCommentWrapper);

    QueryWrapper<PostLike> postLikeWrapper = new QueryWrapper<>();
    postLikeWrapper.eq("post_id", id);
    postLikeMapper.delete(postLikeWrapper);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postMapper.deleteById(id));
    return result;
  }

  // 查询个人帖文
  public Result<List<Post>> postList(Long id) {

    Result<List<Post>> result = new Result<>();

    User user = userMapper.selectById(id);

    QueryWrapper<Post> wrapper = new QueryWrapper<>();
    wrapper.eq("publisher_id", id);

    List<Post> postList = postMapper.selectList(wrapper);
    for (Post post : postList) {
      post.setPublisherName(user.getUsername());
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postList);
    return result;
  }

  // 查询个人帖子评论
  public Result<List<PostComment>> PostCommentList(Long id) {

    Result<List<PostComment>> result = new Result<>();

    User user = userMapper.selectById(id);

    QueryWrapper<PostComment> wrapper = new QueryWrapper<>();
    wrapper.eq("id_from", id);

    List<PostComment> postCommentList = postCommentMapper.selectList(wrapper);
    for (PostComment postComment : postCommentList) {
      postComment.setUsername(user.getUsername());
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postCommentList);
    return result;
  }

  // 查询个人组局评论
  public Result<List<PartyComment>> PartyCommentList(Long id) {

    Result<List<PartyComment>> result = new Result<>();

    User user = userMapper.selectById(id);

    QueryWrapper<PartyComment> wrapper = new QueryWrapper<>();
    wrapper.eq("id_from", id);

    List<PartyComment> partyCommentList = partyCommentMapper.selectList(wrapper);
    for (PartyComment partyComment : partyCommentList) {
      partyComment.setUsername(user.getUsername());
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyCommentList);
    return result;
  }

  // 删除个人帖子评论
  public Result<Integer> PostCommentDeleted(Long userId, Long id) {

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

    // 判断该评论是否属于当前用户
    if (!postComment.getIdFrom().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postCommentMapper.deleteById(id));
    return result;
  }

  // 删除个人组局评论
  public Result<Integer> PartyCommentDeleted(Long userId, Long id) {

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

    // 判断该评论是否属于当前用户
    if (!partyComment.getIdFrom().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
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

//    msg = userLegal.iconUrlLegal(user.getUserIconUrl());
//    if (!msg.equals("OK")) {
//      result.setCode(ExceptionInfo.valueOf(msg).getCode());
//      result.setMessage(ExceptionInfo.valueOf(msg).getMessage());
//      result.setData(0);
//      return result;
//    }

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

  // 删除个人收藏
  public Result<Integer> deleteCollection(Long userId, Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_COLLECTION_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_COLLECTION_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条收藏
    PostEyeOn postEyeOn = postEyeOnMapper.selectById(id);
    System.out.println(postEyeOn);
    if (postEyeOn == null) {
      result.setCode(ExceptionInfo.valueOf("USER_COLLECTION_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_COLLECTION_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    if (!postEyeOn.getIdFrom().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(postEyeOnMapper.deleteById(id));
    return result;
  }

  // 查询黑名单
  public Result<List<PersonalBlackItem>> listBlack(Long id) {

    Result<List<PersonalBlackItem>> result = new Result<>();
    List<PersonalBlackItem> blackItemList = new LinkedList<>();

    QueryWrapper<BlackList> wrapper = new QueryWrapper<>();

    wrapper.eq("user_id", id);
    List<BlackList> blackLists = blackListMapper.selectList(wrapper);
    for (BlackList blackList : blackLists) {
      PersonalBlackItem blackItem = new PersonalBlackItem();
      blackItem.setId(blackList.getId());
      blackItem.setBeUserId(blackList.getBeUserId());
      User user = userMapper.selectById(blackList.getBeUserId());
      blackItem.setIconUrl(user.getUserIconUrl());
      blackItem.setNickName(user.getUsername());
      blackItemList.add(blackItem);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(blackItemList);
    return result;
  }

  // 删除黑名单
  public Result<Integer> deleteBlack(Long userId, Long id) {

    Result<Integer> result = new Result<>();

    if (id == null) {
      result.setCode(ExceptionInfo.valueOf("USER_BLACK_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_BLACK_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在这条黑名单
    BlackList blackList = blackListMapper.selectById(id);
    if (blackList == null) {
      result.setCode(ExceptionInfo.valueOf("USER_BLACK_DELETED").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_BLACK_DELETED").getMessage());
      result.setData(0);
      return result;
    }

    if (!blackList.getUserId().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("USER_NOT_BELONG").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_NOT_BELONG").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(blackListMapper.deleteById(id));
    return result;
  }

}
