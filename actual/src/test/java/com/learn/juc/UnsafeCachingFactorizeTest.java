package com.learn.juc;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-01-24 20:20
 */
@Slf4j
public class UnsafeCachingFactorizeTest {
    @Test
    public void testServiceCount() throws InterruptedException {
        UnsafeCachingFactorize service = new UnsafeCachingFactorize();

        List<Integer> counts = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(() -> {
                Integer count = service.serviceCount();
                if (Objects.nonNull(count)){
                    counts.add(count);
                }
            });
            thread.start();
        }

        Thread.sleep(1000);
        log.info("max is：{}", Collections.max(counts));
        log.info("counts size：{}",counts.size());
    }


    @Test
    public void testServiceCountSafe() throws InterruptedException {
        UnsafeCachingFactorize service = new UnsafeCachingFactorize();

        List<Integer> counts = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(() -> {
                Integer count = service.serviceCountSafe();
                if (Objects.nonNull(count)){
                    counts.add(count);
                }
            });
            thread.start();
        }

        Thread.sleep(1000);
        log.info("max is：{}",Collections.max(counts));
        log.info("counts size：{}",counts.size());
    }


    @Test
    public void testServiceUnsafe() throws InterruptedException {
        UnsafeCachingFactorize service = new UnsafeCachingFactorize();

        List<Boolean> trueThreads = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(() -> {
                //随机取数字1-2
                int paramX = (int) (1 + Math.random() * (2 - 1 + 1));
                String value = service.serviceUnsafe(paramX);
                trueThreads.add(value.substring(1,2).equals(String.valueOf(paramX)));
                log.info("{},{}",paramX,value);
            });
            thread.start();
        }

        Thread.sleep(2000);
        List<Boolean> status = trueThreads.stream().distinct().collect(Collectors.toList());
        List<Boolean> trueCollection = trueThreads.stream().filter(x-> x).collect(Collectors.toList());

        log.info("线程数量：{}",trueThreads.size());
        log.info("期望是1：{}",status.size());
        log.info("期望是true：{}",trueCollection.size() == trueThreads.size());
    }




    @Test
    public void testService() throws InterruptedException {
        UnsafeCachingFactorize service = new UnsafeCachingFactorize();

        List<Boolean> trueThreads = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(() -> {
                //随机取数字1-2
                int paramX = (int) (1 + Math.random() * (2 - 1 + 1));
                String value = service.service(paramX);
                trueThreads.add(value.substring(1,2).equals(String.valueOf(paramX)));
                log.info("{},{}",paramX,value);
            });
            thread.start();
        }

        Thread.sleep(2000);
        List<Boolean> status = trueThreads.stream().distinct().collect(Collectors.toList());
        List<Boolean> trueCollection = trueThreads.stream().filter(x-> x).collect(Collectors.toList());

        log.info("线程数量：{}",trueThreads.size());
        log.info("期望是1：{}",status.size());
        log.info("期望是true：{}",trueCollection.size() == trueThreads.size());
    }

    @Test
    public void testServiceSafe() throws InterruptedException {
        UnsafeCachingFactorize service = new UnsafeCachingFactorize();

        List<Boolean> trueThreads = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(() -> {
                //随机取数字1-2
                int paramX = (int) (1 + Math.random() * (2 - 1 + 1));
                String value = service.serviceSafe(paramX);
                trueThreads.add(value.substring(1,2).equals(String.valueOf(paramX)));
                log.info("{},{}",paramX,value);
            });
            thread.start();
        }

        Thread.sleep(2000);
        List<Boolean> status = trueThreads.stream().distinct().collect(Collectors.toList());
        List<Boolean> trueCollection = trueThreads.stream().filter(x-> x).collect(Collectors.toList());

        log.info("线程数量：{}",trueThreads.size());
        log.info("期望是1：{}",status.size());
        log.info("期望是true：{}",trueCollection.size() == trueThreads.size());

    }
}