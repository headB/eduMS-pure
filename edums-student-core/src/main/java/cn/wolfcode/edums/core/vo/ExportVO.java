package cn.wolfcode.edums.core.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author Leon
 * @date 2020-02-04
 */
@Getter
@Setter
public class ExportVO {

    public static final String DEFAULT_SUFFIX_XLS = ".xls";
    public static final String DEFAULT_SUFFIX_XLSX = ".xlsx";

    private String fileName;
    private Class<?> clazz;
    private List<?> dataList;
    private Set<String> includeFiledNames;
    private Set<String> excludeFiledNames;

    public ExportVO(List<?> dataList) {
        this(defFileName(), dataList);
    }

    static String defFileName() {
        return Instant.now().toEpochMilli() + DEFAULT_SUFFIX_XLS;
    }

    public ExportVO(String fileName, List<?> dataList) {
        this(fileName, null, dataList, null, null);
    }

    public ExportVO(String fileName, Class<?> clazz, List<?> dataList) {
        this(fileName, clazz, dataList, null, null);
    }

    public ExportVO(String fileName, Class<?> clazz, List<?> dataList, Set<String> includeFiledNames) {
        this(fileName, clazz, dataList, includeFiledNames, null);
    }

    public ExportVO(String fileName, Class<?> clazz, List<?> dataList, Set<String> includeFiledNames, Set<String> excludeFiledNames) {
        this.fileName = fileName;
        this.clazz = clazz;
        this.dataList = dataList;
        this.includeFiledNames = includeFiledNames;
        this.excludeFiledNames = excludeFiledNames;
    }
}
