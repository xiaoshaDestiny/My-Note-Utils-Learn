package com.learn;

import com.learn.eunms.CountryEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2020-12-21 23:00
 */
@Builder
@Data
public class WorkerInfo {

    private String cnName;

    private String enName;

    private String id;

    private Integer age;

    private Double height;

    private LocalDateTime birthday;

    private CountryEnum country;
}
