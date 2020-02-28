package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.domain.Role;
import cn.wolfcode.edums.core.mapper.RoleMapper;
import cn.wolfcode.edums.core.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<String> selectRoleSnListByUserId(Long id) {
        Set<String> set = roleMapper.selectRoleSnListByUserId(id);
        Set<String> ret = new HashSet<>(set.size());
        if (set.size() > 0) {
            set.forEach(s -> {
                String[] split = s.split("@");
                if (split.length > 0) {
                    ret.addAll(Arrays.stream(split).collect(Collectors.toSet()));
                }
            });
        }
        return ret;
    }
}
