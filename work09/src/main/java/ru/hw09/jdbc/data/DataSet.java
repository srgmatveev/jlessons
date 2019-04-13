package ru.hw09.jdbc.data;

public abstract class DataSet {
    private long id;

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract String getTableName();
}


