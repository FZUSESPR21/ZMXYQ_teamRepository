package com.team.backend.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.handler.MyMetaObjectHandler
 * @Description : 数据处理类
 * @date : 2021-04-26 14:22:54 Copyright  2021 user. All rights reserved.
 */

@Slf4j
@Component // 把处理器加到IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {

  // 插入时的填充策略
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("start insert fill ....");
    this.setFieldValByName("gmtCreat", new Date(), metaObject);
    this.setFieldValByName("gmtModified", new Date(), metaObject);
  }

  // 更新时的填充策略
  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("start update fill ....");
    this.setFieldValByName("gmtModified", new Date(), metaObject);
  }
}
