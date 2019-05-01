package ru.hw10.hibernate.base.dataSet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

