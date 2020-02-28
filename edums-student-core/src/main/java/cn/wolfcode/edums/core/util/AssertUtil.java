package cn.wolfcode.edums.core.util;

import cn.wolfcode.edums.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author Leon
 * @date 2020-01-07
 */
public class AssertUtil {

    /**
     * 字符串对象不能为空
     *
     * @param str
     * @param message
     */
    public static void notEmpty(String str, String message) {
        isTrue(StringUtils.isNotEmpty(str), message);
    }

    /**
     * 集合对象不能为空
     *
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection collection, String message) {
        isTrue(!CollectionUtils.isEmpty(collection), message);
    }

    /**
     * 表达式为 true
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }
}
