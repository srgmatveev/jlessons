package ru.hw10.jdbc.data;

import java.lang.annotation.Annotation;

@TableAlias(name = "User")
public class UsersDataSet extends DataSet {
    @DataField
    private String name;
    @DataField
    private int age;

    @IsOneToOne(name = "Address")
    @DataField
    private AddressDataSet address;

    public PhoneDataSet getPhone() {
        return phone;
    }

    private void setPhone(PhoneDataSet phone) {
        this.phone = phone;
    }

    @IsManyToOne(name = "Phone")
    @DataField
    private PhoneDataSet phone;

    private void setName(String name) {
        this.name = name;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public UsersDataSet(long id, String name, int age, AddressDataSet address, PhoneDataSet phone) {
        super(id);
        this.name = name;
        this.age = age;
        this.address =  address;
        this.phone =   phone;
    }

    public UsersDataSet(String name, int age, AddressDataSet address, PhoneDataSet phone) {
       this(-1,name, age, address, phone);
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
                ", street='" + address.getStreet() +"'"+
                ", phone=" + phone.getNumber() +
                '}';
    }

    public AddressDataSet getAddress() {
        return address;
    }

    private void setAddress(AddressDataSet address) {
        this.address = address;
    }
}
