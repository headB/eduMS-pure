package cn.wolfcode.edums.core.shiro;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.support.http.SessionUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leon
 */
public class DbShiroRealm extends BaseAuthShiroRealm {

    @Autowired
    private IUserinfoService userService;

    public DbShiroRealm(EhCacheManager ehCacheManager) {
        super(ehCacheManager);
        this.setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
        /* 开启认证与授权缓存 */
        this.setAuthenticationCachingEnabled(true);
        this.setAuthorizationCachingEnabled(true);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        Userinfo user = userService.selectByName(username);
        if (user == null) {
            throw new AuthenticationException("用户名或者密码错误");
        }

        // 封装 session user 必要数据
        SessionUser sessionUser = super.createSessionUser(user);

        return new SimpleAuthenticationInfo(sessionUser, user.getPassword(), "dbRealm");
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        SessionUser user = (SessionUser) principals.getPrimaryPrincipal();

        // 如果是超级管理员 返回所有权限
        if (Constants.ADMIN.equals(user.getUsername())) {
            simpleAuthorizationInfo.addRole(Constants.ADMIN);
            simpleAuthorizationInfo.addStringPermission(Constants.PERMISSION_EXP_ALL);
            return simpleAuthorizationInfo;
        }

        simpleAuthorizationInfo.addRoles(user.getRoles());
        simpleAuthorizationInfo.addStringPermissions(user.getPermissions());

        return simpleAuthorizationInfo;
    }


}

