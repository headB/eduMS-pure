package cn.wolfcode.edums.core.controller;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.support.context.Resources;
import cn.wolfcode.edums.core.support.http.HttpCode;
import cn.wolfcode.edums.core.support.login.LoginHelper;
import cn.wolfcode.edums.core.util.JwtUtils;
import cn.wolfcode.edums.core.util.WebUtil;
import cn.wolfcode.edums.core.vo.Login;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录
 */
@RestController
public class LoginController extends BaseController<Userinfo, IUserinfoService> {

    /**
     * 登录
     *
     * @param user
     * @param request
     * @return
     * @throws LoginException
     */
    @PostMapping("/login")
    public Object login(Login user, HttpServletRequest request) throws LoginException {
        Assert.notNull(user.getAccount(), Resources.getMessage("ACCOUNT_IS_NULL"));
        Assert.notNull(user.getPassword(), Resources.getMessage("PASSWORD_IS_NULL"));
        if (LoginHelper.login(user, WebUtil.getHost(request))) {
            String token = JwtUtils.generateJwtToken(user.getAccount());
            return setSuccessModelMap(token);
        }
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    /**
     * 登出
     *
     * @param modelMap
     * @return
     */
    @PostMapping("/logout")
    public Object logout(ModelMap modelMap) {
        SecurityUtils.getSubject().logout();
        return setSuccessModelMap(modelMap);
    }

    /**
     * 注册
     *
     * @param request
     * @param modelMap
     * @param userinfo
     * @return
     */
    @PostMapping("/regin")
    public Object regin(HttpServletRequest request, ModelMap modelMap, Userinfo userinfo) {
        Assert.notNull(userinfo.getName(), "ACCOUNT");
        Assert.notNull(userinfo.getPassword(), "PASSWORD");
        super.update(userinfo);
        String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);

        if (LoginHelper.login(new Login(userinfo.getName(), userinfo.getPassword(), false), clientIp)) {
            return setSuccessModelMap(modelMap);
        }
        throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
    }

    /**
     * 没有登录
     *
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unauthorized", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public Object unauthorized(ModelMap modelMap) throws Exception {
        return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
    }

    /**
     * 没有权限
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/forbidden", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public Object forbidden(ModelMap modelMap) {
        return setModelMap(modelMap, HttpCode.FORBIDDEN);
    }
}
