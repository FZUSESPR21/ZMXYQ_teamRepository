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
@ApiModel(value="Post对象", description="")
public class Post implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "帖文ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发布者ID，外键")
    private Long publisherId;

    @ApiModelProperty(value = "帖子状态：0(正常状态）,1（举报过多被挂起），2（已被删除）")
    private Integer status;

    @ApiModelProperty(value = "帖子类型ID，外键")
    private Long postTypeId;

    @ApiModelProperty(value = "帖子文本内容")
    private String message;

    @ApiModelProperty(value = "帖子图片URL")
    private String imageUrls;

    @ApiModelProperty(value = "创建时间")
      @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "最近一次修改时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;


}
