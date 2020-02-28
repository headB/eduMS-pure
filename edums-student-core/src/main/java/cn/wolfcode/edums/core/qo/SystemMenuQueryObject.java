package cn.wolfcode.edums.core.qo;

import cn.wolfcode.edums.core.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2019-12-27
 */
@Getter
@Setter
public class SystemMenuQueryObject extends QueryObject {

    private Long parentId;
    private String type = Constants.EDUMS_SYSTEMMENU_TYPE;
    private String field = "c.sequence";

}