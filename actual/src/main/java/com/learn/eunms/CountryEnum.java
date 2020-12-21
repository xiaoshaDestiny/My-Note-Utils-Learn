package com.learn.eunms;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2020-12-20 17:54
 */

/**
 * 枚举类一般只提供get方法
 * 注意 字段、构造器、枚举值
 * fromCode方法
 */
public enum CountryEnum {

    UNKNOWN(1,"未知","未知"),
    CHINA(100,"中国","亚洲"),
    American(101,"美国","北美洲");

    private Integer code;
    private String name;
    private String location;

    CountryEnum(int code, String name, String location) {
        this.code = code;
        this.name = name;
        this.location = location;
    }


    public static CountryEnum fromCode(Integer code) {
        for (CountryEnum countryEnum : CountryEnum.values()) {
            if (countryEnum.getCode() == code) {
                return countryEnum;
            }
        }
        return UNKNOWN;
    }

    public static CountryEnum fromName(String name) {
        for (CountryEnum countryEnum : CountryEnum.values()) {
            if (countryEnum.getName() == name) {
                return countryEnum;
            }
        }
        return UNKNOWN;
    }

    public static String getName(Integer code) {
        return fromCode(code).getName();
    }

    public static String getLocation(Integer code) {
        return fromCode(code).getLocation();
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
}

