package cn.wolfcode.edums.core.controller;


import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.support.http.SessionUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/users")
public class UserinfoController extends BaseController<Userinfo, IUserinfoService> {

    @GetMapping("/session")
    public Object session() {
        SessionUser user = (SessionUser) SecurityUtils.getSubject().getPrincipal();
        return setSuccessModelMap(user);
    }
}
