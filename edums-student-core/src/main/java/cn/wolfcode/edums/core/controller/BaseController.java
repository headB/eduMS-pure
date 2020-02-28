package cn.wolfcode.edums.core.controller;


import cn.wolfcode.edums.core.domain.BaseDomain;
import cn.wolfcode.edums.core.qo.QueryObject;
import cn.wolfcode.edums.core.support.ApplicationContextHolder;
import cn.wolfcode.edums.core.util.ThreadUtil;
import cn.wolfcode.edums.core.vo.DynamicHeadExportVO;
import cn.wolfcode.edums.core.vo.ExportVO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 控制器基类
 *
 * @author Leon
 */
public abstract class BaseController<T extends BaseDomain, S extends IService<T>> extends AbstractController {
    private S service;

    protected S getService() {
        if (service == null) {
            synchronized (this) {
                if (service == null) {
                    ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
                    Class<?> cls = (Class<?>) type.getActualTypeArguments()[1];
                    try {
                        service = (S) ApplicationContextHolder.getService(cls);
                    } catch (Exception e) {
                        logger.error("", e);
                        ThreadUtil.sleep(1, 5);
                    }
                }
            }
        }
        return service;
    }

    /**
     * 分页查询
     */
    public Object query(QueryObject qo) {
        return query(new ModelMap(), new Page<>(qo.getCurrent(), qo.getSize()), qo);
    }

    /**
     * 分页查询
     */
    public Object query(ModelMap modelMap, Page<T> pageParam, QueryObject qo) {
        // 排序参数处理
        OrderItem orderItem;
        if (StringUtils.hasLength(qo.getField())) {
            if (QueryObject.ASC.equals(qo.getOrder())) {
                orderItem = OrderItem.asc(qo.getField());
            } else {
                orderItem = OrderItem.desc(qo.getField());
            }
            pageParam.addOrder(orderItem);
        }
        Page page = getService().page(pageParam, qo);
        return setSuccessModelMap(modelMap, page);
    }

    /**
     * 查询
     */
    public Object queryList(Map<String, Object> param) {
        return queryList(new ModelMap(), param);
    }

    /**
     * 查询
     */
    public Object queryList(ModelMap modelMap, Map<String, Object> param) {
        List<?> list = getService().listByMap(param);
        return setSuccessModelMap(modelMap, list);
    }

    public Object get(Long id) {
        return get(new ModelMap(), new BaseDomain(id));
    }

    public Object get(ModelMap modelMap, BaseDomain param) {
        Object result = getService().getById(param.getId());
        return setSuccessModelMap(modelMap, result);
    }

    public Object update(T param) {
        return update(new ModelMap(), param);
    }

    public Object update(ModelMap modelMap, T param) {
        if (param.getId() == null) {
            getService().save(param);
        } else {
            getService().updateById(param);
        }
        return setSuccessModelMap(modelMap);
    }

    /**
     * 物理删除
     */
    public Object delete(Long id) {
        return delete(new ModelMap(), Collections.singletonList(id));
    }

    /**
     * 物理删除
     */
    public Object delete(Collection<? extends Serializable> idList) {
        return delete(new ModelMap(), idList);
    }

    /**
     * 物理删除
     */
    public Object delete(ModelMap modelMap, Collection<? extends Serializable> idList) {
        Assert.notEmpty(idList, "ID不能为空");
        getService().removeByIds(idList);
        return setSuccessModelMap(modelMap);
    }

    /**
     * 逻辑删除
     */
    public Object del(Long id) {
        return del(new ModelMap(), Collections.singletonList(id));
    }

    /**
     * 逻辑删除
     */
    public Object del(Collection<? extends Serializable> idList) {
        return del(new ModelMap(), idList);
    }

    /**
     * 逻辑删除
     */
    public Object del(ModelMap modelMap, Collection<? extends Serializable> idList) {
        Assert.notEmpty(idList, "ID不能为空");
        // TODO 逻辑删除
        System.out.println(idList);
        return setSuccessModelMap(modelMap);
    }

    protected void export(ExportVO vo, HttpServletResponse resp) throws Exception {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        resp.setContentType("application/vnd.ms-excel");
        resp.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(vo.getFileName(), "UTF-8");
        resp.setHeader("Content-disposition", "attachment;filename=" + fileName + ExportVO.DEFAULT_SUFFIX_XLSX);

        ExcelWriterBuilder builder = EasyExcel.write(resp.getOutputStream(), vo.getClazz())
                .includeColumnFiledNames(vo.getIncludeFiledNames())
                .excludeColumnFiledNames(vo.getExcludeFiledNames());

        if (vo instanceof DynamicHeadExportVO) {
            DynamicHeadExportVO dvo = (DynamicHeadExportVO) vo;
            builder.head(dvo.getHeadList());
        }

        builder.autoCloseStream(Boolean.FALSE).sheet(0).doWrite(vo.getDataList());
    }
}
