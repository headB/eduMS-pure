package cn.wolfcode.edums.teaching.enums;

/**
 * @author Leon
 * @date 2020-01-31
 */
public enum StudentSourceEnum {
    /** 老学员推荐 */
    OLD_STUDENT_RECOMMEND(32768L, "老学员推荐");

    private Long id;
    private String name;

    StudentSourceEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }}
