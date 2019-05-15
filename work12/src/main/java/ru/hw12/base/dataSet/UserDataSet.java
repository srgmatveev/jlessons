package ru.hw12.base.dataSet;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;
    @Column(length = 3)
    private int age;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressDataSet address;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneDataSet> phoneNumbers = new ArrayList<>();


    public void addAddress(AddressDataSet address) {
            address.setUser(this);
            this.address = address;
    }

    public void addPhoneNumber(PhoneDataSet phoneDataSet) {
        this.phoneNumbers.add(phoneDataSet);
        phoneDataSet.setUser(this);
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address= '" + address.getStreet() +"'" +
                fromPhones() +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneDataSet> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    private String fromPhones(){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for(PhoneDataSet item : phoneNumbers){
            sb.append(", ");
            sb.append(item.getNumber());
        }
        sb.append(" ]");

        return sb.toString().replaceFirst(", ","");
    }

}

