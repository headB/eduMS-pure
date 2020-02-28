package cn.wolfcode.edums.core.shiro;

import cn.wolfcode.edums.core.domain.Department;
import cn.wolfcode.edums.core.domain.Employee;
import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.service.IDepartmentService;
import cn.wolfcode.edums.core.service.IEmployeeService;
import cn.wolfcode.edums.core.service.IPermissionService;
import cn.wolfcode.edums.core.service.IRoleService;
import cn.wolfcode.edums.core.support.http.SessionUser;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author Leon
 * @date 2019-12-25
 */
abstract class BaseAuthShiroRealm extends AuthorizingRealm {

    BaseAuthShiroRealm(EhCacheManager ehCacheManager) {
        super(ehCacheManager);
    }

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;

    protected SessionUser createSessionUser(Userinfo user) {
        Employee emp = employeeService.getById(user.getId());
        Assert.notNull(emp, String.format("%s 用户数据异常，无员工数据", user.getName()));
        String deptName = "";
        if (emp.getDeptId() != null) {
            Department dept = departmentService.getById(emp.getDeptId());
            if (dept != null) {
                deptName = dept.getTitle();
            }
        }

        // 封装 session user
        SessionUser sessionUser = new SessionUser(emp.getId(), user.getName(), user.getEmail(), emp.getTrueName(), emp.getSex(),
                emp.getDuty(), emp.getTel(), emp.getBirthDay(), emp.getDeptId(), deptName, emp.getQq());

        // 封装角色与权限
        Set<String> roles = roleService.selectRoleSnListByUserId(user.getId());
        if (!CollectionUtils.isEmpty(roles)) {
            sessionUser.getRoles().addAll(roles);
        }
        Set<String> permissions = permissionService.selectPermsSnListByUserId(user.getId());
        if (!CollectionUtils.isEmpty(permissions)) {
            sessionUser.getPermissions().addAll(permissions);
        }

        return sessionUser;
    }
}
