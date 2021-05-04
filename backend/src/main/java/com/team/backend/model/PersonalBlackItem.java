package com.team.backend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.model.PersonalBlackItem
 * @Description : 个人拉黑列表项类
 * @date : 2021-05-01 16:55:44 Copyright  2021 user. All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "返回前端个人黑名单实体类", description = "")
public class PersonalBlackItem implements Serializable {

  @ApiModelProperty(value = "拉黑列表ID")
  private Long id;

  @ApiModelProperty(value = "被拉黑用户ID")
  private Long beUserId;

  @ApiModelProperty(value = "被拉黑用户头像")
  private String iconUrl;

  @ApiModelProperty(value = "被拉黑用户昵称")
  private String nickName;

}
