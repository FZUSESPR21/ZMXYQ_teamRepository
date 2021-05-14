package com.team.backend.mapper;

import com.team.backend.model.Party;
import com.team.backend.model.PartyType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
public interface PartyTypeMapper extends BaseMapper<PartyType> {

  List<Party> selectByMyWrapper();
}
