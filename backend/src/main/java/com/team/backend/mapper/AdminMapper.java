package com.team.backend.mapper;

import com.team.backend.model.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Component(value = "adminMapper")
public interface AdminMapper extends BaseMapper<Admin> {

}
