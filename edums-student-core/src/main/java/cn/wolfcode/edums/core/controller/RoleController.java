package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.domain.Role;
import cn.wolfcode.edums.core.qo.QueryObject;
import cn.wolfcode.edums.core.service.IRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author beetle_Lai
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, IRoleService> {

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.RoleController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(QueryObject qo) {
       return super.query(qo);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.RoleController:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
       return super.get(id);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.RoleController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(Role param) {
       return super.update(param);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.RoleController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(Role param) {
       return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.RoleController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
       return super.del(ids);
    }

}
