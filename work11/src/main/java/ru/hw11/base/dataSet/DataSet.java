package ru.hw11.base.dataSet;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
