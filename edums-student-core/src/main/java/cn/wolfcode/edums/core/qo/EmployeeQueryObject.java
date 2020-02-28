package cn.wolfcode.edums.core.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2019-12-27
 */
@Getter
@Setter
public class EmployeeQueryObject extends QueryObject {

    private Long deptId = -1L;
    private String sex;

    public String getSex() {
        return empty2null(sex);
    }
}
