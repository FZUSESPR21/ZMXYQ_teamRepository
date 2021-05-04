package com.team.backend.service;

import com.team.backend.model.PostComment;
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
public interface PostCommentService extends IService<PostComment> {

  /**
   * Comment post boolean.
   *
   * @param postComment the post comment 评论相关信息，需保证preId，idFrom,idTo，postId，status，message
   *                    有值并且message不为空白字符串
   * @return the boolean 是否处理成功
   */
  boolean commentPost(PostComment postComment);
}
