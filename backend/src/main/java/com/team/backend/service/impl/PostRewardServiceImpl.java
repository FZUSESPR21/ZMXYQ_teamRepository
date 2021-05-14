package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.PostReward;
import com.team.backend.mapper.PostRewardMapper;
import com.team.backend.model.User;
import com.team.backend.service.PostRewardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;

import com.team.backend.util.Result;
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

  @Resource
  private PostMapper postMapper;

  @Resource
  private UserMapper userMapper;

  @Override
  public Result rewardPost(PostReward postReward) {
    Result result = Result.error(ExceptionInfo.POST_REWARD_INFO_LOST.getCode()
            ,ExceptionInfo.POST_REWARD_INFO_LOST.getMessage());
    if (postReward.getPostId() != null && postReward.getIdFrom() != null
        && postReward.getAmount() != null && postReward.getAmount() > 0) {

        Notification notification = new Notification();
        notification.setType(2);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
        notification.setIsRead(0);
        Long idTo = postMapper.selectById(postReward.getPostId()).getPublisherId();
        notification.setUserId(idTo);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userQueryWrapper.select("rp_value");
        userQueryWrapper.eq("id",postReward.getIdFrom());
        User rewardFrom = userMapper.selectOne(userQueryWrapper);
        userQueryWrapper.clear();

        if (rewardFrom.getRpValue() < postReward.getAmount()) {
          result = Result.error(ExceptionInfo.POST_REWARD_RP_NOT_ENOUGH.getCode()
                  ,ExceptionInfo.POST_REWARD_RP_NOT_ENOUGH.getMessage());
        }else {
          if (postRewardMapper.insert(postReward) == 1) {
            rewardFrom.setRpValue(rewardFrom.getRpValue() - postReward.getAmount());
            userUpdateWrapper.eq("id",postReward.getIdFrom());
            userMapper.update(rewardFrom,userUpdateWrapper);
            userUpdateWrapper.clear();

            User rewardTo = userMapper.selectById(idTo);

            rewardTo.setRpValue(rewardTo.getRpValue() + postReward.getAmount());

            userUpdateWrapper.eq("id",rewardTo.getId());
            userMapper.update(rewardTo,userUpdateWrapper);
            notification.setSourceId(postReward.getId());
            if (notificationMapper.insert(notification) == 1) {
              result = Result.success();
            }
          }
        }
    }
    return result;
  }
}
