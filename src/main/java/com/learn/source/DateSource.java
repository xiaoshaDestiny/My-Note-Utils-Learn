package com.learn.source;

import java.util.Calendar;

/**
 * @author xrb
 * @create 2019-12-27 16:38
 * 日期操作相关的资源
 */
public class DateSource {

    /**
     * 获得某个月最大天数
     * @param yearMonth 可以是8位字符串 yyyyMMdd  也可以是6位字符串yyyyMM
     * @return
     */
    public int getMaxDayByYearMonth(String yearMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.YEAR, Integer.parseInt( yearMonth.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(yearMonth.substring(4,6)) - 1);
        return calendar.getActualMaximum(Calendar.DATE);
    }



}
