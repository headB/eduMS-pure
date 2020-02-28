package cn.wolfcode.edums.core.domain;

import cn.wolfcode.edums.core.Constants;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Leon
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_employee")
public class Employee extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 员工类型：离职
     */
    public static final Integer EMPLOYEE_TYPE_LEAVE = 3;

    /**
     * 拼音
     */
    @TableField("namepy")
    private String namePy;
    /**
     * 真实名称
     */
    @TableField("truename")
    private String trueName;
    /**
     * 性别：男|女
     */
    private String sex;
    /**
     * 备注
     */
    private String remark;
    /**
     * 职称
     */
    private String duty;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 生日
     */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @DateTimeFormat(pattern = Constants.DEFAULT_DATE_PARTTERN)
    @TableField("birthday")
    private LocalDate birthDay;

    /**
     * 0普通员工,1咨询人员，课程顾问，2市场人员，3教师，4管理人员. 5班主任
     */
    private Integer types;
    /**
     * 简介
     */
    private String intro;

    /**
     * 所属部门
     */
    @TableField("dept_id")
    private Long deptId;

    @TableField("workeronly")
    private Boolean workerOnly;

    /**
     * 加入时间
     */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("entertime")
    private LocalDate enterTime;

    /**
     * qq号
     */
    private String qq;

    /**
     * 最后编辑时间
     */
    @TableField("lastmodifiedtime")
    private Long lastModifiedTime;

    /**
     * 微信 id
     */
    @TableField("weixinid")
    private String weixinId;

    /**
     * 最大客户数量：旧数据
     */
    @TableField("maxclientnum")
    private Integer maxClientNum;

    /**
     * 身份证
     */
    @TableField("idcard")
    private String idCard;

    @TableField("hkadr")
    private String hkAdr;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("endtime")
    private LocalDateTime endTime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("positivetime")
    private LocalDateTime positiveTime;

    /**
     * 离职时间
     */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("leavetime")
    private LocalDateTime leaveTime;

    /**
     * 学校
     */
    private String school;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历
     */
    private String education;

    /**
     * 主修专业
     */
    private String major;

    /**
     * 紧急联系人
     */
    @TableField("soslinkman")
    private String sosLinkMan;

    /**
     * 紧急联系方式
     */
    @TableField("soslinktel")
    private String sosLinkTel;

    @TableField("recordid")
    private String recordId;

    /**
     * 试用薪资
     */
    @TableField("probationsalary")
    private Double probationSalary;

    /**
     * 录用薪资
     */
    @TableField("officialsalary")
    private Double officialSalary;

    /**
     * 社保户口性质
     */
    @TableField("socialsecuritynature")
    private String socialSecurityNature;

    /**
     * 社保办理时间
     */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_TIME_PARTTERN, timezone = Constants.DEFAULT_TIMEZONE)
    @TableField("socialsecuritydate")
    private LocalDateTime socialSecurityDate;

    /**
     * 原公司
     */
    @TableField("oldcompany")
    private String oldCompany;

    /**
     * 公积金购买日期
     */
    @TableField("reservedate")
    private LocalDate reserveDate;


}
