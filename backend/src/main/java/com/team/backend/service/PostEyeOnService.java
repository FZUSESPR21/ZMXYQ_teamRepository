package com.team.backend.service;

import com.team.backend.model.PostEyeOn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
public interface PostEyeOnService extends IService<PostEyeOn> {

  /**
   * 收藏帖子
   *
   * Service方法
   *
   * @param eyeOn 需要保证成员变量postId,idFrom有值
   */
  void collectPost(PostEyeOn eyeOn);

}
