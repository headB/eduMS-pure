package cn.wolfcode.edums.core.qo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Leon
 * @date 2019-12-27
 */
@Getter
@Setter
public class QueryObject implements Serializable {

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    private String keyword;
    private Long current = 1L;
    private Long size = 30L;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则
     */
    private String order = ASC;


    public String getKeyword() {
        return empty2null(keyword);
    }

    protected String empty2null(String str) {
        return StringUtils.trimToNull(str);
    }
}
