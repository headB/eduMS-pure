package cn.wolfcode.edums.core.support.security.coder;

import cn.wolfcode.edums.core.support.security.AbstractSecurityCoder;

/**
 * MD加密组件
 * @author Leon
 */
public abstract class AbstractMDCoder extends AbstractSecurityCoder {

    /**
     * MD2加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD2(String data) throws Exception {
        return digest("MD2", data);
    }

    /**
     * MD4加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD4(String data) throws Exception {
        return digest("MD4", data);
    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {
        return digest("MD5", data);
    }

    /**
     * Tiger加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeTiger(String data) throws Exception {
        return digest("Tiger", data);
    }

    /**
     * Whirlpool加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeWhirlpool(String data) throws Exception {
        return digest("Whirlpool", data);
    }

    /**
     * GOST3411加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeGOST3411(String data) throws Exception {
        return digest("GOST3411", data);
    }
}
