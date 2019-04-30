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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhoneDataSet> phoneNumbers = new ArrayList<>();
}

