package com.teeqee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: ChaoChongMap
 * @Version: 1.0.0
 * @Time: 2020-04-26 下午 02:36
 * @Project: java_chaochong
 * @Package: com.teeqee
 * @Software: IntelliJ IDEA
 */
public class ChaoChongMap {
    public static void main(String[] args) {
        Opponent opponent = new Opponent();
        String s = JSON.toJSONString(opponent, SerializerFeature.WriteMapNullValue);
        System.out.println(s);
    }
    @Test
    public void testFormat() {

        System.out.println(format(new Date()));     //今天

        System.out.println(format(new DateTime(new Date()).minusDays(1).toDate())); //昨天

        System.out.println(format(new DateTime(new Date()).minusDays(2).toDate())); //2天前

        System.out.println(format(new DateTime(new Date()).plusDays(1).toDate())); //1天后
    }

    private String format(Date date) {
        DateTime now = new DateTime();
        DateTime today_start = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0);
        DateTime today_end = today_start.plusDays(1);
        DateTime yesterday_start = today_start.minusDays(1);

        if(date.after(today_start.toDate()) && date.before(today_end.toDate())) {
            return String.format("今天 %s", new DateTime(date).toString("HH:mm"));
        } else if(date.after(yesterday_start.toDate()) && date.before(today_start.toDate())) {
            return String.format("昨天 %s", new DateTime(date).toString("HH:mm"));
        }

        return new DateTime(date).toString("yyyy-MM-dd HH:mm");
    }
}

