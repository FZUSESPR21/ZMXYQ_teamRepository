package com.team.backend.service.impl;

import com.team.backend.model.Post;
import com.team.backend.mapper.PostMapper;
import com.team.backend.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-26
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
