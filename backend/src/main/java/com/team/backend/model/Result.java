package com.team.backend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.model.Result
 * @Description : 返回前端结果集类
 * @date : 2021-04-29 12:48:33 Copyright  2021 user. All rights reserved.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "返回前端结果实体类", description = "")
public class Result<T> implements Serializable {

  @ApiModelProperty(value = "信息代码")
  private Integer code;

  @ApiModelProperty(value = "信息内容")
  private String message;

  @ApiModelProperty(value = "结果数据")
  private T data;

}
