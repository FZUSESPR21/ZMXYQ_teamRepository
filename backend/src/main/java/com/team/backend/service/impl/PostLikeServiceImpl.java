package com.team.backend.service.impl;

import com.team.backend.model.PostLike;
import com.team.backend.mapper.PostLikeMapper;
import com.team.backend.service.PostLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Service
public class PostLikeServiceImpl extends ServiceImpl<PostLikeMapper, PostLike> implements
    PostLikeService {

  @Resource
  private PostLikeMapper postLikeMapper;

  @Override
  public boolean likePost(PostLike postLike) {
    boolean result = false;
    if (postLike.getPostId() != null && postLike.getIdFrom() != null) {
      if (postLikeMapper.insert(postLike) == 1) {
        result = true;
      }
    }
    return result;
  }
}
