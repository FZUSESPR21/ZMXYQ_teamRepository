package com.team.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.User;
import javafx.geometry.Pos;

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
   * @param publisherId the publisher id 发帖用户ID
   * @return the post publisher 发帖人信息，包含username,birthday,isGraduated,        province,city,school,userIconUrl
   */
  User getPostPublisher(Long publisherId);

  /**
   * 发布校友圈帖子
   *
   * @param userId    用户ID
   * @param postTheme 帖子主题ID
   * @param message   帖子文字内容
   * @param imgUrls   帖子图片URL,多个URl之间用;分隔
   * @return ExceptionInfo 包含处理结果信息
   */
  ExceptionInfo publishPost(Long userId,Long postTheme,String message,String imgUrls);

  /**
   * List post page order by gmt create id desc list.
   *
   * @param pageNum  the page num
   * @param pageSize the page size
   * @param userId   the user id
   * @return the list
   */
  List<Map<String,Object>> listPostPageOrderByGmtCreateIdDesc(Long pageNum, Long pageSize, Long userId);

  List<Map<String,Object>> fuzzyListPostPageOrderByGmtCreateDesc(String content,Integer pageNum,Integer pageSize
          ,Long currentUserId);

  List<Map<String,Object>> listPostPageByTypeId(Long typeId,Long currentUserId,Integer pageNum,Integer pageSize);

  List<Map<String,Object>> selectTopTenLikePost(Long currentUserId);
}
