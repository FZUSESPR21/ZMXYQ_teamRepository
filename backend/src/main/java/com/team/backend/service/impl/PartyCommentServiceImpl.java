package com.team.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.model.PartyComment;
import com.team.backend.service.PartyCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-26
 */
@Service
public class PartyCommentServiceImpl extends
    ServiceImpl<PartyCommentMapper, PartyComment> implements PartyCommentService {

}
