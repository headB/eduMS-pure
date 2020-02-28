package cn.wolfcode.edums.core.mapper;

import cn.wolfcode.edums.core.domain.SystemMenu;
import cn.wolfcode.edums.core.qo.QueryObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Leon
 * @since 2019-12-24
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    @Override
    <E extends IPage<SystemMenu>> List<SystemMenu> pageList(E page, @Param("qo") QueryObject qo);

    List<SystemMenu> selectMenusByUserId(@Param("userId") Long userId, @Param("type") String type);
}
