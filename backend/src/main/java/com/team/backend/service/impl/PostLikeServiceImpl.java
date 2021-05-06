package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.mapper.NotificationMapper;
import com.team.backend.mapper.PostMapper;
import com.team.backend.model.Notification;
import com.team.backend.model.Post;
import com.team.backend.model.PostLike;
import com.team.backend.mapper.PostLikeMapper;
import com.team.backend.service.PostLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike> implements
    PostLikeService {

  @Resource
  private PostLikeMapper postLikeMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Resource
  private PostMapper postMapper;
  @Override
  public boolean likePost(PostLike postLike) {
    boolean result = false;
    if (postLike.getPostId() != null && postLike.getIdFrom() != null) {

      //查询是否已点赞
      QueryWrapper<PostLike> postLikeQueryWrapper = new QueryWrapper<>();
      postLikeQueryWrapper.eq("post_id",postLike.getPostId())
          .eq("id_from",postLike.getIdFrom());
      Integer existLikeCount = postLikeMapper.selectCount(postLikeQueryWrapper);

      if (existLikeCount == 0) {//排除已点赞情况
        if (postLikeMapper.insert(postLike) == 1) {

          //为发帖用户增加消息通知
          Notification notification = new Notification();
          notification.setType(1);//消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)
          notification.setIsRead(0);
          notification.setSourceId(postLike.getId());

          //查询发帖用户ID
          QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
          postQueryWrapper.select("publisher_id");
          postQueryWrapper.eq("id", postLike.getPostId());
          Post post = postMapper.selectOne(postQueryWrapper);

          notification.setUserId(post.getPublisherId());
          if (notificationMapper.insert(notification) == 1) {
            result = true;
          }
        }
      }
    }
    return result;
  }
}
