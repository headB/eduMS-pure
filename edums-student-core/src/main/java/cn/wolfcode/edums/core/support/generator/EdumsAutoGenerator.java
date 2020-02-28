package cn.wolfcode.edums.core.support.generator;

import cn.wolfcode.edums.core.support.generator.config.EdumsPackageConfig;
import cn.wolfcode.edums.core.support.generator.config.EdumsTemplateConfig;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leon
 * @date 2019-12-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class EdumsAutoGenerator extends AutoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 包 相关配置
     */
    private EdumsPackageConfig packageInfo;
    /**
     * 模板 相关配置
     */
    private EdumsTemplateConfig template;

    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 生成代码
     */
    @Override
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(packageInfo, getDataSource(), getStrategy(), template, getGlobalConfig());
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == getTemplateEngine()) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            templateEngine = new VelocityTemplateEngine();
        }
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================文件生成完成！！！==========================");
    }
}
