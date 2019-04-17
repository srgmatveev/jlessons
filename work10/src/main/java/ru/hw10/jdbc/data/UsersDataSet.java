package ru.hw10.jdbc.data;

import java.lang.annotation.Annotation;

@TableAlias(name = "User")
public class UsersDataSet extends DataSet {
    @DataField
    private String name;
    @DataField
    private int age;

    private void setName(String name) {
        this.name = name;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public UsersDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public UsersDataSet(String name, int age) {
        super(-1);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }

    public String getTableName() {
        if (this.getClass().isAnnotationPresent(TableAlias.class)) {
            String tmpName = this.getClass().getAnnotation(TableAlias.class).name();
            if (!tmpName.isEmpty())
                return tmpName;
        }
        return this.getClass().getSimpleName();
    }


    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id='" + this.getId() + "'" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
