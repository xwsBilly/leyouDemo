package com.leyou.user.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * CodeUtils
 * 密码md5加密
 *
 * @author Billy
 * @date 2019/9/19 15:28
 */
public class CodecUtils {
    /**
     * MD5加密
     *
     * @param data 原数据
     * @param salt 盐
     * @return 密文
     */
    public static String md5Hex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    /**
     * MD5解密
     *
     * @param data 密文
     * @param salt 盐
     * @return 原数据
     */
    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    /**
     * 生成随机的盐
     *
     * @return 盐
     */
    public static String generateSalt() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
