package cn.wolfcode.edums.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leon
 * @date 2020-01-07
 */
public final class PinyinUtil {
    private PinyinUtil() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return String
     */
    public static String getPinYin(String src) {
        if (src == null) {
            return "";
        }
        char[] t1 = src.toCharArray();
        String[] t2;
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = getPinyinFormat();
        StringBuilder t4 = new StringBuilder();
        try {
            for (char c : t1) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    t4.append(t2[0]);
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t4.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("", e);
        }
        return t4.toString();
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return String
     */
    public static String getCamelPinYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2;
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = getPinyinFormat();
        StringBuilder t4 = new StringBuilder();
        String t;
        try {
            for (char c : t1) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    t = t2[0];
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t = Character.toString(c);
                }
                t = t.substring(0, 1).toUpperCase() + t.substring(1);
                t4.append(t);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("", e);
        }
        return t4.toString();
    }

    private static HanyuPinyinOutputFormat getPinyinFormat() {
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        return t3;
    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /**
     * 提取每个汉字的大写首字母
     *
     * @param str
     * @return String
     */
    public static String getPinYinHeadUperChar(String str) {
        return getPinYinHeadChar(str).toUpperCase();
    }

    public static void main(String[] args) {
        String cnStr = "中华人民共和国";
        System.out.println(getPinYin(cnStr));
        System.out.println(getCamelPinYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        System.out.println(getPinYinHeadUperChar(cnStr));
    }
}

