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
@ApiModel(value = "PostComment对象", description = "")
public class PostComment implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  private Date gmtCreate;

  @ApiModelProperty(value = "最近一次修改时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date gmtModified;

  @ApiModelProperty(value = "逻辑删除")
  @TableLogic
  @TableField(fill = FieldFill.INSERT)
  private Integer deleted;

  @ApiModelProperty(value = "所属评论ID，外键")
  private Long preId;

  @ApiModelProperty(value = "所属帖子ID，外键")
  private Long postId;

  @ApiModelProperty(value = "发布评论用户ID，外键")
  private Long idFrom;

  @ApiModelProperty(value = "评论状态：0(正常状态）,1（举报过多被挂起），2（已被删除）")
  private Integer status;

  private String message;

  @ApiModelProperty(value = "被回复用户ID，外键")
  private Long idTo;

  @TableField(exist = false)
  private String nameFrom;

  @ApiModelProperty(value = "发帖者用户名")
  @TableField(exist = false)
  private String username;
}
