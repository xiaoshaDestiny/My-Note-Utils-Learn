package com.learn;

import com.learn.eunms.CountryEnum;

/**
 * @author xu.rb
 * @since 2020-12-20 17:51
 */
public class Person {

    private String name;

    private Integer age;

    private CountryEnum country;

    public Person(String name, Integer age, CountryEnum country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public static final class PersonBuilder {
        private String name;
        private Integer age;
        private CountryEnum country;

        public PersonBuilder aPerson() {
            return new PersonBuilder();
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        /**
         * 枚举构造的时候可以重载的，对类型的转换很有帮助。比如日期时间类型，在这里重载之后，使用的地方就不用刻意构造
         */
        public PersonBuilder country(CountryEnum country) {
            this.country = country;
            return this;
        }

        public PersonBuilder country(Integer countryCode) {
            this.country = CountryEnum.fromCode(countryCode);
            return this;
        }

        public PersonBuilder country(String name) {
            this.country = CountryEnum.fromName(name);
            return this;
        }

        public Person build() {
            return new Person(name, age, country);
        }
    }
}
