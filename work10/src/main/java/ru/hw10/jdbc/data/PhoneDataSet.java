package ru.hw10.jdbc.data;

@TableAlias(name = "Phone")
public class PhoneDataSet extends DataSet {
    @DataField
    private String number;

    public PhoneDataSet(long id, String number) {
        super(id);
        this.number = number;
    }

    public PhoneDataSet(String number) {
        this(-1,number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id='" + this.getId() + "'" +
                "number='" + number + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    private void setNumber(String number) {
        this.number = number;
    }
}
