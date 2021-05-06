package com.team.backend.service;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Post;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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

  /**
   * 发布校友圈帖子
   * 
   * @param userId 用户ID
   * @param postTheme 帖子主题ID
   * @param message 帖子文字内容
   * @param imgUrls 帖子图片URL,多个URl之间用;分隔
   * @return ExceptionInfo 包含处理结果信息
   */
  ExceptionInfo publishPost(Long userId,Long postTheme,String message,String imgUrls);

}
