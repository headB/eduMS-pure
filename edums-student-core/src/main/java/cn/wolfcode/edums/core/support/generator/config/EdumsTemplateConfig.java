package cn.wolfcode.edums.core.support.generator.config;

import cn.wolfcode.edums.core.Constants;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Leon
 * @date 2019-12-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class EdumsTemplateConfig extends TemplateConfig {

    private String listPage = Constants.TEMPLATE_LIST_PAGE;
    private String formPage = Constants.TEMPLATE_FORM_PAGE;
    private String jsPage = Constants.TEMPLATE_JS;

}
