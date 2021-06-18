package com.team.backend.handler;

import com.team.backend.mapper.UserMapper;
import com.team.backend.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.handler.SetRpValueScheduled
 * @Description : 定时更新人品值类
 * @date : 2021-06-18 11:30:35 Copyright  2021 user. All rights reserved.
 */
@Component
@Configuration
@EnableScheduling
public class SetRpValueScheduled {

  @Autowired
  UserMapper userMapper;

  // 添加定时任务,每天凌晨0点更新一次
  @Scheduled(cron = "0 0 0 * * ?")
  private void configureTasks() throws Exception{

    System.err.println("执行静态定时任务时间: " + LocalDateTime.now());

    List<User> userList = userMapper.selectList(null);
    for (User user: userList){
      user.setRpValue(user.getRpValue()+50);// 每天人品值+50
      userMapper.updateById(user);
    }
  }
}
