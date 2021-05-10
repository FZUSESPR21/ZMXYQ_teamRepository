package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.mapper.PartyMapper;
import com.team.backend.mapper.PartyParticipantsMapper;
import com.team.backend.mapper.PartyTypeMapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Party;
import com.team.backend.model.PartyParticipants;
import com.team.backend.model.Result;
import com.team.backend.service.PartyParticipantsService;
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
public class PartyParticipantsServiceImpl extends
    ServiceImpl<PartyParticipantsMapper, PartyParticipants> implements PartyParticipantsService {

  @Resource
  private PartyMapper partyMapper;
  @Resource
  private UserMapper userMapper;
  @Resource
  private PartyCommentMapper partyCommentMapper;
  @Resource
  private PartyParticipantsMapper partyParticipantsMapper;
  @Resource
  private PartyTypeMapper partyTypeMapper;

  //将参与者移出组局
  public Result<Integer> moveOffParticipant(long partyid, long userid) {
    Result<Integer> result = new Result<>();
    Party fromParty = partyMapper.selectById(partyid);
    // 判断数据库是否存在该组局
    if (fromParty == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }

    QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
    wrapper.eq("party_id", partyid)
        .eq("participant_id", userid);
    PartyParticipants participant = partyParticipantsMapper.selectOne(wrapper);
    //判断该用户是否是该组局的参与者
    if (participant == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOTCONTAIN_USER").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOTCONTAIN_USER").getMessage());
      result.setData(0);
      return result;
    }
    result.setData(partyParticipantsMapper.delete(wrapper));
    return result;
  }

}
