package cn.wolfcode.edums.core.filter;

import cn.wolfcode.edums.core.shiro.jwt.JWTToken;
import cn.wolfcode.edums.core.support.http.HttpCode;
import cn.wolfcode.edums.core.support.http.SessionUser;
import cn.wolfcode.edums.core.util.JwtUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Leon
 */
public class JwtAuthFilter extends AuthenticatingFilter {

    private static final Logger log = LogManager.getLogger();
    private static final int TOKEN_REFRESH_INTERVAL = 300;

    public JwtAuthFilter() {
        this.setLoginUrl("/unauthorized");
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //对于OPTION请求做拦截，不做token校验
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return false;
        }

        return super.preHandle(request, response);
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
        request.setAttribute("jwtShiroFilter.FILTERED", true);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        Boolean afterFiltered = (Boolean) (request.getAttribute("jwtShiroFilter.FILTERED"));
        if (BooleanUtils.isTrue(afterFiltered)) {
            return true;
        }

        boolean allowed = false;
        String uri = "[unknown uri]";
        try {
            if (request instanceof ShiroHttpServletRequest) {
                ShiroHttpServletRequest req = (ShiroHttpServletRequest) request;
                uri = req.getRequestURI();
            }
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) { // not found any token
            log.error("Request: {}, Not found any token", uri);
        } catch (Exception e) {
            log.error("Request: {}, Error occurs when login", e, uri);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getToken(servletRequest);
        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)) {
            return new JWTToken(jwtToken);
        }

        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.OK.value());

        JSONObject ret = new JSONObject();
        ret.put("code", HttpCode.UNAUTHORIZED.value());
        ret.put("msg", HttpCode.UNAUTHORIZED.msg());
        ret.put("timestamp", System.currentTimeMillis());

        httpResponse.getWriter().write(ret.toJSONString());
        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            SessionUser user = (SessionUser) subject.getPrincipal();
            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
            if (shouldRefresh) {
                newToken = JwtUtils.generateJwtToken(user.getUsername());
            }
        }
        if (StringUtils.isNotBlank(newToken)) {
            httpResponse.setHeader(JwtUtils.TOKEN_IN_HEADER, newToken);
        }

        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    protected String getToken(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String token = httpRequest.getHeader(JwtUtils.TOKEN_IN_HEADER);
        if (token == null) {
            token = httpRequest.getParameter(JwtUtils.TOKEN_IN_HEADER);
        }
        return token;
    }

    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
