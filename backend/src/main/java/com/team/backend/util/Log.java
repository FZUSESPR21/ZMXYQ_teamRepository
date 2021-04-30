package com.team.backend.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : ccreater
 * @ClassName : com.team.backend.util.Log
 * @Description : 类描述
 * @date : 2021-04-30 17:19 Copyright  2021 ccreater. All rights reserved.
 */
@Slf4j
public class Log {
  public static void error(Exception e){
    StringWriter errors = new StringWriter();
    e.printStackTrace(new PrintWriter(errors));
    log.error(errors.toString());
  }
  public static void error(String msg){
    log.error(msg);
  }
}
