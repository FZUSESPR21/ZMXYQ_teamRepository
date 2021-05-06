package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.Party;
import com.team.backend.mapper.PartyMapper;
import com.team.backend.model.User;
import com.team.backend.service.PartyService;
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
public class PartyServiceImpl extends ServiceImpl<PartyMapper, Party> implements PartyService {
  @Resource
  private UserMapper userMapper;
  public User getPartyPublisher(Long publisherId) {
    User user = new User();
    if (publisherId != null) {
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.select("username","birthday","is_graduated","province","city","school"
          ,"user_icon_url");
      queryWrapper.eq("id",publisherId);
      user = userMapper.selectOne(queryWrapper);
    }
    return user;
  }
}
