package cn.wolfcode.edums.core.shiro;

import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.shiro.jwt.JWTCredentialsMatcher;
import cn.wolfcode.edums.core.shiro.jwt.JWTToken;
import cn.wolfcode.edums.core.support.http.SessionUser;
import cn.wolfcode.edums.core.util.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 自定义身份认证
 * 基于HMAC（ 散列消息认证码）的控制域
 */
public class JWTShiroRealm extends BaseAuthShiroRealm {
    private final Logger log = LoggerFactory.getLogger(JWTShiroRealm.class);

    @Autowired
    private IUserinfoService userService;

    public JWTShiroRealm(EhCacheManager ehCacheManager) {
        super(ehCacheManager);
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
        /* 开启认证与授权缓存 */
        this.setAuthenticationCachingEnabled(true);
        this.setAuthorizationCachingEnabled(true);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();

        Userinfo user = userService.selectByName(JwtUtils.getUsername(token));
        if (user == null) {
            throw new AuthenticationException("token过期，请重新登录");
        }

        // 封装 session user 必要数据
        SessionUser sessionUser = super.createSessionUser(user);

        return new SimpleAuthenticationInfo(sessionUser, sessionUser.getUsername(), "jwtRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
}

