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
@ApiModel(value = "PostReward对象", description = "")
public class PostReward implements Serializable {

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

  @ApiModelProperty(value = "被赞赏帖子ID，外键")
  private Long postId;

  @ApiModelProperty(value = "赞赏用户ID，外键")
  private Long idFrom;

  @ApiModelProperty(value = "赞赏数量")
  private Integer amount;


}
