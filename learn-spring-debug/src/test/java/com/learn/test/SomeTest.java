package com.learn.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2020-08-09 20:12
 */
public class SomeTest {

    @Test
    public void testTime(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        //yyyy-MM-dd HH:mm:ss   转    yyyy-MM-dd
        LocalDateTime localDateTime = LocalDateTime.parse("2019-01-01 08:09:55", dtf);
        String res = localDateTime.format(df);
        System.out.println(res);



        //yyyy-MM-dd            转    yyyy-MM-dd HH:mm:ss
        LocalDate localDate = LocalDate.parse("2020-01-01", df);
        Date date = Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
        LocalDateTime localDateTime1 = date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        String format = localDateTime1.format(dtf);
        System.out.println(format);



        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date2 = Date.from(instant);
        Instant instant2 = date2.toInstant();
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(instant2, zone);
        String format2 = localDateTime2.format(dtf);
        System.out.println(format2);
    }


    @Test
    public void testTime2(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //yyyy-MM-dd            转    yyyy-MM-dd HH:mm:ss
        LocalDate localDate = LocalDate.parse("2020-01-01", df);
        ZoneId zone = ZoneId.systemDefault();
        Date date = Date.from(localDate.atStartOfDay().atZone(zone).toInstant());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zone);
        String format2 = localDateTime.format(dtf);
        System.out.println(format2);
    }
}
