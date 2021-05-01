package com.team.backend.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ccreater
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Admin对象", description = "")
public class Admin implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  private Date gmtCreate;

  @ApiModelProperty(value = "修改时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date gmtModified;

  @ApiModelProperty(value = "逻辑删除")
  @TableLogic
  @TableField(fill = FieldFill.INSERT)
  private Integer deleted;

  @ApiModelProperty(value = "管理员ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "密码")
  private String password;

  @ApiModelProperty(value = "昵称")
  private String nickname;

  @ApiModelProperty(value = "头像")
  private String adminIconUrl;

  public Admin(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
    this.deleted = 0;
    this.adminIconUrl = "/favicon.ico";
  }


}
