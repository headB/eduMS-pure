package cn.wolfcode.edums.core.domain;

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
 * @author chenhui
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_systemdictionary")
public class SystemDictionary extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String sn;

    private String title;

    private String intro;

    private String category;


}
