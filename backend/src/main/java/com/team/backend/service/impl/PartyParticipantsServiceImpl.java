package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.*;
import com.team.backend.model.Party;
import com.team.backend.model.PartyParticipants;
import com.team.backend.model.Result;
import com.team.backend.service.PartyParticipantsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PartyParticipantsServiceImpl extends
        ServiceImpl<PartyParticipantsMapper, PartyParticipants> implements PartyParticipantsService {
    @Autowired
    PartyMapper partyMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PartyCommentMapper partyCommentMapper;
    @Autowired
    PartyParticipantsMapper partyParticipantsMapper;
    @Autowired
    PartyTypeMapper partyTypeMapper;

    //将参与者移出组局
    public Result<Integer> moveOffParticipant(long id, long userid) {
        Result<Integer> result = new Result<>();
        Party fromParty = partyMapper.selectById(id);
        // 判断数据库是否存在该组局
        if (fromParty == null) {
            result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
            result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
            result.setData(0);
            return result;
        }
        QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
        wrapper.eq("party_id", id)
                .eq("participant_id", userid);
        result.setData(partyParticipantsMapper.delete(wrapper));
        return result;
    }

}
