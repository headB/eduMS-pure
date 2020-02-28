package cn.wolfcode.edums.teaching.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author Leon
 * @date 2020-01-31
 */
public enum StudentEduEnum {
    /** 高中 */
    HIGH_SCHOOL("高中"),
    /** 大专 */
    COLLEGE("大专"),
    /** 本科 */
    BACHELOR_DEGREE("本科"),
    /** 硕士 */
    MASTER("硕士"),
    /** 未知学历 */
    UNKNOWN("未知学历");

    private String name;

    StudentEduEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static StudentEduEnum transfer(String name) {
        if (StringUtils.isBlank(name)) {
            return UNKNOWN;
        }

        for (StudentEduEnum value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }

        return UNKNOWN;
    }
}
