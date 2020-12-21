package com.learn;

import com.learn.eunms.CountryEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CatInfo {
    private String name;
    private Integer age;
    private CountryEnum countryEnum;
    private String color;
}
