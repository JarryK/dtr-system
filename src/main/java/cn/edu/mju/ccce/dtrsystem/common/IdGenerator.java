package cn.edu.mju.ccce.dtrsystem.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.common.IdGenerator<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>mySQL-id生成<br>
 * <b>创建时间：</b>2020-02-18 21:34<br>
 */
public class IdGenerator {

    public static long genLongId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String timestamp = sdf.format(Calendar.getInstance().getTime());
        int maxLength = String.valueOf(Long.MAX_VALUE).length();
        int genLength = maxLength - timestamp.length();
        String randomNuber = getRandomNuber(genLength);
        String string = timestamp + randomNuber;
        return Long.parseLong(string);
    }

    /**
     * . 生成随机码
     *
     * @param m 位数
     * @return String
     */
    public static String getRandomNuber(int m) {
        Random rd = new Random();
        int number = 1;
        for (int i = 0; i < m; i++) {
            number *= 10;
        }
        String string = rd.nextInt(number) + String.valueOf(rd.nextInt(number)) + number;
        return string.substring(0, m);
    }
}

