package cn.wolfcode.edums.teaching.enums;

import lombok.Getter;

@Getter
public enum  StudentJobTypeEnum {

    SOCIOLOGY(0, "社招"),
    SCHOOL(1, "校招"),
    COMPANY(2, "创业"),
    RECOMMEND(3, "推荐");

    private  Integer key;
    private  String value;


    StudentJobTypeEnum(Integer key,String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据key获取value
     */
    public static String getValueByKey(Integer key) {
        StudentJobTypeEnum[] enums = StudentJobTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getKey().equals(key)) {
                return enums[i].getValue();
            }
        }
        return null;
    }
    /**
     * 根据key获取value
     */
    public static Integer getKeyByValue(String value) {
        StudentJobTypeEnum[] enums = StudentJobTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getValue().equals(value)) {
                return enums[i].getKey();
            }
        }
        return null;
    }


}
