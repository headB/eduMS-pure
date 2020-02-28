package cn.wolfcode.edums.core.service;

import cn.wolfcode.edums.core.domain.Permission;
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
public interface IPermissionService extends IService<Permission> {

    Set<String> selectPermsSnListByUserId(Long id);

    Set<String> selectAllSnList();
}
