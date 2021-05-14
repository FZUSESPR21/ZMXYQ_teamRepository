package com.team.backend.service;

import com.team.backend.model.PartyParticipants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
public interface PartyParticipantsService extends IService<PartyParticipants> {

  //将参与者移出组局
  Result moveOffParticipant(Long partyid, Long userid);
}
