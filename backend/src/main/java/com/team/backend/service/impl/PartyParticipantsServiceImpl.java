package com.team.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.mapper.PartyParticipantsMapper;
import com.team.backend.model.PartyParticipants;
import com.team.backend.service.PartyParticipantsService;
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
public class PartyParticipantsServiceImpl extends
    ServiceImpl<PartyParticipantsMapper, PartyParticipants> implements PartyParticipantsService {

}
