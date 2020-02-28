package cn.wolfcode.edums.core.vo;

import cn.wolfcode.edums.core.domain.Department;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2020-01-08
 */
@Getter
@Setter
public class DepartmentVO extends Department {

    private String parentName;
}
