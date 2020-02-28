package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.domain.Department;
import cn.wolfcode.edums.core.qo.DepartmentQueryObject;
import cn.wolfcode.edums.core.service.IDepartmentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Leon
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController extends BaseController<Department, IDepartmentService> {

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(DepartmentQueryObject qo) {
       return super.query(qo);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:query"})
    @GetMapping(params = "type=all")
    public Object all() {
       return setSuccessModelMap(getService().list());
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
       return super.get(id);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(Department param) {
       return super.update(param);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(Department param) {
       return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.DepartmentController:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        getService().removeByIds(ids);
        return setSuccessModelMap();
    }

}
