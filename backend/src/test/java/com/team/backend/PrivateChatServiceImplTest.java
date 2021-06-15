package com.team.backend;

import com.team.backend.exception.ExceptionInfo;
import com.team.backend.model.Result;
import com.team.backend.service.impl.PrivateChatServiceImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.PrivateChatServiceImplTest
 * @Description : PrivateChatServiceImpl测试类
 * @date : 2021-06-15 20:28:27 Copyright  2021 user. All rights reserved.
 */
@SpringBootTest
public class PrivateChatServiceImplTest {

  @Autowired
  PrivateChatServiceImpl privateChatService;

  @Test
  void listChatTest(){

    Result<List<Map<String, Object>>> result = privateChatService.listChat(123456L);
    System.out.println(result);
  }

  @Test
  void clearListTest(){
    Result<Integer> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(3);
    Assertions.assertEquals(result,privateChatService.clearList(123456L));
  }

  @Test
  void deleteOneTest(){

    Result<Integer> result = new Result<>();

    result.setCode(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getCode());
    result.setMessage(ExceptionInfo.valueOf("USER_DIALOG_ID_NULL").getMessage());

    Assertions.assertEquals(result,privateChatService.deleteOne(123456L,null));

    result.setCode(ExceptionInfo.valueOf("OK").getCode());
    result.setMessage(ExceptionInfo.valueOf("OK").getMessage());
    result.setData(1);
    Assertions.assertEquals(result,privateChatService.deleteOne(123456L,3L));
  }

}
