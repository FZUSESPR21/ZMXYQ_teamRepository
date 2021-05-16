package com.team.backend.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.mapper.PartyMapper;
import com.team.backend.model.Party;
import com.team.backend.model.PartyComment;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.PartyCommentService;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class PartyCommentServiceImpl extends
    ServiceImpl<PartyCommentMapper, PartyComment> implements PartyCommentService {

  @Resource
  private PartyCommentMapper partyCommentMapper;

  @Resource
  private PartyMapper partyMapper;

  @Resource
  private UserMapper userMapper;

  //  评论组局
  @Override
  public boolean commentParty(PartyComment partyComment) {
    boolean result = false;
    if (partyComment.getIdFrom() != null && partyComment.getPartyId() != null &&
        partyComment.getPreId() != null && !StringUtils.isBlank(partyComment.getInformation())) {
      if (partyCommentMapper.insert(partyComment) == 1) {
        if (partyComment.getPreId() <= 0) {//preId传入小于等于0，表明是一级评论，数据库中preId与Id相同
          UpdateWrapper<PartyComment> updateWrapper = new UpdateWrapper<>();
          updateWrapper.eq("id", partyComment.getId());
          updateWrapper.set("pre_id", partyComment.getId());
        }
        result = true;
      }

    }
    return result;
  }

  // 查询组局评论列表
  public Result<List<Map<String, Object>>> PartyCommentList(Long id) {//传入组局ID获取对应组局的评论列表

    Result<List<Map<String, Object>>> result = new Result<>();
    List<Map<String, Object>> mapList = new LinkedList<>();

    QueryWrapper<PartyComment> wrapper = new QueryWrapper<>();
    wrapper.eq("party_id", id);
    List<PartyComment> partyComments = partyCommentMapper.selectList(wrapper);
    if (partyMapper.selectById(id) == null) {
      result.setCode(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_NOT_EXISTED").getMessage());
      result.setData(null);
      return result;
    }
    if (partyComments.size() == 0) {
      result.setCode(ExceptionInfo.valueOf("PARTY_COMMENT_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("PARTY_COMMENT_NULL").getMessage());
      result.setData(null);
      return result;
    }
    for (PartyComment partyComment : partyComments) {
      Map<String, Object> map = new HashMap<>();
      map.put("commentId", partyComment.getId());
      map.put("commentUserId", partyComment.getIdFrom());
      User user = userMapper.selectById(partyComment.getIdFrom());
      map.put("images", user.getUserIconUrl());//返回数组
      map.put("commentUsername", user.getUsername());
      map.put("message", partyComment.getInformation());
      map.put("preId", partyComment.getPreId());
      map.put("gmtCreate", partyComment.getGmtCreate());
      mapList.add(map);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(mapList);
    return result;
  }

}
