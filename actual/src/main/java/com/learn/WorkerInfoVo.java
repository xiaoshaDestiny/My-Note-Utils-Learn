package com.learn;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WorkerInfoVo {
    private String name;

    private Integer id;

    private Integer age;

    private Double height;

    private String birthday;

    private String country;
}
