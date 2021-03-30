package xyz.visonforcoding.carambola.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Security {

    /**
     * md5加密
     *
     * @param plainText
     * @return
     */
    public static String md5(String plainText) {
        return DigestUtils.md5Hex(plainText);
    }

    /**
     * 加盐的md5加密
     *
     * @param plainText
     * @param salt
     * @return
     */
    public static String encrypt(String plainText, String salt) {
        return Security.md5(plainText + salt);
    }

    /**
     * 验证密码
     *
     * @param pwd 数据库中的密码
     * @param inputPwd 输入的密码
     * @param salt 加盐
     * @return
     */
    public static Boolean verifyPassword(String pwd, String inputPwd, String salt) {

        if (pwd.equals(Security.encrypt(inputPwd, salt))) {
            return true;
        }
        return false;
    }

}
