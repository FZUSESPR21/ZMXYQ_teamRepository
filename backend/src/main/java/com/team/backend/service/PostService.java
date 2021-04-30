package com.team.backend.service;

import com.team.backend.model.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @author Tars
 * @since 2021-04-28
 */
public interface PostService extends IService<Post> {

  /**
   * 获取发帖人的信息展示数据
   *
   * @param publisherId the publisher id 发贴用户ID
   * @return the post publisher 发帖人信息，包含username,birthday,isGraduated,
   *        province,city,school,userIconUrl
   */
  User getPostPublisher(Long publisherId);

}
