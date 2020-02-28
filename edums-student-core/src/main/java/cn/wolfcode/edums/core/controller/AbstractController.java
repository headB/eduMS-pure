package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.support.ApplicationContextHolder;
import cn.wolfcode.edums.core.support.http.HttpCode;
import cn.wolfcode.edums.core.support.http.SessionUser;
import cn.wolfcode.edums.core.util.InstanceUtil;
import cn.wolfcode.edums.core.util.ThreadUtil;
import cn.wolfcode.edums.core.util.WebUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 控制器基类
 *
 * @author Leon
 */
public abstract class AbstractController implements InitializingBean {
    protected Logger logger = LogManager.getLogger();

    @Override
    public void afterPropertiesSet() throws Exception {
        Field[] fields = getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object v = field.get(this);
                Class<?> cls = field.getType();
                if (v == null && cls.getSimpleName().toLowerCase().contains("service")) {
                    v = ApplicationContextHolder.getService(cls);
                    field.set(this, v);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            logger.error("", e);
            ThreadUtil.sleep(1, 5);
            afterPropertiesSet();
        }
    }

    /** 获取当前用户Id(shiro) */
    protected SessionUser getCurrUser() {
        return (SessionUser) SecurityUtils.getSubject().getPrincipal();
    }

    /** 获取当前用户Id */
    protected Long getCurrUser(HttpServletRequest request) {
        SessionUser user = WebUtil.getCurrentUser(request);
        if (user == null) {
            return null;
        } else {
            return user.getId();
        }
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap() {
        return setSuccessModelMap(new ModelMap(), null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
        return setSuccessModelMap(modelMap, null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(Object data) {
        return setModelMap(new ModelMap(), HttpCode.OK, data);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
        return setModelMap(modelMap, HttpCode.OK, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code) {
        return setModelMap(new ModelMap(), code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(Integer code, String msg) {
        return setModelMap(new ModelMap(), code, msg, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
        return setModelMap(modelMap, code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code, Object data) {
        return setModelMap(new ModelMap(), code, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(Integer code, String msg, Object data) {
        return setModelMap(new ModelMap(), code, msg, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
        return setModelMap(modelMap, 0, code.msg(), data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, Integer code, String msg, Object data) {
        if (!modelMap.isEmpty()) {
            Map<String, Object> map = InstanceUtil.newLinkedHashMap();
            map.putAll(modelMap);
            modelMap.clear();
            for (Entry<String, Object> entry : map.entrySet()) {
                if (!entry.getKey().startsWith("org.springframework.validation.BindingResult")
                        && !"void".equals(entry.getKey())) {
                    modelMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (data != null) {
            if (data instanceof Page<?>) {
                Page<?> page = (Page<?>) data;
                modelMap.put("data", page.getRecords());
                modelMap.put("current", page.getCurrent());
                modelMap.put("size", page.getSize());
                modelMap.put("pages", page.getPages());
                modelMap.put("count", page.getTotal());
            } else {
                modelMap.put("data", data);
            }
        }
        modelMap.put("code", code);
        modelMap.put("msg", msg);
        modelMap.put("timestamp", System.currentTimeMillis());
        logger.info("response===>{}", JSON.toJSONString(modelMap));
        return ResponseEntity.ok(modelMap);
    }
}