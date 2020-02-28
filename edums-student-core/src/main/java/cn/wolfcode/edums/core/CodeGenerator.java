package cn.wolfcode.edums.core;

import cn.wolfcode.edums.core.controller.BaseController;
import cn.wolfcode.edums.core.domain.BaseDomain;
import cn.wolfcode.edums.core.support.generator.EdumsAutoGenerator;
import cn.wolfcode.edums.core.support.generator.config.EdumsPackageConfig;
import cn.wolfcode.edums.core.support.generator.config.EdumsTemplateConfig;
import cn.wolfcode.edums.core.support.generator.engine.EdumsFreemarkerTemplateEngine;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Leon
 */
public class CodeGenerator {

    private static final String DEFAULT_HTML_MODULE_NAME_TYPE = "1";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip, boolean required) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        String ipt = scanner.nextLine();
        if (required && StringUtils.isBlank(ipt)) {
            throw new MybatisPlusException("请输入正确的" + tip + "！");
        }

        return StringUtils.isBlank(ipt) ? null : ipt;
    }

    public static void main(String[] args) {
        // 代码生成器
        EdumsAutoGenerator mpg = new EdumsAutoGenerator();
        // 模块名称
        String moduleName = scanner("模块名", true);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/edums-student" + moduleName + "/src/main/java");
        gc.setResourceOutputDir(projectPath + "/edums-web/src/main/resources");
        gc.setAuthor("beetle_Lai");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.113.66:3307/crm?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("dev");
        dsc.setPassword("abcd1234");
        mpg.setDataSource(dsc);

        // 包配置
        EdumsPackageConfig pc = new EdumsPackageConfig();
        pc.setModuleName(moduleName);
        String htmlModule = scanner("html 模块名, 输入 1 与模块名相同, 输入回车跳过（默认为 system）, 其他则直接输入", false);
        if (StringUtils.isNotBlank(htmlModule)) {
            if (DEFAULT_HTML_MODULE_NAME_TYPE.equals(htmlModule)) {
                htmlModule = moduleName;
            }
            pc.setHtmlModule(htmlModule);
        }
        pc.setParent("cn.wolfcode.edums");
        pc.setEntity("domain");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                // 用来作为类注释的时间，模板中通过${cfg.datetime}获取
                map.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                this.setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/edums-student" + pc.getModuleName() + "/src/main/resources/cn/wolfcode/edums/" + pc.getModuleName() + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        EdumsTemplateConfig templateConfig = new EdumsTemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseDomain.class);
        strategy.setSuperControllerClass(BaseController.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个按,分割", true).split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new EdumsFreemarkerTemplateEngine());
        mpg.execute();
    }
}
