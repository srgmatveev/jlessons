package ru.hw10.jdbc.data;

@TableAlias(name = "Address")
public class AddressDataSet extends DataSet {
    @DataField
    private String street;

    public AddressDataSet(long id, String street) {
        super(id);
        this.street = street;
    }

    public AddressDataSet(String street) {
       this(-1, street);
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }


    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id='" + this.getId() + "'" +
                "street='" + street + '\'' +
                '}';
    }
}
