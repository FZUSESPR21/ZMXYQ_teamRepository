package com.team.backend.service.impl;

import com.team.backend.mapper.NotificationMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.PostReward;
import com.team.backend.mapper.PostRewardMapper;
import com.team.backend.service.PostRewardService;
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
 * @author Tars
 * @since 2021-04-28
 */
@Service
@Transactional
public class PostRewardServiceImpl extends ServiceImpl<PostRewardMapper, PostReward> implements
    PostRewardService {

  @Resource
  private PostRewardMapper postRewardMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Override
  public boolean rewardPost(PostReward postReward) {
    boolean result = false;
    if (postReward.getPostId() != null && postReward.getId() != null
        && postReward.getAmount() != null && postReward.getAmount() > 0) {
      if (postRewardMapper.insert(postReward) == 1) {
        Notification notification = new Notification();
        notification.setType(2);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
        notification.setIsRead(0);
        notification.setSourceId(postReward.getId());
        if (notificationMapper.insert(notification) == 1) {
          result = true;
        }
      }
    }
    return result;
  }
}
