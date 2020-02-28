package cn.wolfcode.edums.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@TableName("t_tpermission")
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String name;

    private String sn;

    private String description;

    private String operation;

    private Integer status;

    @TableField("parent_id")
    private Long parentId;


}
