package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.PostComment;
import com.team.backend.mapper.PostCommentMapper;
import com.team.backend.service.PostCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment> implements
    PostCommentService {

  @Resource
  private PostCommentMapper postCommentMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Override
  public boolean commentPost(PostComment postComment) {
    boolean result = false;
    if (postComment.getIdFrom() != null && postComment.getIdTo() != null
      && postComment.getPostId() != null && postComment.getPreId() != null
      && !StringUtils.isBlank(postComment.getMessage())) {
      if (postCommentMapper.insert(postComment) == 1) {
        if (postComment.getPreId() <= 0) {//preId传入小于等于0，表明是一级评论，数据库中preId与Id相同
          UpdateWrapper<PostComment> updateWrapper = new UpdateWrapper<>();
          updateWrapper.eq("id",postComment.getId());
          updateWrapper.set("pre_id",postComment.getId());
        }
        Notification notification = new Notification();
        notification.setType(0);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
        notification.setIsRead(0);
        notification.setSourceId(postComment.getId());
        if (notificationMapper.insert(notification) == 1) {
          result = true;
        }
      }
    }
    return result;
  }
}
