package ru.hw03.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyOwnArrayListTest {

    @Test
    void add() {
    MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
    testList.add(15);
    assertEquals(testList.size(),1);
    testList.add(22);
    assertEquals(testList.size(),2);
    }

    @Test
    void iterable(){

        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        for (int item:testList){

        }

        testList.add(15);
        testList.add(22);
        testList.add(32);
        for (int item:testList){

        }
    }

    @Test
    void get(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        testList.add(15);
        testList.add(22);
        testList.add(32);
        assertEquals(testList.get(0),15);
        assertEquals(testList.get(1),22);
        assertEquals(testList.get(2),32);

    }


    @Test
    void set(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        testList.add(15);
        testList.add(22);
        testList.add(32);
        testList.set(0,34);
        assertEquals(testList.get(0),34);
        assertEquals(testList.get(1),22);
        assertEquals(testList.get(2),32);

    }

}
