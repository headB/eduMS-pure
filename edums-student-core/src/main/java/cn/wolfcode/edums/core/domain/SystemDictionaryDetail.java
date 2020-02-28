package cn.wolfcode.edums.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenhui
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_systemdictionarydetail")
public class SystemDictionaryDetail extends BaseDomain {

    private static final long serialVersionUID = 1L;

    private String title;

    private String tvalue;

    private Integer sequence;

    private String intro;

    @TableField("parent_id")
    private Long parentId;

    /**
     * 数据字典详情常用数据
     */
    public enum Enums {
        /** 高校 */
        SCHOOL("高校", 32770L),
        /** 流失类型：开除 */
        FIRED("开除", 458752L),
        /** 流失阶段：报名流失 */
        SIGNUP("报名流失", 360448L);

        /** TODO 后期应该增加 sn */
        private Long id;
        private String name;

        Enums(String name, Long id) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
