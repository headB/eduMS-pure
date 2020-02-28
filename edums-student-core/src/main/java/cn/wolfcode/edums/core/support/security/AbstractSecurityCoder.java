package cn.wolfcode.edums.core.support.security;

import java.security.MessageDigest;
import java.security.Security;

/**
 * @author Leon
 */
public abstract class AbstractSecurityCoder {
    private static Byte ADDFLAG = 0;
    static {
        if (ADDFLAG == 0) {
            // 加入BouncyCastleProvider支持
            Security.addProvider(new BouncyCastleProvider());
            ADDFLAG = 1;
        }
    }

    public static byte[] digest(String algorithm, String data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // 执行消息摘要
        return md.digest(data.getBytes("UTF-8"));
    }
}
