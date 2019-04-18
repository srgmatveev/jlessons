package ru.hw10.jdbc.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersDataSet1 extends  UsersDataSet{
    @IsOneToOne(name = "Address")
    private AddressDataSet address;

    public UsersDataSet1(long id, String name, int age) {
        super(id, name, age);
    }
}

@TableAlias(name = "User")
class UsersDataSet2 extends  UsersDataSet{
    public UsersDataSet2(long id, String name, int age) {
        super(id, name, age);
    }
}


@TableAlias(name = "")
class UsersDataSet3 extends  UsersDataSet{
    public UsersDataSet3(long id, String name, int age) {
        super(id, name, age);
    }
}

class UsersDataSetTest {
    private  UsersDataSet usersDataSet, usersDataSet1, usersDataSet2, usersDataSet3;
    private String name;
    private int age;
    private long id;

    @BeforeEach
    void setUP(){
        name = "Boris";
        age =35;
        id =1;
        usersDataSet = new UsersDataSet(id,name,age);
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