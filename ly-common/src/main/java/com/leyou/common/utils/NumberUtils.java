package com.leyou.common.utils;

import java.util.Random;

public class NumberUtils {
    public static double toDouble(String value) {
        return Double.parseDouble(value);
    }

    /**
     * 生成位数自定义的0-9随机数
     *
     * @param len 总位数
     * @return 随机数
     */
    public static String generateCode(int len) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int nextInt = new Random().nextInt(9);
            code.append(nextInt);
        }
        return code.toString();
    }
}
