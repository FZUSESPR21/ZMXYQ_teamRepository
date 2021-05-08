package com.team.backend.mapper;

import com.team.backend.model.Party;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Component
public interface PartyMapper extends BaseMapper<Party> {
    //    List<Party> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<Party> partyWrapper);
    List<Party> selectByMyWrapper();
}
