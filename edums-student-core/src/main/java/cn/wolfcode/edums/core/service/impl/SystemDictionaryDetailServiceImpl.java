package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.domain.SystemDictionaryDetail;
import cn.wolfcode.edums.core.mapper.SystemdictionarydetailMapper;
import cn.wolfcode.edums.core.service.ISystemDictionaryDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenhui
 * @since 2019-12-31
 */
@Service
public class SystemDictionaryDetailServiceImpl extends ServiceImpl<SystemdictionarydetailMapper, SystemDictionaryDetail> implements ISystemDictionaryDetailService {

    @Autowired
    private SystemdictionarydetailMapper mapper;

    @Override
    public List<SystemDictionaryDetail> listByParentSn(String sn) {
        return mapper.selectByParentSn(sn);
    }
}
