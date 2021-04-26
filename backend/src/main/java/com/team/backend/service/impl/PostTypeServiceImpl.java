package com.team.backend.service.impl;

import com.team.backend.model.PostType;
import com.team.backend.mapper.PostTypeMapper;
import com.team.backend.service.PostTypeService;
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
public class PostTypeServiceImpl extends ServiceImpl<PostTypeMapper, PostType> implements PostTypeService {

}
