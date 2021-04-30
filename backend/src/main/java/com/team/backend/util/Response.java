package com.team.backend.util;

import java.util.Map;
import lombok.Data;

/**
 * @author : ccreater
 * @ClassName : com.team.backend.util.Response
 * @Description : Response工具类
 * @date : 2021-04-30 13:10 Copyright  2021 ccreater. All rights reserved.
 */
@Data
public class Response {
  private int code;
  private String message;
  private Map<String, Object> data;
  public Response(int code,String message){
    this.code = code;
    this.message = message;
  }
  public Response(int code,String message,Map<String, Object> data){
    this(code,message);
    this.data = data;
  }
}
