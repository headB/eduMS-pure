package cn.wolfcode.edums.core.vo;

import cn.wolfcode.edums.core.domain.SystemDictionaryDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Leon
 * @date 2020-01-09
 */
@Getter
@Setter
public class SystemDictionaryDetailVO extends SystemDictionaryDetail {
    private String parentName;
}
