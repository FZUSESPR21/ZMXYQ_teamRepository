package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.model.BlackList;
import com.team.backend.mapper.BlackListMapper;
import com.team.backend.service.BlackListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
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

  @Resource
  private BlackListMapper blackListMapper;

  @Override
  public boolean blockUser(BlackList blackList, boolean isBlock) {
    boolean result = false;
    if (blackList.getUserId() != null && blackList.getBeUserId() != null) {
      QueryWrapper<BlackList> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("user_id",blackList.getUserId());
      queryWrapper.eq("be_user_id",blackList.getBeUserId());
      BlackList existRecord = blackListMapper.selectOne(queryWrapper);
      if (isBlock && existRecord == null) {//需要删除，并且数据库中已删除
        if (blackListMapper.insert(blackList) == 1) {
          result = true;
        }
        result = true;
      }else if (!isBlock && existRecord != null) {//需要新增，且数据库中不存在
        queryWrapper.clear();
        queryWrapper.eq("id",existRecord.getId());
        if (blackListMapper.delete(queryWrapper) == 1) {
          result = true;
        }
      }
    }
    return result;
  }
}
