package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.Permission;
import cn.wolfcode.edums.core.mapper.PermissionMapper;
import cn.wolfcode.edums.core.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<String> selectPermsSnListByUserId(Long id) {
        return permissionMapper.selectPermsSnListByUserId(id, Constants.EDUMS_PERMISSION_STATUS);
    }

    @Override
    public Set<String> selectAllSnList() {
        return permissionMapper.selectAllSnList(Constants.EDUMS_PERMISSION_STATUS);
    }
}
