package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.PartyCommentMapper;
import com.team.backend.model.PartyComment;
import com.team.backend.model.Result;
import com.team.backend.service.PartyCommentService;
import java.util.List;
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

  //  评论组局
  public Result<Integer> commentParty(PartyComment partyComment) {
    Result<Integer> result = new Result<>();

    if (partyComment.getIdFrom() != null && partyComment.getPartyId() != null &&
        partyComment.getPreId() != null && !StringUtils.isBlank(partyComment.getInformation())) {
      if (partyCommentMapper.insert(partyComment) == 1) {
        if (partyComment.getPreId() <= 0) {//preId传入小于等于0，表明是一级评论，数据库中preId与Id相同
          UpdateWrapper<PartyComment> updateWrapper = new UpdateWrapper<>();
          updateWrapper.eq("id", partyComment.getId());
          updateWrapper.set("pre_id", partyComment.getId());
        }
      }

    }
    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(0);
    return result;
  }

  // 查询组局评论
  public Result<List<PartyComment>> PartyCommentList(Long id) {//传入组局ID获取对应组局的评论列表

    Result<List<PartyComment>> result = new Result<>();

    QueryWrapper<PartyComment> wrapper = new QueryWrapper<>();
    wrapper.eq("party_id", id);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(partyCommentMapper.selectList(wrapper));
    return result;
  }

}
