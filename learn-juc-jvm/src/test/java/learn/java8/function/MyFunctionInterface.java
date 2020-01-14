package learn.java8.function;

/**
 * @author xrb
 * @create 2020-01-14 9:27
 */
@FunctionalInterface
public interface MyFunctionInterface {

    Integer simpleCalNumber(Integer num);


    default Integer numberPlusPlus(Integer num){
        return num++;
    }

    static Integer numberSubSub(Integer num){
        return num--;
    }

}
