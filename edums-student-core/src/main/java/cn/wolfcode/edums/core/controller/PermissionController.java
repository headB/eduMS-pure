package cn.wolfcode.edums.core.controller;


import cn.wolfcode.edums.core.domain.Permission;
import cn.wolfcode.edums.core.qo.PermissionQueryObject;
import cn.wolfcode.edums.core.service.IPermissionService;
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
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController<Permission, IPermissionService> {

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.PermissionController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(PermissionQueryObject qo) {
        return super.query(qo);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.PermissionController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        return super.del(ids);
    }
}
