package cn.wolfcode.edums.core.vo;

import cn.wolfcode.edums.core.domain.SystemMenu;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2020-02-21
 */
@Getter
@Setter
public class SystemMenuVO extends SystemMenu {

    private String parentName;
}
