package cn.wolfcode.edums.teaching.qo;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.qo.QueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author Leon
 * @date 2020-01-13
 */
@Getter
@Setter
public class StudentChartsQueryObject extends QueryObject {

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = Constants.DEFAULT_DATE_PARTTERN)
    private LocalDate beginDate;

    /**
     * 结束日期，因为只是日期不会涉及到小时因此不需要做特殊处理
     */
    @DateTimeFormat(pattern = Constants.DEFAULT_DATE_PARTTERN)
    private LocalDate endDate;

    /**
     * 所属学科
     */
    private Long trainProductId = -1L;

    /**
     * 班级类型
     */
    private Integer classType = -1;
}
