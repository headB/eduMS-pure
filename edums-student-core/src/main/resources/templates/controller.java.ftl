package ${package.Controller};

import cn.wolfcode.edums.core.controller.BaseController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.wolfcode.edums.core.qo.QueryObject;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

import java.util.ArrayList;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${entity?uncap_first}s")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}, ${table.serviceName}> {
<#else>
public class ${table.controllerName} {
</#if>

    @RequiresPermissions(value = {"${package.Controller}.${table.controllerName}:query", "分页查询"}, logical = Logical.OR)
    @GetMapping
    public Object query(QueryObject qo) {
       return super.query(qo);
    }

    @Override
    @RequiresPermissions(value = {"${package.Controller}.${table.controllerName}:get", "查看详情"}, logical = Logical.OR)
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Long id) {
       return super.get(id);
    }

    @RequiresPermissions(value = {"${package.Controller}.${table.controllerName}:create", "新增数据"}, logical = Logical.OR)
    @PostMapping
    public Object create(${entity} param) {
       return super.update(param);
    }

    @Override
    @RequiresPermissions(value = {"${package.Controller}.${table.controllerName}:update", "更新数据"}, logical = Logical.OR)
    @PutMapping
    public Object update(${entity} param) {
       return super.update(param);
    }

    @RequiresPermissions(value = {"${package.Controller}.${table.controllerName}:delete", "删除数据"}, logical = Logical.OR)
    @DeleteMapping
    public Object delete(@RequestParam(value = "ids[]", required = false) ArrayList<Long> ids) {
       return super.del(ids);
    }

}
</#if>
