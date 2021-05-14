package com.team.backend.service;

import com.team.backend.model.PartyType;
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
public interface PartyTypeService extends IService<PartyType> {

  //根据组局类型ID获取组局
  Result PartylistByTypeID(int id);
}
