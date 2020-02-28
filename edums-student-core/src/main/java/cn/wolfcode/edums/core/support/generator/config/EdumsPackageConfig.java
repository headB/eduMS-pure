package cn.wolfcode.edums.core.support.generator.config;

import cn.wolfcode.edums.core.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
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
public class EdumsPackageConfig extends PackageConfig {

    private String resourcesParent = "static.dist";
    private String htmlModule = "system";
    private String views = "views";
    private String jsPage = "controller";

    public String getViews() {
        if (StringUtils.isNotBlank(htmlModule)) {
            return views + StringPool.DOT + htmlModule + StringPool.DOT + Constants.STRING_PLACEHOLDER;
        }
        return views;
    }
}
