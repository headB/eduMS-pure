package cn.wolfcode.edums.core.service.impl;

import cn.wolfcode.edums.core.Constants;
import cn.wolfcode.edums.core.domain.Employee;
import cn.wolfcode.edums.core.domain.Userinfo;
import cn.wolfcode.edums.core.mapper.EmployeeMapper;
import cn.wolfcode.edums.core.service.IEmployeeService;
import cn.wolfcode.edums.core.service.IUserinfoService;
import cn.wolfcode.edums.core.util.AssertUtil;
import cn.wolfcode.edums.core.util.PinyinUtil;
import cn.wolfcode.edums.core.vo.EmployeeVO;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Leon
 * @since 2019-12-25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private IUserinfoService userinfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Employee entity) {
        // 设置初始化信息
        LocalDateTime now = LocalDateTime.now();
        entity.setEnterTime(now.toLocalDate());
        entity.setWorkerOnly(false);
        AssertUtil.notEmpty(entity.getTrueName(), "员工姓名不能为空");
        entity.setNamePy(PinyinUtil.getPinYinHeadUperChar(entity.getTrueName()));

        boolean save = super.save(entity);
        AssertUtil.isTrue(save, "新增员工失败");

        // 创建
        Userinfo userinfo = buildUserinfo(entity, now);
        return userinfoService.save(userinfo);
    }

    @Override
    public boolean updateById(Employee entity) {
        entity.setLastModifiedTime(Instant.now().toEpochMilli());
        if (entity instanceof EmployeeVO) {
            EmployeeVO vo = (EmployeeVO) entity;
            if (StringUtils.isNotBlank(vo.getEmail()) || StringUtils.isNotBlank(vo.getTrueName())) {
                Userinfo userinfo = new Userinfo();
                userinfo.setName(PinyinUtil.getPinYin(vo.getTrueName()));
                userinfo.setId(entity.getId());
                userinfo.setEmail(vo.getEmail());
                userinfoService.updateById(userinfo);
            }
        }
        return super.updateById(entity);
    }

    private Userinfo buildUserinfo(Employee entity, LocalDateTime now) {
        AssertUtil.isTrue(entity instanceof EmployeeVO, "新增员工失败，参数类型错误");
        EmployeeVO vo = (EmployeeVO) entity;

        Userinfo userinfo = new Userinfo();
        userinfo.setId(entity.getId());
        userinfo.setName(PinyinUtil.getPinYin(entity.getTrueName()));
        userinfo.setEmail(vo.getEmail());
        userinfo.setPassword(Constants.DEFAULT_LOGIN_PASSWORD);
        userinfo.setRegisterTime(now);
        userinfo.setStatus(Userinfo.STATUS_NORMAL);
        userinfo.setLoginTimes(0);
        userinfo.setEnableChangePassword(true);
        userinfo.setPasswordExpiredDays(Userinfo.DEFAULT_PASSWORD_EXPIRED_DAYS);
        userinfo.setLastLogoutTime(0);
        userinfo.setUseUsb(false);
        return userinfo;
    }
}
