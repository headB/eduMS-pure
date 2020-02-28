package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.domain.SystemMenu;
import cn.wolfcode.edums.core.qo.SystemMenuQueryObject;
import cn.wolfcode.edums.core.service.ISystemMenuService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Leon
 * @since 2019-12-24
 */
@RestController
@RequestMapping("/menus")
public class SystemMenuController extends BaseController<SystemMenu, ISystemMenuService> {

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query() {
        return setSuccessModelMap(getService().queryMenuListByCurUser());
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:list", "分页查询"}, logical = Logical.OR)
    @GetMapping("/list")
    public Object list(SystemMenuQueryObject qo) {
        return super.query(qo);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
        return super.get(id);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(SystemMenu param) {
        return super.update(param);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(SystemMenu param) {
        return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.SystemmenuController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        return super.del(ids);
    }

}
