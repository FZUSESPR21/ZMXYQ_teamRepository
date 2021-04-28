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
@ApiModel(value="Party对象", description="")
public class Party implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "组局ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "组局描述")
    private String description;

    @ApiModelProperty(value = "发布者ID")
    private Long publisherId;

    @ApiModelProperty(value = "组局类型ID")
    private Long partyTypeId;

    @ApiModelProperty(value = "组局人数限定")
    private Integer peopleCnt;

    @ApiModelProperty(value = "图片url链接")
    private String imageUrls;

    @ApiModelProperty(value = "创建时间")
      @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;


}
