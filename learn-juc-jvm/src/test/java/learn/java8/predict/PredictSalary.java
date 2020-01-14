package learn.java8.predict;

/**
 * @author xrb
 * @create 2020-01-13 16:31
 */
public class PredictSalary implements PredictStrategyMode<People>{

    /**
     * 薪资大于5000
     * @param people
     * @return
     */
    @Override
    public boolean checkOne(People people) {
        return people.getSalary() > 5000;
    }

    @Override
    public boolean checkTwo(People people) {
        return people.getSalary() > 500;
    }
}
