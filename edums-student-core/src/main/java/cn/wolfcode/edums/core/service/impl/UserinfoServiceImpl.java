package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.mapper.UserinfoMapper;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.util.AssertUtil;
import cn.wolfcode.edums.core.util.InstanceUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Userinfo entity) {
        // 密码加密
        entity.setPassword(new Md5Hash(entity.getPassword()).toHex().toUpperCase());
        return super.save(entity);
    }

    @Override
    public Userinfo selectByName(String name) {
        Map<String, Object> params = InstanceUtil.newHashMap();
        params.put("status", 0);
        params.put("name", name);
        return toSingle(listByMap(params));
    }

    @Override
    public void leave(ArrayList<Long> ids) {
        AssertUtil.notEmpty(ids, "操作失败，参数异常");
        // 将用户状态设置为停用
        List<Userinfo> userinfoList = ids.stream().map(id -> {
            Userinfo user = new Userinfo();
            user.setId(id);
            user.setStatus(Userinfo.STATUS_DISABLED);
            return user;
        }).collect(Collectors.toList());

        super.updateBatchById(userinfoList);
    }

    @Override
    public void restore(Long id) {
        Userinfo userinfo = new Userinfo();
        userinfo.setId(id);
        userinfo.setStatus(Userinfo.STATUS_NORMAL);
        super.updateById(userinfo);
    }

    @Override
    public void resetPwd(ArrayList<Long> ids) {
        AssertUtil.notEmpty(ids, "操作失败，参数异常");
        String password = new Md5Hash(Constants.DEFAULT_LOGIN_PASSWORD).toHex().toUpperCase();

        List<Userinfo> userinfoList = ids.stream().map(id -> {
            Userinfo u = new Userinfo();
            u.setId(id);
            u.setPassword(password);
            return u;
        }).collect(Collectors.toList());

        super.updateBatchById(userinfoList);
    }

    private Userinfo toSingle(List<Userinfo> userinfos) {
        return userinfos.size() > 0 ? userinfos.get(0) : null;
    }
}
