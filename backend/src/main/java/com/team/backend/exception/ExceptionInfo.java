package com.team.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.exception.ExceptionInfo
 * @Description : 错误信息枚举类
 * @date : 2021-04-29 12:50:33 Copyright  2021 user. All rights reserved.
 */

/**
 * 60: 用户异常
 * 61: 帖子异常
 * 62: 组局异常
 */
@AllArgsConstructor
@Getter
public enum ExceptionInfo {

  OK(200,"ok");

  private Integer code;
  private String message;



}
