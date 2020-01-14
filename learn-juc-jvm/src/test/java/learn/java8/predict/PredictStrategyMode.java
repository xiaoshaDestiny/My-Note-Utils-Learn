package learn.java8.predict;

/**
 * @author xrb
 * @create 2020-01-13 16:27
 * 策略设计模式的抽象接口
 * 当接口不是函数式接口的时候，@FunctionalInterface注解会标红报错
 *
 */
@FunctionalInterface
public interface PredictStrategyMode<T> {

    /**
     * 检查单个对象是够满足条件
     * @param t
     * @return
     */
    boolean checkOne(T t);


    default boolean checkTwo(T t){
        return true;
    }

    static boolean checkThree(){
        return false;
    }

}
