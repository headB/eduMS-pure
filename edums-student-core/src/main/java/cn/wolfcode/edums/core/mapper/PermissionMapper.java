package cn.wolfcode.edums.core.mapper;

import cn.wolfcode.edums.core.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户 id 查询权限列表
     *
     * @param id
     * @param status
     * @return
     */
    Set<String> selectPermsSnListByUserId(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有权限列表
     *
     * @param status
     * @return
     */
    Set<String> selectAllSnList(Integer status);
}
