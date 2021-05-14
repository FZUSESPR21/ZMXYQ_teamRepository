package com.team.backend.service;

import com.team.backend.model.PartyComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.backend.model.PostComment;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
public interface PartyCommentService extends IService<PartyComment> {

  /**
   * Comment party boolean.
   *
   * @param partyComment the party comment 组局评论相关信息，需保证 information userId partyId preId
   *                     有值并且information不为空白字符串
   * @return the boolean 是否处理成功
   */
  boolean commentParty(PartyComment partyComment);
}
