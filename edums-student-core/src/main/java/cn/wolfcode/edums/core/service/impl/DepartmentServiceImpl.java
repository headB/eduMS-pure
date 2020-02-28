package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.domain.Department;
import cn.wolfcode.edums.core.mapper.DepartmentMapper;
import cn.wolfcode.edums.core.service.IDepartmentService;
import cn.wolfcode.edums.core.util.InstanceUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-25
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public boolean save(Department entity) {
        setDirpath(entity);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Department entity) {
        setDirpath(entity);
        return super.updateById(entity);
    }

    private void setDirpath(Department entity) {
        if (entity.getParentId() != null) {
            List<String> snList = InstanceUtil.newArrayList();
            // 递归得到当前及其所有父部门的 sn
            getSnList(snList, entity);
            // 翻转列表
            Collections.reverse(snList);
            String dirpath = StringUtils.join(snList.iterator(), "@");
            entity.setDirPath(dirpath + "@");
        }
    }

    private void getSnList(List<String> snList, Department entity) {
        if (entity != null) {
            snList.add(entity.getSn());
            if (entity.getParentId() != null) {
                entity = baseMapper.selectById(entity.getParentId());
                getSnList(snList, entity);
            }
        }
    }
}
