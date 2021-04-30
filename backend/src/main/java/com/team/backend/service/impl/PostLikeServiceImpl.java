package com.team.backend.service.impl;

import com.team.backend.mapper.NotificationMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.PostLike;
import com.team.backend.mapper.PostLikeMapper;
import com.team.backend.service.PostLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
@Transactional
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike> implements
    PostLikeService {

  @Resource
  private PostLikeMapper postLikeMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Override
  public boolean likePost(PostLike postLike) {
    boolean result = false;
    if (postLike.getPostId() != null && postLike.getIdFrom() != null) {
      if (postLikeMapper.insert(postLike) == 1) {
        Notification notification = new Notification();
        notification.setType(1);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
        notification.setIsRead(0);
        notification.setSourceId(postLike.getId());
        if (notificationMapper.insert(notification) == 1) {
          result = true;
        }
      }
    }
    return result;
  }
}
