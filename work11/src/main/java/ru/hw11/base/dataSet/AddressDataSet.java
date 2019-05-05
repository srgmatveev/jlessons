package ru.hw11.base.dataSet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressDataSet extends DataSet {
    @Column
    private String street;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDataSet user;

}
