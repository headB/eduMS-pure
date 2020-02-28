package cn.wolfcode.edums.core.domain;

import cn.wolfcode.edums.core.Constants;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Leon
 * @since 2019-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_userinfo")
public class Userinfo extends BaseDomain {

    /**
     * 默认状态
     */
    public static final Integer STATUS_NORMAL = 0;
    public static final Integer STATUS_DISABLED = -1;
    /**
     * 默认密码过期天数
     */
    public static final Integer DEFAULT_PASSWORD_EXPIRED_DAYS = -1;
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Long id;
    @JsonAlias("username")
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    @TableField("registertime")
    private LocalDateTime registerTime;
    private Integer status;
    @TableField("logintimes")
    private Integer loginTimes;
    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("lastlogintime")
    private LocalDateTime lastLoginTime;
    @TableField("lastloginip")
    private String lastLoginIp;
    private String problem;
    private String solution;
    @TableField("imuin")
    private Long imUin;
    @TableField("lastlogouttime")
    private Integer lastLogoutTime;
    @TableField("enablechangepassword")
    private Boolean enableChangePassword;
    @TableField("passwordexpireddays")
    private Integer passwordExpiredDays;
    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("passwordchangetime")
    private LocalDateTime passwordChangeTime;
    @TableField("loginatsametime")
    private Boolean loginAtSameTime;
    @TableField("useusb")
    private Boolean useUsb;

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
