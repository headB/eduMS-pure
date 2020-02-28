package cn.wolfcode.edums.core.support.login;

import cn.wolfcode.edums.core.exception.LoginException;
import cn.wolfcode.edums.core.support.context.Resources;
import cn.wolfcode.edums.core.vo.Login;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public final class LoginHelper {
    private LoginHelper() {
    }

    /** 用户登录 */
    public static final Boolean login(Login user, String host) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword(), host);
        token.setRememberMe(user.getRememberMe());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);

            return subject.isAuthenticated();
        } catch (LockedAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", token.getPrincipal()));
        } catch (DisabledAccountException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", token.getPrincipal()));
        } catch (ExpiredCredentialsException e) {
            throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", token.getPrincipal()));
        } catch (Exception e) {
            throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
        }
    }
}
