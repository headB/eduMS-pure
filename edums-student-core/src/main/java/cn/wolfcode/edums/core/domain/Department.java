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
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_department")
public class Department extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String sn;

    private String title;

    private String intro;

    @TableField("dirpath")
    private String dirPath;

    private String tel;

    private String fax;

    private Integer sequence;

    @TableField("parent_id")
    private Long parentId;


}
