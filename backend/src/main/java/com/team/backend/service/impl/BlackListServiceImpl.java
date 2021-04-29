package com.team.backend.service.impl;

import com.team.backend.model.BlackList;
import com.team.backend.mapper.BlackListMapper;
import com.team.backend.service.BlackListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements
    BlackListService {

}
