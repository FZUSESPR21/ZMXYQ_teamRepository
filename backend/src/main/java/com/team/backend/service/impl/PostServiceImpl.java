package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Post;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PostMapper;
import com.team.backend.model.User;
import com.team.backend.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;

import javax.annotation.Resource;
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
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

  @Resource
  private UserMapper userMapper;

  @Resource
  private PostMapper postMapper;

  @Override
  public User getPostPublisher(Long publisherId) {
    User user = new User();
    if (publisherId != null) {
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.select("username", "birthday", "is_graduated", "province", "city", "school", "user_icon_url");
      queryWrapper.eq("id", publisherId);
      user = userMapper.selectOne(queryWrapper);
    }
    return user;
  }

  @Override
  public ExceptionInfo publishPost(Long userId, Long postTheme, String message, String imgUrls)  {
    ExceptionInfo info;
    if (userId == null || postTheme == null || StringUtils.isBlank(message)) {
      info = ExceptionInfo.POST_PUBLISH_INFO_LOST;
    }else {
      Post post = new Post();
      post.setImageUrls(imgUrls);
      post.setMessage(message);
      post.setPostTypeId(postTheme);
      post.setPublisherId(userId);
      post.setStatus(0);
      try {
        postMapper.insert(post);
        info = ExceptionInfo.OK;
      } catch (Exception e) {
        e.printStackTrace();
        info = ExceptionInfo.POST_PUBLISH_INSERT_FAIL;
      }
    }
    return info;
  }
}
