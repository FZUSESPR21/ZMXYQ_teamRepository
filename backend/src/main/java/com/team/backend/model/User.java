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
 * <p>
 *
 * </p>
 *
 * @author yangyu
 * @since 2021-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户ID(使用微信提供的openID)")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "用户名")
  private String username;

  @ApiModelProperty(value = "用户头像")
  private String userIconUrl;

  @ApiModelProperty(value = "用户证件照")
  private String certificateImageUrl;

  @ApiModelProperty(value = "用户状态(0为未审核，1为已审核)")
  private Integer status;

  @ApiModelProperty(value = "用户学校")
  private String school;

  @ApiModelProperty(value = "毕业年份")
  private Integer admittedYear;

  @ApiModelProperty(value = "性别(0为女，1为男，2为无)")
  private Integer sex;

  @ApiModelProperty(value = "出生年月")
  private Date birthday;

  @ApiModelProperty(value = "是否毕业")
  private Integer isGraduated;

  @ApiModelProperty(value = "省份")
  private String province;

  @ApiModelProperty(value = "城市")
  private String city;

  @ApiModelProperty(value = "人品值")
  private Long rpValue;

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

  @ApiModelProperty(value = "微信提供的OpenID")
  private Long openId;

}
