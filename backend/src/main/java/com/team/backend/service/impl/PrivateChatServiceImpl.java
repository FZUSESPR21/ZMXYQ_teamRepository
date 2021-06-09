package com.team.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

  // 获取用户私聊列表
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

  // 清空用户私聊列表
  public Result<Integer> clearList(Long id) {

    Result<Integer> result = new Result<>();

    QueryWrapper<PrivateChat> wrapper = new QueryWrapper<>();
    wrapper.eq("from_id", id);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(privateChatMapper.delete(wrapper));
    return result;
  }


  // 删除私聊列表中的某一项
  public Result<Integer> deleteOne(Long id, Long dialogUserId) {

    Result<Integer> result = new Result<>();

    if (dialogUserId == null) {
      result.setCode(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getMessage());
      return result;
    }

    QueryWrapper<PrivateChat> wrapper = new QueryWrapper<>();
    wrapper.eq("from_id", id)
        .eq("to_id", dialogUserId);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(privateChatMapper.delete(wrapper));
    return result;
  }

  // 用户发送消息
  public Result<Integer> sendMessage(Long id, Long userIdTo, String content) {

    Result<Integer> result = new Result<>();

    if (userIdTo == null) {
      result.setCode(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getMessage());
      return result;
    }

    if (content == null || content == "") {
      result.setCode(ExceptionInfo.valueOf("USER_SEND_MESSAGE_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_SEND_MESSAGE_NULL").getMessage());
      return result;
    }

    PrivateChat privateChat = new PrivateChat();
    privateChat.setFromId(id);
    privateChat.setToId(userIdTo);
    privateChat.setMessage(content);

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(privateChatMapper.insert(privateChat));
    return result;
  }

  // 用户接收消息
  public Result<List<Map<String, Object>>> receiveMessage(Long id, Long toId, Integer pageNum,
      Integer pageSize) {

    Result<List<Map<String, Object>>> result = new Result<>();
    List<Map<String, Object>> messageList = new LinkedList<>();

    if (toId == null) {
      result.setCode(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getMessage());
      return result;
    }

    if (pageNum == null) {
      result.setCode(ExceptionInfo.valueOf("USER_PAGE_NUM_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PAGE_NUM_NULL").getMessage());
      return result;
    }

    if (pageNum < 0) {
      result.setCode(ExceptionInfo.valueOf("USER_PAGE_NUM_BELOW0").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PAGE_NUM_BELOW0").getMessage());
      return result;
    }

    if (pageSize == null) {
      result.setCode(ExceptionInfo.valueOf("USER_PAGE_SIZE_NULL").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PAGE_SIZE_NULL").getMessage());
      return result;
    }

    if (pageSize < 0) {
      result.setCode(ExceptionInfo.valueOf("USER_PAGE_SIZE_BELOW0").getCode());
      result.setMessage(ExceptionInfo.valueOf("USER_PAGE_SIZE_BELOW0").getMessage());
      return result;
    }

    QueryWrapper<PrivateChat> wrapper = new QueryWrapper<>();
    wrapper.eq("from_id", id).eq("to_id", toId)
        .or(wq -> wq.eq("from_id", toId).eq("to_id", id))
        .orderByDesc("gmt_create");

    IPage<PrivateChat> page = new Page<>(pageNum, pageSize);
    page = privateChatMapper.selectPage(page, wrapper);
    List<PrivateChat> privateChatList = page.getRecords();

    for (PrivateChat privateChat : privateChatList) {
      Map<String, Object> message = new HashMap<>();
      message.put("time", privateChat.getGmtCreate());
      message.put("content", privateChat.getMessage());
      if (privateChat.getFromId().equals(id)) {
        message.put("isFromMe", 1);
      } else {
        message.put("isFromMe", 0);
      }

      messageList.add(message);
    }

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(messageList);
    return result;
  }

}
