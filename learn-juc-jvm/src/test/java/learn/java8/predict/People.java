package learn.java8.predict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class People {
	private int id;
	private String name;
	private int age;
	private double salary;
	private String address;
}
