package cn.wolfcode.edums.core.mapper;

import cn.wolfcode.edums.core.domain.SystemDictionaryDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenhui
 * @since 2019-12-31
 */
public interface SystemdictionarydetailMapper extends BaseMapper<SystemDictionaryDetail> {

    List<SystemDictionaryDetail> selectByParentSn(@Param("sn") String sn);
}
