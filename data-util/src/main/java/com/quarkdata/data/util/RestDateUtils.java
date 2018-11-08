package com.quarkdata.data.util;

import java.util.Date;

/**
 * @author maorl
 * @date 1/2/18.
 */
public class RestDateUtils {

    /**
     * 获取剩余时间
     *
     * @param expireTime
     * @return
     */
    public static String getRestTime(Date expireTime) {
        long t = expireTime.getTime() - System.currentTimeMillis();
        String restTime;
        if (t > 24 * 60 * 60 * 1000) {
            restTime = t / (24 * 60 * 60 * 1000) + "天后";
        } else if (t > 60 * 60 * 1000) {
            restTime = t / (60 * 60 * 1000) + "小时后";
        } else if (t > 60 * 1000) {
            restTime = t / (60 * 1000) + "分钟后";
        } else if (t > 1000) {
            restTime = t / 1000 + "秒后";
        } else {
            restTime = "已失效";
        }
        return restTime;
    }
}
