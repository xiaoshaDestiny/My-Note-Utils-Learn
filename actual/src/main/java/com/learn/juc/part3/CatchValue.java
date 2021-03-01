package com.learn.juc.part3;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-03-02 00:16
 */
public class CatchValue {

    private final Integer lastNum ;
    private final String value;

    public CatchValue(Integer lastNum, String value) {
        this.lastNum = lastNum;
        this.value = value;
    }

    public String getValue(Integer lastNumIn) {
        if (Objects.isNull(lastNum) || !(lastNum.intValue() == lastNumIn.intValue())) {
            return null;
        }
        return value;
    }
}
