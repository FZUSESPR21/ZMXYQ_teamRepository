package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.Post;
import com.team.backend.model.PostComment;
import com.team.backend.mapper.PostCommentMapper;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.PostCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment> implements
    PostCommentService {

  @Resource
  private PostCommentMapper postCommentMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Autowired
  PostMapper postMapper;

  @Autowired
  UserMapper userMapper;

  @Override
  public boolean commentPost(PostComment postComment) {
    boolean result = false;
    if (postComment !=null && postComment.getIdFrom() != null && postComment.getIdTo() != null
        && postComment.getPostId() != null && postComment.getPreId() != null
        && !StringUtils.isBlank(postComment.getMessage())) {
      postComment.setStatus(0);
      if (postCommentMapper.insert(postComment) == 1) {
        if (postComment.getIdTo() <= 0) {//idTo传入小于等于0，表明是一级评论，数据库中preId与Id相同
          UpdateWrapper<PostComment> updateWrapper = new UpdateWrapper<>();
          updateWrapper.eq("id", postComment.getId());
          postComment.setPreId(postComment.getId());
          postComment.setIdTo(-1L);
          postCommentMapper.update(postComment,updateWrapper);
        }
        //添加评论消息记录
        Notification notification = new Notification();
        notification.setType(0);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
        notification.setIsRead(0);
        notification.setSourceId(postComment.getId());
        QueryWrapper<Post> postWrapper = new QueryWrapper<>();
        postWrapper.eq("id",postComment.getPostId());
        postWrapper.select("publisher_id");
        Post post = postMapper.selectOne(postWrapper);
        if (postComment.getIdTo() == -1) {
          notification.setUserId(post.getPublisherId());
        }else {
          notification.setUserId(postComment.getIdTo());
        }
        if (notificationMapper.insert(notification) == 1) {
          result = true;
        }
      }
    }
    return result;
  }

  // 获取帖子评论列表
  public Result<List<Map<String, Object>>> listComment(Long postId) {
    Result<List<Map<String, Object>>> result = new Result<>();
    List<Map<String, Object>> mapList = new LinkedList<>();

    if (postId == null) {
      result.setCode(ExceptionInfo.valueOf("POST_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("POST_ID_NULL").getMessage());
      result.setData(mapList);
      return result;
    }

    Post post = postMapper.selectById(postId);
    if (post == null) {
      result.setCode(ExceptionInfo.valueOf("POST_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("POST_NOT_EXISTED").getMessage());
      result.setData(mapList);
      return result;
    }

    QueryWrapper<PostComment> wrapper = new QueryWrapper<>();
    wrapper.eq("post_id", postId);
    List<PostComment> postComments = postCommentMapper.selectList(wrapper);
    for (PostComment postComment : postComments) {
      Map<String, Object> map = new HashMap<>();
      map.put("commentId", postComment.getId());
      map.put("commentUserId", postComment.getIdFrom());
      User user = userMapper.selectById(postComment.getIdFrom());
      map.put("commentUsername", user.getUsername());
      map.put("replyUserId", postComment.getIdTo());
      if (!postComment.getIdTo().equals(-1L)) {
        User user1 = userMapper.selectById(postComment.getIdTo());
        map.put("replyUsername", user1.getUsername());
      } else {
        map.put("replyUsername", null);
      }
      map.put("message", postComment.getMessage());
      map.put("preId", postComment.getPreId());
      mapList.add(map);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(mapList);
    return result;
  }
}
