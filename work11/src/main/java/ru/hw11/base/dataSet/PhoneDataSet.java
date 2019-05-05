package ru.hw11.base.dataSet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PhoneDataSet extends DataSet {
    @Column
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDataSet user;

    public PhoneDataSet(final String number) {
        this.number = number;
    }
}
