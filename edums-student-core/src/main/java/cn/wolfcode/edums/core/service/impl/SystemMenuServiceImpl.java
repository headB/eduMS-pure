package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.SystemMenu;
import cn.wolfcode.edums.core.mapper.SystemMenuMapper;
import cn.wolfcode.edums.core.service.ISystemMenuService;
import cn.wolfcode.edums.core.support.http.SessionUser;
import cn.wolfcode.edums.core.util.InstanceUtil;
import cn.wolfcode.edums.core.vo.Menu;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-24
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    @Cacheable("menu")
    public List<Menu> queryMenuListByCurUser() {
        Subject subject = SecurityUtils.getSubject();
        SessionUser user = (SessionUser) subject.getPrincipal();

        List<SystemMenu> systemMenus = null;

        // 如果是超级管理员直接查询所有
//        if (Constants.ADMIN.equals(user.getUsername())) {
        /** TODO 暂时不设权限，后续版本完善 */
            systemMenus = this.list(new QueryWrapper<SystemMenu>().eq("types", Constants.EDUMS_SYSTEMMENU_TYPE));
//        } else {
//            systemmenus = systemMenuMapper.selectMenusByUserId(user.getId(), Constants.EDUMS_SYSTEMMENU_TYPE);
//        }

        // 所有菜单转换
        Map<Long, Menu> menuMap = systemMenus.stream().map(m ->
                new Menu(m.getId(), m.getParentId(), m.getTitle(), m.getSn(), m.getUrl(), m.getIcon(), m.getSequence(), null)
        ).collect(Collectors.toMap(Menu::getId, v -> v));

        // 构造返回数据列表
        List<Menu> ret = InstanceUtil.newArrayList();

        // 建立层级结构
        menuMap.forEach((key, tmp) -> {
            Menu parent = menuMap.get(tmp.getParentId());
            if (parent != null) {
                List<Menu> list = parent.getList() == null ? InstanceUtil.newArrayList() : parent.getList();
                list.add(tmp);
                parent.setList(list);
            }

            if (tmp.getParentId() == null) {
                ret.add(tmp);
            }
        });

        // 按照 sequence 排序
        shorted(ret);
        return ret;
    }

    private void shorted(List<Menu> ret) {
        ret.sort(Comparator.comparingInt(Menu::getSequence));

        // 子元素排序
        for (Menu menu : ret) {
            List<Menu> list = menu.getList();
            if (list != null) {
                shorted(list);
            }
        }
    }
}
