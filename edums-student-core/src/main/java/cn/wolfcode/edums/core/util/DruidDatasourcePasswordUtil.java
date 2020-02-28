package cn.wolfcode.edums.core.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author Leon
 * @date 2020-01-04
 */
public class DruidDatasourcePasswordUtil {

    /**
     * 数据库密码加密
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static String encrypt(String source) throws Exception {
        return ConfigTools.encrypt(source);
    }

    /**
     * 数据库密码解密
     *
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String decrypt(String publicKey, String encrypt) throws Exception {
        return ConfigTools.decrypt(publicKey, encrypt);
    }

    private static void init(String password) throws Exception {
        String[] keyPair = ConfigTools.genKeyPair(512);
        System.out.println("private key:" + keyPair[0]);
        System.out.println("public key:" + keyPair[1]);
        System.out.println("encrypt password:" + ConfigTools.encrypt(keyPair[0], password));
    }

    public static void main(String[] args) throws Exception {
        init("abcd1234");
    }
}
