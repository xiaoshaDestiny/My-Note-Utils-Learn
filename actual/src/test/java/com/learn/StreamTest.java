package com.learn;

import com.alibaba.fastjson.JSON;
import com.learn.eunms.CountryEnum;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2020-12-21 22:57
 */
@Slf4j
public class StreamTest {

    private static List<WorkerInfo> workers = Lists.newArrayList();

    static {
        workers.add(WorkerInfo.builder().id("1").cnName("张三").enName("zsan").age(22).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(170.2).build());
        workers.add(WorkerInfo.builder().id("2").cnName("李四").enName("lisi").age(22).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(189.2).build());
        workers.add(WorkerInfo.builder().id("3").cnName("郭杰瑞").enName("jerry").age(28).birthday(LocalDateTime.now()).country(CountryEnum.American).height(165.2).build());
        workers.add(WorkerInfo.builder().id("4").cnName("詹姆斯").enName("jams").age(35).birthday(LocalDateTime.now()).country(CountryEnum.American).height(161.8).build());
        workers.add(WorkerInfo.builder().id("5").cnName("米老鼠").enName("jerry").age(12).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(180.2).build());
        workers.add(WorkerInfo.builder().id("6").cnName("汤姆猫").enName("tom").age(4).birthday(LocalDateTime.now()).country(CountryEnum.American).height(185.2).build());
    }

    /**
     * 获取集合中的某一列
     */
    @Test
    public void mapGetColumn(){
        List<String> getCnNameColumn = workers.stream().map(WorkerInfo::getCnName).collect(Collectors.toList());
        log.info("{}.取cnName列", JSON.toJSONString(getCnNameColumn));

        List<Integer> getAgeColumnDistinct = workers.stream().map(WorkerInfo::getAge).distinct().collect(Collectors.toList());
        log.info("{}.取age列,并用distinct去重", JSON.toJSONString(getAgeColumnDistinct));

        Set<Integer> getAgeColumnSet = workers.stream().map(WorkerInfo::getAge).distinct().collect(Collectors.toSet());
        log.info("{}.取age列,并用收集方法的toSet()方法去重", JSON.toJSONString(getAgeColumnSet));


        //取数据列原始方法演示一
        List<String> cnNames = new ArrayList<>();
        for (WorkerInfo worker : workers) {
            cnNames.add(worker.getCnName());
        }
        log.info("{}.取cnName列(不推荐)", JSON.toJSONString(cnNames));

        //取数据列原始方法演示二
        List<String> cnNames2 = new ArrayList<>();
        workers.forEach(worker -> cnNames2.add(worker.getCnName()));
        log.info("{}.取cnName列(不推荐)", JSON.toJSONString(cnNames2));
    }

}
