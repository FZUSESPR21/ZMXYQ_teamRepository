package com.team.backend.service;

import com.team.backend.model.Party;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.User;

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
}
