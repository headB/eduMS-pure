package cn.wolfcode.edums.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_trole")
public class Role extends BaseDomain {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;

    @TableField("path")
    private String path;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("status")
    private Integer status;

    @TableField("types")
    private Integer types;

    @TableField("roletype")
    private Integer roleType;

    @TableField("rolegroup_id")
    private Long roleGroupId;

    @TableField("parent_id")
    private Long parentId;


}
