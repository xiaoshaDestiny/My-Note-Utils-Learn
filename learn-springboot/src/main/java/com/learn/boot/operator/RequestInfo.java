package com.learn.boot.operator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xu.rb
 * @since 2021-05-15 01:09
 */
@Builder
@Getter
@Setter
@ToString
public class RequestInfo {
    private String env;
    private Integer projectId;
}
