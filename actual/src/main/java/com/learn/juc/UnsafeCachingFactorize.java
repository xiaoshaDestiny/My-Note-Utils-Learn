package com.learn.juc;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xu.rb
 * @since 2021-01-24 20:12
 */
public class UnsafeCachingFactorize {

    /**
     * 单个变量 i++非原子操作（Unsafe）
     */
    private Integer count = 1;
    public Integer serviceCount() {
        return this.count++;
    }


    /**
     * 单个变量原子操作（竞态条件 Safe）
     */
    private final AtomicInteger  countSafe = new AtomicInteger(0);
    public Integer serviceCountSafe() {
        return countSafe.incrementAndGet();
    }


    /**
     * 虽然用了原子变量 但是多个变量之间的操作不是原子的，也会失效
     */
    private final AtomicReference<Integer> lastNumUnsafe = new AtomicReference<>();
    private final AtomicReference<String> valueUnsafe = new AtomicReference<>();
    public String serviceUnsafe(Integer key) {
        String result = null;
        if (Objects.nonNull(lastNumUnsafe.get()) && key.equals(lastNumUnsafe.get())){
            result = valueUnsafe.get()+"cache";
        }

        if (result == null) {
            if (key == 1){
                result = "v1";
            }
            if (key == 2){
                result = "v2";
            }
            lastNumUnsafe.set(key);
            valueUnsafe.set(result);
        }
        return result;
    }


    /**
     * 多个原子变量的操作不是原子的（Unsafe）
     * 但是此时使用Atomic已经没有意义了
     */
    private final AtomicReference<Integer> lastNum = new AtomicReference<>();
    private final AtomicReference<String> value = new AtomicReference<>();
    public String service(Integer key) {
        String result = null;

        synchronized (this){
            if (Objects.nonNull(lastNum.get()) && key.equals(lastNum.get())){
                result = value.get()+"cache";
            }
        }

        if (result == null) {
            if (key == 1){
                result = "v1";
            }
            if (key == 2){
                result = "v2";
            }
            synchronized (this){
                lastNum.set(key);
                value.set(result);
            }
        }
        return result;
    }



    private Integer lastNumSafe;
    private String valueSafe;
    public String serviceSafe(Integer key) {
        String result = null;

        synchronized (this){
            if (key.equals(lastNumSafe)){
                result = valueSafe + "cache";
            }
        }

        if (result == null) {
            if (key == 1){
                result = "v1";
            }
            if (key == 2){
                result = "v2";
            }
            synchronized (this){
                lastNumSafe = key;
                valueSafe = result;
            }
        }
        return result;
    }
}
