package com.team.backend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.model.PersonalCollection
 * @Description : 个人收藏类
 * @date : 2021-05-01 15:37:19 Copyright  2021 user. All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "返回前端个人收藏实体类", description = "")
public class PersonalCollection implements Serializable {

  @ApiModelProperty(value = "收藏ID")
  private Long id;

  @ApiModelProperty(value = "帖子ID")
  private Long postId;

  @ApiModelProperty(value = "发帖人用户名/昵称")
  private String nickName;

  @ApiModelProperty(value = "发帖人头像地址")
  private String iconUrl;

  @ApiModelProperty(value = "帖子内容")
  private String postContent;

  @ApiModelProperty(value = "帖子发布时间")
  private Date postTime;

}
