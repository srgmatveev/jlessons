package ru.hw10.jdbc.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@TableAlias(name = "User")
class UsersDataSet1 extends  DataSet{
    @DataField
    private String name;
    @DataField
    private int age;
    @IsOneToOne(name = "Address")
    private AddressDataSetTest address;

    private void setName(String name) {
        this.name = name;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public UsersDataSet1(long id, String name, int age) {
        super(id);this.name = name;
        this.age = age;
    }

    public UsersDataSet1(String name, int age) {
        super(-1);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }


    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id='" + this.getId() + "'" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public AddressDataSetTest getAddress() {
        return address;
    }

    private void setAddress(AddressDataSetTest address) {
        this.address = address;
    }
}

@TableAlias(name = "User")
class UsersDataSet2 extends  UsersDataSet1{
    @IsOneToOne
    private AddressDataSetTest address;
    public UsersDataSet2(long id, String name, int age) {
        super(id, name, age);
    }
}


@TableAlias(name = "")
class UsersDataSet3 extends  UsersDataSet1{
    public UsersDataSet3(long id, String name, int age) {
        super(id, name, age);
    }
}

class UsersDataSetTest {
    private  UsersDataSet1 usersDataSet, usersDataSet1, usersDataSet2, usersDataSet3;
    private String name;
    private int age;
    private long id;

    @BeforeEach
    void setUP(){
        name = "Boris";
        age =35;
        id =1;
        usersDataSet = new UsersDataSet1(id,name,age);
    }

    @AfterEach
    void  tearDown(){
        usersDataSet = null;
        System.gc();
    }

    @Test
    void getName() {
        assertEquals(usersDataSet.getName(),name);
    }

    @Test
    void getAge() {
        assertEquals(usersDataSet.getAge(),age);
    }

    @Test
    void getId() {
        assertEquals(usersDataSet.getId(),id);
    }

    @Test
    void getTableName() {
        usersDataSet1 = new UsersDataSet1(id,name,age);
        usersDataSet2 = new UsersDataSet2(id,name,age);
        usersDataSet3 = new UsersDataSet3(id,name,age);

        assertEquals(usersDataSet2.getTableName(),"User");

        assertEquals(usersDataSet1.getTableName(),"UsersDataSet1");
        assertEquals(usersDataSet3.getTableName(),"UsersDataSet3");
    }

    @Test
    void toString1() {
        String tmpUsersDataSet = "UsersDataSet{id='1', name='Boris', age=35}";
        assertEquals(usersDataSet.toString(),tmpUsersDataSet);
    }
}