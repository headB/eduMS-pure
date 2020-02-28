package cn.wolfcode.edums.core.service;

import cn.wolfcode.edums.core.domain.Userinfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
public interface IUserinfoService extends IService<Userinfo> {

    Userinfo selectByName(String name);

    /**
     * 停用员工账号
     *
     * @param ids
     */
    void leave(ArrayList<Long> ids);

    /**
     * 恢复员工账号
     *
     * @param id
     */
    void restore(Long id);

    /**
     * 重置员工密码
     *
     * @param ids
     */
    void resetPwd(ArrayList<Long> ids);
}
