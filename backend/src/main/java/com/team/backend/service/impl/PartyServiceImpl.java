package com.team.backend.service.impl;

import com.team.backend.model.Party;
import com.team.backend.mapper.PartyMapper;
import com.team.backend.service.PartyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PartyServiceImpl extends ServiceImpl<PartyMapper, Party> implements PartyService {

}
