package cn.wolfcode.edums.core.service;

import cn.wolfcode.edums.core.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
public interface IRoleService extends IService<Role> {

    Set<String> selectRoleSnListByUserId(Long id);
}
