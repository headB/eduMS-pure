package cn.wolfcode.edums.core.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2019-12-27
 */
@Getter
@Setter
public class DepartmentQueryObject extends QueryObject {

    private String field = "c.sequence";
    private String order = ASC;
    private Long parentId = -1L;
}
