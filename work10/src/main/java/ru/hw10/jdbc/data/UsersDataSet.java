package ru.hw10.jdbc.data;

import java.lang.annotation.Annotation;

@TableAlias(name = "User")
public class UsersDataSet extends DataSet {
    @DataField
    private String name;
    @DataField
    private int age;

    @IsOneToOne(name = "Address")
    private AddressDataSet address;

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


    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id='" + this.getId() + "'" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public AddressDataSet getAddress() {
        return address;
    }

    private void setAddress(AddressDataSet address) {
        this.address = address;
    }
}
