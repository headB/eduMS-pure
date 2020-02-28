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
public class PermissionQueryObject extends QueryObject {

    private Integer status = Constants.EDUMS_PERMISSION_STATUS;
}
