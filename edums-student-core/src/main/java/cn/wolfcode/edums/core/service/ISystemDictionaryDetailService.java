package cn.wolfcode.edums.core.service;

import cn.wolfcode.edums.core.domain.SystemDictionaryDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenhui
 * @since 2019-12-31
 */
public interface ISystemDictionaryDetailService extends IService<SystemDictionaryDetail> {

    /**
     * 根据目录编码查询字典明细
     * @param sn
     * @return
     */
    List<SystemDictionaryDetail> listByParentSn(String sn);
}
