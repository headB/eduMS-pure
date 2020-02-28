package cn.wolfcode.edums.teaching.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author chenhui
 * @date 2020-02-14
 */
public enum StudentJobStatusEnum {

    LEAVE("离职"),

    STAY("在职");

    private String name;

    StudentJobStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static StudentJobStatusEnum transfer(String name) {
        for (StudentJobStatusEnum value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
