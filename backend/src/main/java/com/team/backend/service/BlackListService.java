package com.team.backend.service;

import com.team.backend.model.BlackList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
public interface BlackListService extends IService<BlackList> {

  /**
   * 拉黑用户
   *
   * @param blackList the black list 拉黑信息，需保证userId，beUserId不为空
   * @param isBlock   the is block 是否拉黑
   * @return the boolean 是否处理成功
   */
  boolean blockUser(BlackList blackList,boolean isBlock);

}
