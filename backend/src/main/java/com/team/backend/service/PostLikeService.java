package com.team.backend.service;

import com.team.backend.model.PostLike;
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
public interface PostLikeService extends IService<PostLike> {

  /**
   * 点赞评论
   * <p>
   * Service方法
   *
   * @param postLike 需保证成员变量postId,idFrom有值
   * @return the boolean 是否处理成功
   */
  boolean likePost(PostLike postLike);

}
