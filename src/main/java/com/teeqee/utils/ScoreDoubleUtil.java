package com.teeqee.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class ScoreDoubleUtil {

    /**
     * @return 返回当天24点的时间戳
     */
    public static  long todatTimestamp(){
        Long nowTime = System.currentTimeMillis();
        long todayZero = nowTime / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return todayZero / 1000;
    }


    /**
     * @param d 将double 转成int ,不进行四舍五入
     * @return
     */
    public static int douleToInt(double d) {
        //将丢失精度
        return (int) d;
    }

    /**
     * @param i 将Int 转成double并在小数后面增加时间戳到秒
     * @return
     */
    public static double intToDouble(int i) {
        //2065-01-24 13:20:00
        long unreachable = 3800000000L;
        long notTime = System.currentTimeMillis() / 1000;
        //截取第一个
        long space = unreachable - notTime;
        String replace = Long.toString(space);
        String intString = Integer.toString(i);
        return Double.valueOf(intString + "." + replace);
    }


    /**
     * @Description: 将long类型转成double
     * @Author: zsj
     * @Date: 2019/9/19
     */
    public static double longToDouble(long i) {
        //2065-01-24 13:20:00
        long unreachable = 3000000000L;
        //秒级别
        long nowTime = System.currentTimeMillis() / 1000;
        //越后越小
        long space = unreachable - nowTime;
        String replace = Long.toString(space);
        String string = Long.toString(i);
        return Double.valueOf(string + "." + replace);
    }

    /**
     * @param paramArray 从数组中随机抽取若干不重复元素
     *                   paramArray:被抽取数组
     *                   count:抽取元素的个数
     * @param count
     * @return
     */
    public static String[] getRandomArray(String[] paramArray, int count) {
        if (paramArray.length < count) {
            return paramArray;
        }
        String[] newArray = new String[count];
        Random random = new Random();
        //接收产生的随机数
        int temp = 0;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= count; i++) {
            //将产生的随机数作为被抽数组的索引
            temp = random.nextInt(paramArray.length);
            if (!(list.contains(temp))) {
                newArray[i - 1] = paramArray[temp];
                list.add(temp);
            } else {
                i--;
            }
        }
        return newArray;
    }
}
