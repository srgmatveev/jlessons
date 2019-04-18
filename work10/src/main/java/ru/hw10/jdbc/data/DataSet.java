package ru.hw10.jdbc.data;

public class DataSet {
    private long id;

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id ;
    }

    public String getTableName() {
        if (this.getClass().isAnnotationPresent(TableAlias.class)) {
            String tmpName = this.getClass().getAnnotation(TableAlias.class).name();
            if (!tmpName.isEmpty())
                return tmpName;
        }
        return this.getClass().getSimpleName();
    }
}


