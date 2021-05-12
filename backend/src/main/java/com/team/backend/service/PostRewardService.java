package com.team.backend.service;

import com.team.backend.model.PostReward;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.util.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
public interface PostRewardService extends IService<PostReward> {

  /**
   * 赞赏帖子，交换部分人品值
   *
   * @param postReward 赞赏基本信息，需保证postId，idFrom,amount有值，且amount > 0
   * @return the boolean 是否处理成功
   */
  Result rewardPost(PostReward postReward);

}
