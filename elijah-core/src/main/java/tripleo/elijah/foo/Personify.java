package tripleo.elijah.foo;

import com.baeldung.annotation.processor.BuilderProperty;

public class Personify {

    private int age;

    private String name;

    public int getAge() {
        return age;
    }

    @BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    @BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

}
