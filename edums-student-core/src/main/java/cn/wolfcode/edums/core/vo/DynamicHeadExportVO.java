package cn.wolfcode.edums.core.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @author Leon
 * @date 2020-02-04
 */
@Getter
@Setter
public class DynamicHeadExportVO extends ExportVO {

    private List<List<String>> headList;

    public DynamicHeadExportVO(List<List<String>> headList, List<?> dataList) {
        this(defFileName(), null, headList, dataList, null, null);
    }

    public DynamicHeadExportVO(String fileName, List<List<String>> headList, List<?> dataList) {
        this(fileName, null, headList, dataList, null, null);
    }

    public DynamicHeadExportVO(String fileName, Class<?> clazz, List<List<String>> headList, List<?> dataList, Set<String> includeFiledNames, Set<String> excludeFiledNames) {
        super(fileName, clazz, dataList, includeFiledNames, excludeFiledNames);
        this.headList = headList;
    }
}
