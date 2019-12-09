package com.learn.juc;

import lombok.Getter;

/**
 * @author xrb
 * @create 2019-11-26 19:54
 */
public enum CountryEnum {
    
    ONE(1,"韩国"),TWO(2,"魏国"),THREE(3,"燕国"),FOUR(4,"赵国"),FIVE(5,"楚国"),SIX(6,"齐国");

    @Getter
    private Integer retCode;

    @Getter
    private String retMessage;

    CountryEnum(Integer retCode,String retMessage){
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum elem : values) {
            if(index == elem.getRetCode()){
                return elem;
            }
        }
        return null;
    }
}
