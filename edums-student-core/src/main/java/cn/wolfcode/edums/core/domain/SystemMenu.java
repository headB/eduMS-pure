package cn.wolfcode.edums.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.wolfcode.edums.core.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Leon
 * @since 2019-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_systemmenu")
public class SystemMenu extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

    private String types;

    private Integer sequence;

    @TableField("appclass")
    private String appClass;

    @TableField("otherscripts")
    private String otherScripts;

    private String pack;

    private String icon;

    private Integer status;

    private String params;

    private Double fee;

    private String sn;

    @TableField("vrtype")
    private String vrType;

    @TableField("therole")
    private String theRole;

    @TableField("issystem")
    private Boolean isSystem;

    @TableField("actionclass")
    private String actionClass;

    @TableField("parent_id")
    private Long parentId;

}
