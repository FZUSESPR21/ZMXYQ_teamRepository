package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Post;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PostMapper;
import com.team.backend.model.PostComment;
import com.team.backend.model.User;
import com.team.backend.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @author Tars
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

  @Override
  public List<Map<String,Object>> listPostPageOrderByGmtCreateIdDesc(Long pageNum, Long pageSize, Long userId) {
    IPage<Post> postIPage = new Page<>(pageNum,pageSize);
    List<Post> posts = postMapper.listPostPageOrderByGmtCreateDesc(postIPage, userId);
    return pack(posts);
  }

  @Override
  public List<Map<String, Object>> fuzzyListPostPageOrderByGmtCreateDesc(String content, Integer pageNum
          , Integer pageSize,Long currentUserId) {
    IPage<Post> postIPage = new Page<>(pageNum,pageSize);
    Map<String, Object> queryMap = new HashMap<>();
    queryMap.put("content",content);
    queryMap.put("currentUserId",currentUserId);
    return pack(postMapper.fuzzyListPostPageOrderByGmtCreateDesc(postIPage,queryMap));
  }

  @Override
  public List<Map<String, Object>> listPostPageByTypeId(Long typeId, Long currentUserId, Integer pageNum, Integer pageSize) {
    IPage<Post> postIPage = new Page<>(pageNum,pageSize);
    Map<String,Object> queryMap = new HashMap<>();
    queryMap.put("typeId",typeId);
    queryMap.put("currentUserId",currentUserId);
    return pack(postMapper.listPostPageByTypeId(postIPage,queryMap));
  }

  private List<Map<String,Object> > pack(List<Post> posts) {
    List<Map<String,Object>> resultList;
    if (posts != null) {
      resultList = new ArrayList<>(posts.size());
      Map<String, Object> tempResult;
      for (Post post : posts) {
        tempResult = new HashMap<>();
        tempResult.put("postId", post.getId());
        tempResult.put("publisherId", post.getPublisherId());
        tempResult.put("publisherName",post.getPublisherName());
        tempResult.put("postType", post.getPostTypeName());
        tempResult.put("message", post.getMessage());
        tempResult.put("imageUrls", post.getImageUrls());
        tempResult.put("gmtCreate", post.getGmtCreate());
        tempResult.put("isEyeOn", post.getIsEyeOn());
        tempResult.put("eyeOnNum", post.getEyeOnNum());
        tempResult.put("isLike", post.getIsLike());
        tempResult.put("likeNum", post.getLikeNum());
        tempResult.put("rewardNum", post.getRewardNum());
        tempResult.put("comments", new ArrayList<>());
        List<PostComment> comments = post.getComments();
        if (comments != null) {
          List<Map<String, Object>> postComments = new ArrayList<>();
          Map<String, Object> tempComment;
          for (PostComment postComment : comments) {
            tempComment = new HashMap<>();
            tempComment.put("commentId", postComment.getId());
            tempComment.put("commentUserId", postComment.getIdFrom());
            tempComment.put("commentUsername", postComment.getNameFrom());
            tempComment.put("message", postComment.getMessage());
            tempComment.put("preId", postComment.getPreId());
            postComments.add(tempComment);
          }
          tempResult.put("comments", postComments);
        }
        resultList.add(tempResult);
      }
    }else {
      resultList = new ArrayList<>();
    }
    return resultList;
  }
}
