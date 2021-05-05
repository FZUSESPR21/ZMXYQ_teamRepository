package com.team.backend.util;

import com.team.backend.exception.ExceptionInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
  private int code;
  private String message;
  private T data;

  public Result(T data){
    this.data = data;
  }

  public static Result success() {
    Result result = new Result<>();
    result.setCode(ExceptionInfo.OK.getCode());
    result.setMessage(ExceptionInfo.OK.getMessage());
    return result;
  }

  public static <T> Result<T> success(T data) {
    Result<T> result = new Result<>(data);
    result.setCode(ExceptionInfo.OK.getCode());
    result.setMessage(ExceptionInfo.OK.getMessage());
    return result;
  }

  public static Result error(int code, String msg) {
    Result result = new Result();
    result.setCode(code);
    result.setMessage(msg);
    return result;
  }
}
