package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.domain.SystemDictionary;
import cn.wolfcode.edums.core.domain.SystemDictionaryDetail;
import cn.wolfcode.edums.core.qo.QueryObject;
import cn.wolfcode.edums.core.service.ISystemDictionaryDetailService;
import cn.wolfcode.edums.core.service.ISystemDictionaryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenhui
 * @since 2019-12-31
 */
@RestController
@RequestMapping("/dictionary")
public class SystemDictionaryController extends BaseController<SystemDictionary, ISystemDictionaryService> {

    @Autowired
    private ISystemDictionaryDetailService systemDictionaryDetailService;

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionaryController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(QueryObject qo) {
        return super.query(qo);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionaryController:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
        return super.get(id);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionaryController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(SystemDictionary param) {
        return super.update(param);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionaryController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(SystemDictionary param) {
        return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionaryController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        return super.delete(ids);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionarydetailController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping("/details")
    public Object all(@RequestParam(required = false) String sn) {
        return setSuccessModelMap(systemDictionaryDetailService.listByParentSn(sn));
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionarydetailController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping("/details")
    public Object createDetail(SystemDictionaryDetail param) {
        systemDictionaryDetailService.save(param);
        return setSuccessModelMap();
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionarydetailController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping("/details")
    public Object updateDetail(SystemDictionaryDetail param) {
        systemDictionaryDetailService.updateById(param);
        return setSuccessModelMap();
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemdictionarydetailController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping("/details")
    public Object deleteDetail(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        systemDictionaryDetailService.removeByIds(ids);
        return setSuccessModelMap();
    }

}
