package ru.hw10.hibernate.base.dataSet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressDataSet extends DataSet {
    @Column
    private String street;
    @OneToOne(mappedBy = "address")
    private UserDataSet user;

}
