package cn.wolfcode.edums.core.service;

import cn.wolfcode.edums.core.domain.SystemMenu;
import cn.wolfcode.edums.core.vo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Leon
 * @since 2019-12-24
 */
public interface ISystemMenuService extends IService<SystemMenu> {

    /**
     * 根据当前用户获取菜单列表
     *
     * @return
     */
    List<Menu> queryMenuListByCurUser();
}
