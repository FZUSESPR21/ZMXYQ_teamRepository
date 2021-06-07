package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.backend.exception.ExceptionInfo;
import com.team.backend.mapper.UserMapper;
import com.team.backend.model.PrivateChat;
import com.team.backend.mapper.PrivateChatMapper;
import com.team.backend.model.Result;
import com.team.backend.model.User;
import com.team.backend.service.PrivateChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PrivateChatServiceImpl extends ServiceImpl<PrivateChatMapper, PrivateChat> implements
    PrivateChatService {

  @Autowired
  UserMapper userMapper;

  @Autowired
  PrivateChatMapper privateChatMapper;

  public Result<List<Map<String, Object>>> listChat(Long id) {

    Result<List<Map<String, Object>>> result = new Result<>();
    List<Map<String, Object>> chatList = new LinkedList<>();

    QueryWrapper<PrivateChat> wrapper = new QueryWrapper<>();
    wrapper.select("to_id")
        .eq("from_id", id)
        .groupBy("to_id");
    List<Map<String, Object>> maps = privateChatMapper.selectMaps(wrapper);

    for (Map<String, Object> objectMap : maps) {
      Long toId = (Long) objectMap.get("to_id");
      Map<String, Object> map = new HashMap<>();
      map.put("dialogUserId", toId);
      User user = userMapper.selectById(toId);
      map.put("dialogUserName", user.getUsername());
      map.put("iconUrl", user.getUserIconUrl());
      QueryWrapper<PrivateChat> chatWrapper = new QueryWrapper<>();
      chatWrapper.eq("from_id", id)
          .eq("to_id", toId)
          .orderByDesc("gmt_create");
      List<PrivateChat> privateChatList = privateChatMapper.selectList(chatWrapper);
      PrivateChat privateChat = privateChatList.get(0);
      map.put("latestTime", privateChat.getGmtCreate());
      map.put("latestMessage", privateChat.getMessage());
      chatList.add(map);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(chatList);
    return result;
  }

}
