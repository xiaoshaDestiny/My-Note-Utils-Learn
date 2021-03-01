package com.learn;

import com.alibaba.fastjson.JSON;
import com.learn.eunms.CountryEnum;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;
import org.testng.collections.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class StreamFilterTest {
    private static List<WorkerInfo> workers = Lists.newArrayList();

    static {
        workers.add(WorkerInfo.builder().id("1").cnName("张三").enName("zsan").age(22).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(170.2).build());
        workers.add(WorkerInfo.builder().id("2").cnName("李四").enName("lisi").age(22).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(189.2).build());
        workers.add(WorkerInfo.builder().id("3").cnName("郭杰瑞").enName("jerry").age(28).birthday(LocalDateTime.now()).country(CountryEnum.American).height(165.2).build());
        workers.add(WorkerInfo.builder().id("4").cnName("詹姆斯").enName("jams").age(35).birthday(LocalDateTime.now()).country(CountryEnum.American).height(161.8).build());
        workers.add(WorkerInfo.builder().id("5").cnName("米老鼠").enName("jerry").age(12).birthday(LocalDateTime.now()).country(CountryEnum.CHINA).height(180.2).build());
        workers.add(WorkerInfo.builder().id("6").cnName("汤姆猫").enName("tom").age(4).birthday(LocalDateTime.now()).country(CountryEnum.American).height(185.2).build());
    }

    @Test
    public void findBasic() {
        workers.stream().filter(worker -> worker.getAge() == 22).findFirst().ifPresent(x-> log.info(x.toString()));
        workers.stream().filter(worker -> worker.getAge() == 4).findAny().ifPresent(x-> log.info(x.toString()));

        boolean containsAge100 = workers.stream().noneMatch(worker -> worker.getAge() == 100);
        log.info("workers 中不存在age是100的人 {}",containsAge100);

        boolean containsAge4 = workers.stream().anyMatch(worker -> worker.getAge() == 4);
        log.info("workers 中存在age是4的人 {}",containsAge4);

        boolean allIdLengthIs1 = workers.stream().allMatch(worker -> worker.getId().length() == 1);
        log.info("workers 中所有元素Id的长度是1 {}",allIdLengthIs1);

        //ifPresent 是一个Optional的方法,集合中不存在元素,就不会继续执行
        workers.stream().filter(worker -> worker.getAge() == 100).findFirst().ifPresent(x-> log.info(x.toString()));


        //WorkerInfo(cnName=张三, enName=zsan, id=1, age=22, height=170.2, birthday=2021-02-27T01:29:52.116, country=CHINA)
        //WorkerInfo(cnName=汤姆猫, enName=tom, id=6, age=4, height=185.2, birthday=2021-02-27T01:29:52.118, country=American)
        //workers 中不存在age是100的人 true
        //workers 中存在age是4的人 true
        //workers 中所有元素Id的长度是1 true
    }

    @Test
    public void filterBasic() {
        //筛选age=22的全部对象
        List<WorkerInfo> age22Workers = workers.stream().filter(worker -> worker.getAge() == 22).collect(Collectors.toList());

        //筛选age=22的数量
        long count = workers.stream().filter(worker -> worker.getAge() == 22).count();
        Assert.assertEquals(age22Workers.size(),count);
    }

    @Test
    public void filterBasicNpe() {
        workers.add(WorkerInfo.builder().id("7").cnName("npe").build());
        List<WorkerInfo> age22Workers = workers.stream().filter(this::ageIs22).collect(Collectors.toList());
        Assert.assertEquals(age22Workers.size(),2);
    }

    private boolean ageIs22 (WorkerInfo workerInfo){
        return Objects.nonNull(workerInfo) && Objects.nonNull(workerInfo.getAge()) && workerInfo.getAge() == 22;
    }


    /**
     * map()灵活运用
     * 1、获取集合中的某一字段集合
     */
    @Test
    public void mapGetColumn() {
        List<String> getCnNameColumn = workers.stream()
                .map(WorkerInfo::getCnName)
                .collect(Collectors.toList());
        log.info("{}.取cnName列", JSON.toJSONString(getCnNameColumn));
        //["张三","李四","郭杰瑞","詹姆斯","米老鼠","汤姆猫"].取cnName列

        List<Integer> getAgeColumnDistinct = workers.stream()
                .map(WorkerInfo::getAge)
                .distinct()
                .collect(Collectors.toList());
        log.info("{}.取age列,并用distinct去重", JSON.toJSONString(getAgeColumnDistinct));
        //[22,28,35,12,4].取age列,并用distinct去重
    }

    /**
     * map()灵活运用
     * 2、map天生自带convert的抽象用于转换对象（工作中常用）
     * <p>
     * 贴近数据库层数据，数据力求准确，种类也较多，Date,String,Double等
     * 贴近后端编码业务，数据种类最为丰富，需要定义枚举、时间日期类型计算等等这些都会构造出复杂的对象
     * 贴近前端展示数据，数据类型简单较好
     * <p>
     * 利用map配合基础的convert方法
     */
    @Test
    public void mapConvert() {
        List<WorkerInfoVo> workerInfoVoList = workers.stream().map(this::convertToVo).collect(Collectors.toList());
        log.info(JSON.toJSONString(workerInfoVoList));

        //[{"age":22,"birthday":"2020-12-28 21:39:46","country":"中国","height":170.2,"id":1,"name":"张三"},
        // {"age":22,"birthday":"2020-12-28 21:39:46","country":"中国","height":189.2,"id":2,"name":"李四"},
        // {"age":28,"birthday":"2020-12-28 21:39:46","country":"美国","height":165.2,"id":3,"name":"郭杰瑞"},
        // {"age":35,"birthday":"2020-12-28 21:39:46","country":"美国","height":161.8,"id":4,"name":"詹姆斯"},
        // {"age":12,"birthday":"2020-12-28 21:39:46","country":"中国","height":180.2,"id":5,"name":"米老鼠"},
        // {"age":4,"birthday":"2020-12-28 21:39:46","country":"美国","height":185.2,"id":6,"name":"汤姆猫"}]
    }

    private WorkerInfoVo convertToVo(WorkerInfo workerInfo) {
        return WorkerInfoVo.builder()
                .id(Integer.parseInt(workerInfo.getId()))
                .age(workerInfo.getAge())
                .name(workerInfo.getCnName())
                .country(workerInfo.getCountry().getName())
                .height(workerInfo.getHeight())
                .birthday(workerInfo.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

    }


    /**
     * 没有直接使用值 stream会返回一个 null元素 不会直接异常
     */
    @Test
    public void mapWrongCaseNotUse() {
        List<WorkerInfo> workerInfos = Lists.newArrayList(WorkerInfo.builder().id("1").cnName("张三").enName("zsan").build());
        List<String> ageStrings = workerInfos.stream()
                .map(WorkerInfo::getAge)
                .map(String::valueOf)
                .collect(Collectors.toList());
        Assert.assertTrue(CollectionUtils.hasElements(ageStrings));
    }

    /**
     * 使用了null元素，抛出npe
     * 使用值之前切记需要校验，良好的编码习惯，避免大量空指针，防御性编程很重要
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void mapWrongCaseUseValue() {
        List<WorkerInfo> workerInfos = Lists.newArrayList(WorkerInfo.builder().id("1").cnName("张三").enName("zsan").build());
        List<Integer> ageStrings = workerInfos.stream()
                .map(WorkerInfo::getAge)
                .map(age -> age + 1)
                .collect(Collectors.toList());
        Assert.assertFalse(CollectionUtils.hasElements(ageStrings));
    }


    /**
     * Stream大部分情况下需要一个收集的函数
     */
    @Test
    public void mapWrongCaseLostColleation() {
        Stream<String> stringStream = workers.stream().map(WorkerInfo::getCnName);
        Stream<Integer> ageStream = workers.stream().map(WorkerInfo::getAge);

    }









}