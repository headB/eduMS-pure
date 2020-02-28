package cn.wolfcode.edums.core.mapper;

import cn.wolfcode.edums.core.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
public interface RoleMapper extends BaseMapper<Role> {

    Set<String> selectRoleSnListByUserId(Long id);
}
