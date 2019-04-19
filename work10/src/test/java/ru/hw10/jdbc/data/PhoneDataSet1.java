package ru.hw10.jdbc.data;

@TableAlias(name = "Phone")
public class PhoneDataSet1 extends DataSet {
    @DataField
    private String number;

    public PhoneDataSet1(long id, String number) {
        super(id);
        this.number = number;
    }

    public PhoneDataSet1(String number) {
        this(-1,number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet1{" +
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
