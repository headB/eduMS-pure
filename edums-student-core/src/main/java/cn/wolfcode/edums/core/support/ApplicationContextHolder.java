package cn.wolfcode.edums.core.support;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.controller.BaseController;
import cn.wolfcode.edums.core.domain.Permission;
import cn.wolfcode.edums.core.service.IPermissionService;
import cn.wolfcode.edums.core.util.InstanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Leon
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static final Logger logger = LogManager.getLogger();
    private static ApplicationContext applicationContext;
    private static final Map<String, Object> SERVICE_FACTORY = new HashMap<>();

    @Autowired
    private IPermissionService permissionService;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    @PostConstruct
    private void init() {
        logger.info("------------------ 初始化权限开始 ------------------");
        try {
            Set<String> exps = permissionService.selectAllSnList();

            // 转换权限表达式
            int oldPermsNum = exps.size();
            Map<String, BaseController> beans = applicationContext.getBeansOfType(BaseController.class);
            Set<Permission> newPerms = InstanceUtil.newHashSet();

            beans.forEach((key, value) -> {
                Class<?> superclass = value.getClass().getSuperclass();
                // 若被 cglib 代理则尝试获取方法上的权限注解
                if (!superclass.getName().equalsIgnoreCase(BaseController.class.getName())) {
                    Method[] methods = superclass.getDeclaredMethods();
                    Arrays.stream(methods).forEach(m -> {
                        RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                        if (rp != null) {
                            String[] expArr = rp.value();
                            if (expArr.length > 0) {
                                String exp = expArr[0];
                                String desc = expArr[expArr.length - 1];

                                // 初始化该类的所有方法 xxx.xxx.XXXxx:*
                                addPerms(newPerms, exps, exp.split(":")[0] + ":" + Constants.ASTERISK_FOR_SYMBOL, "所有方法");

                                // 加载实际的方法权限
                                addPerms(newPerms, exps, exp, desc);
                            }
                        }
                    });
                }
            });

            if (newPerms.size() > 0) {
                permissionService.saveBatch(newPerms);
            }
            logger.info("[init perms] find old permission[{}], generate new permission[{}]", oldPermsNum, newPerms.size());
        } catch (Exception e) {
            logger.error("[init perms] initialize permission field. because:{}", e.getMessage());
        }
        logger.info("------------------ 初始化权限结束 ------------------");
    }

    private void addPerms(Set<Permission> newPerms, Set<String> exps, String realExp, String desc) {
        if (!exps.contains(realExp)) {
            Permission permission = new Permission(realExp, null, desc, null, Constants.EDUMS_PERMISSION_STATUS, null);
            newPerms.add(permission);
            exps.add(realExp);
        }
    }

    public static <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> t) {
        return applicationContext.getBeansOfType(t);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getService(Class<T> cls) {
        final String clsName = cls.getName();
        T v = (T) SERVICE_FACTORY.get(clsName);
        if (v == null) {
            synchronized (clsName) {
                v = (T) SERVICE_FACTORY.get(clsName);
                if (v == null) {
                    logger.info("*****Autowire {}*****", cls);
                    v = getBean(cls);
                    logger.info("*****{} Autowired*****", cls);
                    SERVICE_FACTORY.put(clsName, v);
                }
            }
        }
        return v;
    }
}
