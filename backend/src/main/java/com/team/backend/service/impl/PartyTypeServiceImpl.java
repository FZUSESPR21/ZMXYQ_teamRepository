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
import com.team.backend.model.PartyType;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.PartyTypeService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class PartyTypeServiceImpl extends ServiceImpl<PartyTypeMapper, PartyType> implements
    PartyTypeService {

  @Autowired
  PartyCommentMapper partyCommentMapper;

  @Autowired
  PartyMapper partyMapper;

  @Autowired
  PartyTypeMapper partyTypeMapper;

  @Autowired
  PartyParticipantsMapper partyParticipantsMapper;

  @Autowired
  UserMapper userMapper;

  public Result<List<Map<String, Object>>> PartylistByTypeID(int id) {// 根据TypeID获取组局

    Result<List<Map<String, Object>>> result = new Result<>();
    QueryWrapper<Party> wrapper = new QueryWrapper<>();
    List<Party> partylistByTypeID = new LinkedList<>();
    List<Map<String, Object>> mapList = new LinkedList<>();

    if (id == -1) {//当传入为"-1"时返回所有组局信息
      partylistByTypeID = partyMapper.selectList(null);
      for (Party party : partylistByTypeID) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(party.getPublisherId());
        int nowPeopleCnt = partyMapper.selectNowPartyCnt(party.getId());
        map.put("partyID", party.getId());
        map.put("description", party.getDescription());
        map.put("username", user.getUsername());
        map.put("sex", user.getSex());
        map.put("peopleCnt", party.getPeopleCnt());
        map.put("nowPeopleCnt", nowPeopleCnt);
        map.put("partyType", party.getPartyTypeId());
        map.put("gmtCreate", party.getGmtCreate());
        mapList.add(map);
      }
    } else if (id == -2) {//当传入"-2"时返回有空位的组局
      partylistByTypeID = partyTypeMapper.selectByMyWrapper();
      for (Party party : partylistByTypeID) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(party.getPublisherId());
        int nowPeopleCnt = partyMapper.selectNowPartyCnt(party.getId());
        map.put("partyID", party.getId());
        map.put("description", party.getDescription());
        map.put("username", user.getUsername());
        map.put("sex", user.getSex());
        map.put("peopleCnt", party.getPeopleCnt());
        map.put("nowPeopleCnt", nowPeopleCnt);
        map.put("partyType", party.getPartyTypeId());
        map.put("gmtCreate", party.getGmtCreate());
        mapList.add(map);
      }
    } else if (id == 0 || id == 1 || id == 2 || id == 3 || id == 4 || id == 5 || id == 6 || id == 7
        || id == 8) {
      //0-自习 1-电影 2-聚餐 3-拼车 4-拼单 5-运动 6-游戏 7-旅行 8-其他
      wrapper.eq("party_type_id", id);
      partylistByTypeID = partyMapper.selectList(wrapper);
      for (Party party : partylistByTypeID) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(party.getPublisherId());
        int nowPeopleCnt = partyMapper.selectNowPartyCnt(party.getId());
        map.put("partyID", party.getId());
        map.put("description", party.getDescription());
        map.put("username", user.getUsername());
        map.put("sex", user.getSex());
        map.put("peopleCnt", party.getPeopleCnt());
        map.put("nowPeopleCnt", nowPeopleCnt);
        map.put("partyType", party.getPartyTypeId());
        map.put("gmtCreate", party.getGmtCreate());
        mapList.add(map);
      }
    } else {
      result.setCode(ExceptionInfo.valueOf("PARTY_TYPE_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_TYPE_NOT_EXISTED").getMessage());
      result.setData(null);
      return result;
    }
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(mapList);
    return result;
  }


}
