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

    @Test
    void addAll(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        fromList.add(15);
        fromList.add(22);
        fromList.add(32);
        testList.addAll(fromList);
        assertEquals(testList.size(),3);
        assertIterableEquals(testList,fromList);
    }

    @Test
    void addAllZeroSize(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        testList.addAll(fromList);
        assertEquals(testList.size(),0);
        assertIterableEquals(testList,fromList);
        testList.add(15);
        testList.add(22);
        testList.add(32);
        testList.addAll(fromList);
        assertEquals(testList.size(),3);
    }

    @Test
    void ascendingSort(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        testList.add(32);
        testList.add(22);
        testList.add(15);
        testList.add(18);

        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        fromList.add(15);
        fromList.add(18);
        fromList.add(22);
        fromList.add(32);
        testList.sort((a,b)-> a.compareTo(b));
        assertIterableEquals(testList,fromList);
    }


    @Test
    void descendingSort(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        testList.add(15);
        testList.add(18);
        testList.add(32);
        testList.add(22);


        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        fromList.add(32);
        fromList.add(22);
        fromList.add(18);
        fromList.add(15);


        testList.sort((a,b)-> b.compareTo(a));
        assertIterableEquals(testList,fromList);
    }

    @Test
    void cloneTest(){
        MyOwnArrayList<Integer> testList= new MyOwnArrayList<>();
        testList.add(15);
        testList.add(18);
        testList.add(32);
        testList.add(22);

        MyOwnArrayList<Integer> cloneList= testList.clone();
        assertEquals(cloneList.size(),4);

        testList.set(3,2678);
        assertEquals(testList.get(3),2678);
        assertEquals(cloneList.get(3),22);
    }
}
