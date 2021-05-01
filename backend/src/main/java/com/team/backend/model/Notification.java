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
 * @since 2021-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Notification对象", description = "")
public class Notification implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "消息表ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "消息对应类表中的ID")
  private Long sourceId;

  @ApiModelProperty(value = "消息对应类型(0:评论,1:点赞,2:赞赏,3:收藏)")
  private Integer type;

  @ApiModelProperty(value = "消息是否已读(0:未读,1:已读)")
  private Integer isRead;

  @ApiModelProperty(value = "创建时间")
  @TableField(fill = FieldFill.INSERT)
  private Date gmtCreate;

  @ApiModelProperty(value = "最近一次修改时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date gmtModified;

  @ApiModelProperty(value = "逻辑删除")
  @TableField(fill = FieldFill.INSERT)
  @TableLogic
  private Integer deleted;


}
