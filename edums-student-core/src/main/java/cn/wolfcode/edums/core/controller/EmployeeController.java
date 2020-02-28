package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.domain.Employee;
import cn.wolfcode.edums.core.qo.EmployeeQueryObject;
import cn.wolfcode.edums.core.service.IEmployeeService;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.vo.EmployeeVO;
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
 * @author Leon
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController extends BaseController<Employee, IEmployeeService> {

    @Autowired
    private IUserinfoService userinfoService;

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(EmployeeQueryObject qo) {
        return super.query(qo);
    }

    @Override
    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
        return super.get(id);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(EmployeeVO param) {
        return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(EmployeeVO param) {
        return super.update(param);
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:stop", "停用员工"}, logical = Logical.OR)
    @DeleteMapping
    public Object stop(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        userinfoService.leave(ids);
        return setSuccessModelMap();
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:restore", "恢复停用员工"}, logical = Logical.OR)
    @PatchMapping("/{id}/restore")
    public Object stop(@PathVariable("id") Long id) {
        userinfoService.restore(id);
        return setSuccessModelMap();
    }

    @RequiresPermissions(value = {"cn.wolfcode.edums.core.controller.EmployeeController:resetpwd", "重置密码"}, logical = Logical.OR)
    @PatchMapping("/resetpwd")
    public Object resetpwd(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
        userinfoService.resetPwd(ids);
        return setSuccessModelMap();
    }

}
