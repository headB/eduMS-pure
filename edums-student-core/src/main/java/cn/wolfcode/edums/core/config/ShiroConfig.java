package cn.wolfcode.edums.core.config;

import cn.wolfcode.edums.core.filter.AnyRolesAuthorizationFilter;
import cn.wolfcode.edums.core.filter.JwtAuthFilter;
import cn.wolfcode.edums.core.shiro.DbShiroRealm;
import cn.wolfcode.edums.core.shiro.JWTShiroRealm;
import cn.wolfcode.edums.core.support.context.OrderedProperties;
import cn.wolfcode.edums.core.util.InstanceUtil;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 权限拦截配置
 * @author hox
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(EhCacheManager ehcacheManager, Authorizer authorizer, Authenticator authenticator) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(ehcacheManager);
        manager.setSessionManager(new DefaultSessionManager());
        manager.setAuthenticator(authenticator);
        manager.setAuthorizer(authorizer);
        return manager;
    }

    @Bean
    public EhCacheManager ehcacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath*:ehcache.xml");
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter) Objects.requireNonNull(shiroFilter(securityManager).getObject()));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public Authenticator authenticator(EhCacheManager ehCacheManager) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(ehCacheManager), dbShiroRealm(ehCacheManager)));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    public Authorizer authorizer(EhCacheManager ehCacheManager) {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(Collections.singletonList(dbShiroRealm(ehCacheManager)));
        return authorizer;
    }

    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean("dbRealm")
    public Realm dbShiroRealm(EhCacheManager ehcacheManager) {
        return new DbShiroRealm(ehcacheManager);
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(EhCacheManager ehcacheManager) {
        return new JWTShiroRealm(ehcacheManager);
    }

// TODO 暂时去除权限检测
//    @Bean
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) throws IOException {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        factory.setLoginUrl("/unauthorized");
        factory.setUnauthorizedUrl("/forbidden");
        Map<String, String> filterMap = InstanceUtil.newLinkedHashMap();
        OrderedProperties properties = new OrderedProperties("classpath:config/shiro.properties");
        filterMap.putAll(Maps.fromProperties(properties));
        factory.setFilterChainDefinitionMap(filterMap);

        Map<String, Filter> filters = factory.getFilters();
        filters.put("authcToken", createAuthFilter());
        filters.put("anyRole", createRolesFilter());
        return factory;
    }

    protected JwtAuthFilter createAuthFilter() {
        return new JwtAuthFilter();
    }

    protected AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

}