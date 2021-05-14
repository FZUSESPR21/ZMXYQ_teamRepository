package com.team.backend.service;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Party;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
public interface PartyService extends IService<Party> {

  User getPartyPublisher(Long publisherId);

  //新建组局
  ExceptionInfo insertParty(Long id, String description, String imageUrls, int peopleCnt,
      Long partyTypeId);

  //修改组局信息
  Result<Integer> updateParty(Long id, String description, String imageUrls, int peopleCnt,
      Long partyTypeId);

  //查看我的组局
  Result<List<Map<String, Object>>> GetMyPartyList(Long id);

  //获取组局详情
  Result<Map<String, Object>> getPartymes(Long id);

  //参加组局
  Result<Integer> joinParty(Long userid, Long id);

  //退出组局
  Result<Integer> exitParty(Long userId, Long id);

  //删除组局
  Result<Integer> deleteParty(Long userId, Long partyId);

  //组局模糊搜索
  Result<List<Map<String, Object>>> searchParty(String massage);
}