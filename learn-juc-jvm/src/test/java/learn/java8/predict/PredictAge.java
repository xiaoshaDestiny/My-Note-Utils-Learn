package learn.java8.predict;

/**
 * @author xrb
 * @create 2020-01-13 16:29
 */
public class PredictAge implements PredictStrategyMode<People> {

    /**
     * 年龄小于35
     * @param people
     * @return
     */
    @Override
    public boolean checkOne(People people) {
        return people.getAge() <= 35;
    }

    @Override
    public boolean checkTwo(People people) {
        return people.getAge() <= 36;
    }
}
