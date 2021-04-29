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
@ApiModel(value = "PartyComment对象", description = "")
public class PartyComment implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "组局评论ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "内容")
  private String information;

  @ApiModelProperty(value = "对应组局ID")
  private Long partyId;

  @ApiModelProperty(value = "父评论ID")
  private Long preId;

  @ApiModelProperty(value = "评论者ID")
  private Long idFrom;

  @ApiModelProperty(value = "评论状态0(正常状态）,1（举报过多被挂起），2（已被删除）")
  private Integer status;

  @ApiModelProperty(value = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  private Date gmtCreate;

  @ApiModelProperty(value = "逻辑删除")
  @TableLogic
  private Integer deleted;

  @ApiModelProperty(value = "最近一次修改时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date gmtModified;


}
