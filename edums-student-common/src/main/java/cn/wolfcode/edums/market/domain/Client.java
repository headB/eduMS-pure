package cn.wolfcode.edums.market.domain;

import cn.wolfcode.edums.core.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_client")
public class Client extends BaseDomain {

    private static final long serialVersionUID = 1L;

    public static final Integer STATUS_NORMAL = 1;
    public static final Integer STATUS_STUDENT = 2;
    public static final Integer STATUS_LOST = 3;
    /**
     * 黑名单
     */
    public static final Integer STATUS_BLACKLIST = -1;
    /**
     * 无法联系
     */
    public static final Integer STATUS_NOCONTACT = 5;
    /**
     * 暂不关注
     */
    public static final Integer STATUS_NOFOCUS = 6;

    public static final Integer EXIST_NORMAL = 0;
    public static final Integer EXIST_POOL = 1;

    /**
     * 咨询时间
     */
    @TableField("vdate")
    private LocalDateTime vdate;

    /**
     * 最后一次的跟进时间 如果2个月没有跟进? 则需要接触保护
     */
    @TableField("lastcontacttime")
    private LocalDateTime lastContactTime;

    /**
     * 真实姓名
     */
    @TableField("truename")
    private String trueName;

    @TableField("qq")
    private String qq;

    @TableField("tel")
    private String tel;

    @TableField("age")
    private Integer age;

    @TableField("sex")
    private String sex;

    @TableField("email")
    private String email;

    @TableField("address")
    private String address;

    /**
     * 学校
     */
    @TableField("school")
    private String school;

    /**
     * 最高学历
     */
    @TableField("edu")
    private String edu;

    /**
     * 工作年限
     */
    @TableField("workyear")
    private Double workYear;

    /**
     * 推荐人名称，当为外部推荐人的时候使用
     */
    @TableField("introducername")
    private String introducerName;

    /**
     * 用户关注的焦点，用户关心的主要问题
     */
    @TableField("focus")
    private String focus;

    @TableField("remark")
    private String remark;

    /**
     * 状态, 1 正常,2 已正式学员,3已丢单,-1黑名单,5无法联系,6暂不跟进
     */
    @TableField("status")
    private Integer status;

    /**
     * 丢单时间
     */
    @TableField("lostdate")
    private LocalDateTime lostDate;

    /**
     * 丢单原因
     */
    @TableField("lostreason")
    private String lostReason;

    /**
     * 丢单备注
     */
    @TableField("lostinfo")
    private String lostInfo;

    /**
     * 电话交流数
     */
    @TableField("telnum")
    private Integer telNum;

    /**
     * 在线交流数
     */
    @TableField("imnum")
    private Integer imNum;

    /**
     * 学员上门拜访次数
     */
    @TableField("visitnum")
    private Integer visitNum;

    /**
     * 意向班级
     */
    @TableField("intentclass_id")
    private Long intentClassId;

    /**
     * 省份
     */
    @TableField("province_id")
    private Long provinceId;

    /**
     * 意向登记
     */
    @TableField("intentlevel_id")
    private Long intentLevelId;

    /**
     * 咨询人
     */
    @TableField("seller_id")
    private Long sellerId;

    /**
     * 来源
     */
    @TableField("source_id")
    private Long sourceId;

    /**
     * 推荐人，当为内部推荐人，比如说员工或者学生
     */
    @TableField("introducer_id")
    private Long introducerId;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 客户类型:1,线下客户，2,线上客户，3，老学员介绍
     */
    @TableField("types")
    private Integer types;

    /**
     * 是否零付款(或嘿牛)学员
     */
    @TableField("applyzero")
    private Boolean applyZero;

    /**
     * 申请进度,0为还未开始办理手续,1为填写自我介绍,2为通过基础测试,3为通过入学测试,4为办理完入学手续并转为正式学员
     */
    @TableField("applyprogress")
    private Integer applyProgress;

    /**
     * 录入时间
     */
    @TableField("inputdate")
    private LocalDateTime inputDate;

    /**
     * 学校所属学校,可以取代之前的school属性
     */
    @TableField("sc_id")
    private Long scId;

    /**
     * 录入人
     */
    @TableField("inputuser_id")
    private Long inputUserId;

    /**
     * 其他咨询方式交流次数
     */
    @TableField("othernum")
    private Integer otherNum;

    /**
     * 下次客户跟踪访问的时间:每添加一次跟进信息时，自动根据ClientVisit中的访
     */
    @TableField("nextvisitdate")
    private LocalDateTime nextVisitDate;

    /**
     * 最后一次修改数据时间
     */
    @TableField("lastupdatetime")
    private LocalDateTime lastUpdateTime;

    /**
     * 是否携带笔记本电脑
     */
    @TableField("haslaptop")
    private Boolean hasLaptop;

    /**
     * 大学入学时间
     */
    @TableField("startschool")
    private LocalDateTime startSchool;

    /**
     * 大学班级
     */
    @TableField("collegeclass")
    private String collegeclass;

    @TableField("weixinid")
    private String weiXinId;

    /**
     * 约定上门咨询日期
     */
    @TableField("ordercomedate")
    private LocalDateTime orderComeDate;

    /**
     * 0正常状态  1存在客户池中
     */
    @TableField("existence")
    private Integer existence = EXIST_NORMAL;

    @TableField("topooltime")
    private LocalDateTime toPoolTime;

    @TableField("frompooltime")
    private LocalDateTime frompooltime;

    @TableField("weixinid2")
    private String weixinid2;

    /**
     * 客户现状:关联到名为ClientCurrentStatus的数据字典
     */
    @TableField("currentstatus_id")
    private Long currentstatusId;

    /**
     * 考试编号
     */
    @TableField("examnumber")
    private String examnumber;

    /**
     * 意向学科
     */
    @TableField("subject_id")
    private Long subjectId;

    /**
     * 微信号
     */
    @TableField("weixinnumber")
    private String weixinnumber;

    /**
     * 意向校区
     */
    @TableField("campus_id")
    private Long campusId;


}
