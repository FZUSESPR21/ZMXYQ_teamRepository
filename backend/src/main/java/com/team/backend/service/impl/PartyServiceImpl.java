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
import com.team.backend.model.User;
import com.team.backend.service.PartyService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
public class PartyServiceImpl extends ServiceImpl<PartyMapper, Party> implements PartyService {

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
  ;

  public User getPartyPublisher(Long publisherId) {
    User user = new User();
    if (publisherId != null) {
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.select("username", "birthday", "is_graduated", "province", "city", "school"
          , "user_icon_url");
      queryWrapper.eq("id", publisherId);
      user = userMapper.selectOne(queryWrapper);
    }
    return user;
  }

  // 新建组局
  @Override
  public ExceptionInfo insertParty(Long id, String description, String imageUrls, int peopleCnt,
      Long partyTypeId) {
    ExceptionInfo result;
    if (description == null || description == "") {
      result = ExceptionInfo.PARTY_DESCRIPTION_NULL;
      return result;
    }
    if (imageUrls == null || imageUrls == "") {
      result = ExceptionInfo.PARTY_IMAGEURLS_NULL;
      return result;
    }
    if (peopleCnt == 0) {
      result = ExceptionInfo.PARTY_PEOPLECNT_NULL;
      return result;
    }
    if (partyTypeId == null) {
      result = ExceptionInfo.PARTY_PARTYTYPEID_NULL;
      return result;
    }
    Party party = new Party();
    party.setPublisherId(id);
    party.setDescription(description);
    party.setImageUrls(imageUrls);
    party.setPeopleCnt(peopleCnt);
    party.setPartyTypeId(partyTypeId);
    party.setStatus(0);
    party.setDeleted(0);
    try {
      partyMapper.insert(party);
      result = ExceptionInfo.OK;
    } catch (Exception e) {
      e.printStackTrace();
      result = ExceptionInfo.PARTY_INSERT_FAIL;
    }
    return result;
  }

  // 获取组局审核状态
  public Result<Integer> PartyIdentifyStatus(Long id) {

    Result<Integer> result = new Result<>();
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    Party party = partyMapper.selectById(id);
    result.setData(party.getStatus());
    return result;
  }

  // 修改组局信息
  public Result<Integer> updateParty(Long id, String description, String imageUrls, int peopleCnt,
      Long partyTypeId) {
    Result<Integer> result = new Result<>();
    Party party = partyMapper.selectById(id);
    if (party.getId() == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }
    if (description == null || description == "") {
      result.setCode(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_DESCRIPTION_NULL").getMessage());
      result.setData(0);
      return result;
    }
    if (imageUrls == null || imageUrls == "") {
      result.setCode(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_IMAGEURLS_NULL").getMessage());
      result.setData(0);
      return result;
    }
    if (peopleCnt == 0) {
      result.setCode(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_PEOPLECNT_NULL").getMessage());
      result.setData(0);
      return result;
    }
    if (partyTypeId == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_PARTYTYPEID_NULL").getMessage());
      result.setData(0);
      return result;
    }
    // 判断数据库是否存在这个组局
    Party aqlParty = partyMapper.selectById(party.getId());
    if (aqlParty == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }
    party.setDescription(description);
    party.setImageUrls(imageUrls);
    party.setPeopleCnt(peopleCnt);
    party.setPartyTypeId(partyTypeId);
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyMapper.updateById(party));
//    partyMapper.updateById(party)
    return result;
  }

  //查看我的组局
  public Result<List<Map<String, Object>>> GetMyPartyList(Long id) {//使用个人ID查询

    Result<List<Map<String, Object>>> result = new Result<>();
    QueryWrapper<Party> wrapper = new QueryWrapper<>();
    wrapper.eq("publisher_id", id);
    List<Party> MyPartyList = partyMapper.selectList(wrapper);
    List<Map<String, Object>> mapList = new LinkedList<>();
    for (Party party : MyPartyList) {
      Map<String, Object> map = new HashMap<>();
      User user = userMapper.selectById(party.getPublisherId());
      map.put("partyID", party.getId());
      map.put("description", party.getDescription());
      map.put("peopleCnt", party.getPeopleCnt());
      map.put("partyType", party.getPartyTypeId());
      map.put("gmtCreate", party.getGmtCreate());
      map.put("username", user.getUsername());
      map.put("sex", user.getSex());
      mapList.add(map);
    }
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(mapList);
    return result;
  }

  //获取组局详情
  public Result<Map<String, Object>> getPartymes(Long id) { //根据组局ID获取组局详情

    Result<Map<String, Object>> result = new Result<>();
    Party party = partyMapper.selectById(id);
    Map<String, Object> map = new HashMap<>();
    if (party == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(map);
      return result;
    }
    map.put("partyID", party.getId());
    map.put("context", party.getDescription());
    map.put("images", party.getImageUrls());
    map.put("peopleCnt", party.getPeopleCnt());
    map.put("partyType", party.getPartyTypeId());
    map.put("gmtCreate", party.getGmtCreate());
    List<Long> idList = new LinkedList<>();
    QueryWrapper<PartyParticipants> participantsQueryWrapper = new QueryWrapper<>();
    participantsQueryWrapper.eq("party_id", party.getId());
    List<PartyParticipants> partyParticipantsList = partyParticipantsMapper
        .selectList(participantsQueryWrapper);
    for (PartyParticipants partyParticipants : partyParticipantsList) {
      idList.add(partyParticipants.getParticipantId());
    }
    map.put("participantsID", idList);
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(map);
    return result;
  }


  //参加组局
  public Result<Integer> joinParty(Long userid, Long id) {
    Result<Integer> result = new Result<>();
    Party jionParty = partyMapper.selectById(id);
    // 判断数据库是否存在该组局
    if (jionParty == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }
    QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
    wrapper.eq("party_id", id);
    List<PartyParticipants> partyParticipantsList = partyParticipantsMapper.selectList(wrapper);
    if (partyParticipantsList.size() < jionParty.getPeopleCnt()) {// 判断该组局是否已满员
      PartyParticipants partyParticipants = new PartyParticipants();
      partyParticipants.setPartyId(id);
      partyParticipants.setParticipantId(userid);
      partyParticipants.setDeleted(0);//设置删除逻辑为0
      result.setCode(ExceptionInfo.valueOf("OK").getCode());
      result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
      result.setData(partyParticipantsMapper.insert(partyParticipants));
    } else {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_AVAILABLE").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_AVAILABLE").getMessage());
      result.setData(0);
    }
    return result;
  }

  //退出组局
  public Result<Integer> exitParty(Long userId, Long id) {
    Result<Integer> result = new Result<>();
    Party exitParty = partyMapper.selectById(id);
    // 判断数据库是否存在该组局
    if (exitParty == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }
    QueryWrapper<PartyParticipants> wrapper = new QueryWrapper<>();
    wrapper.eq("party_id", id)
        .eq("participant_id", userId);
    PartyParticipants participants = partyParticipantsMapper.selectOne(wrapper);
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyParticipantsMapper.deleteById(participants.getId()));
    return result;
  }


  //删除组局
  public Result<Integer> deleteParty(Long userId, Long partyId) {

    Result<Integer> result = new Result<>();

    if (userId == null) {
      result.setCode(ExceptionInfo.valueOf("USER_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    if (partyId == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_ID_NULL").getMessage());
      result.setData(0);
      return result;
    }

    // 判断数据库是否存在该组局
    Party dlParty = partyMapper.selectById(partyId);
    if (dlParty == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(0);
      return result;
    }
    // 判断该组局是否属于当前用户
    if (!dlParty.getPublisherId().equals(userId)) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOTBELONGTO_USER").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOTBELONGTO_USER").getMessage());
      result.setData(0);
      return result;
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyMapper.deleteById(partyId));
    return result;

  }

  // 根据massage模糊搜索组局
  public Result<List<Map<String, Object>>> searchParty(String massage) {

    Result<List<Map<String, Object>>> result = new Result<>();

    if (massage == null) {
      result.setCode(ExceptionInfo.valueOf("USER_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_ID_NULL").getMessage());
      result.setData(null);
      return result;
    }

    QueryWrapper<Party> wrapper = new QueryWrapper<>();
    //用massage进行组局搜索
    wrapper.like("id", massage).or()
        .like("description", massage).or()
        .like("publisher_id", massage).or()
        .like("party_type_id", massage).or()
        .like("people_cnt", massage).or()
        .like("image_urls", massage).or()
        .like("gmt_create", massage);
    List<Party> searchPartyList = partyMapper.selectList(wrapper);
    List<Map<String, Object>> mapList = new LinkedList<>();
    for (Party party : searchPartyList) {
//      System.out.println(party);
      Map<String, Object> map = new HashMap<>();
      User user = userMapper.selectById(party.getPublisherId());
      map.put("partyID", party.getId());
      map.put("description", party.getDescription());
      map.put("peopleCnt", party.getPeopleCnt());
      map.put("partyType", party.getPartyTypeId());
      map.put("gmtCreate", party.getGmtCreate());
      map.put("username", user.getUsername());
      map.put("sex", user.getSex());
      mapList.add(map);
    }
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(mapList);
    return result;
  }


}