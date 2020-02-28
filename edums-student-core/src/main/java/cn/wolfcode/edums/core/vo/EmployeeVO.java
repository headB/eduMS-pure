package cn.wolfcode.edums.core.vo;

import cn.wolfcode.edums.core.domain.Employee;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2019-12-25
 */
@Getter
@Setter
public class EmployeeVO extends Employee {

    private String email;
    private String deptName;
    private String username;
    private Integer status;

    @Override
    public String toString() {
        return super.toString() + ", EmployeeVO{" +
                "deptName='" + deptName + '\'' +
                '}';
    }
}
