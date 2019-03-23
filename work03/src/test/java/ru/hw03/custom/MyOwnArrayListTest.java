package ru.hw03.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MyOwnArrayListTest {

    private MyOwnArrayList<Integer> testList;

    @BeforeEach
    void setUp() {
        testList = new MyOwnArrayList<>();
    }

    @Test
    void add() {
    testList.add(15);
    assertEquals(testList.size(),1);
    testList.add(22);
    assertEquals(testList.size(),2);
    }

    @Test
    void iterable(){
        int i=0;
        for (int item:testList){
            i++;
        }
        assertEquals(i,0);
        testList.add(15);
        testList.add(22);
        testList.add(32);
        for (int item:testList){
            i++;
        }
        assertEquals(i,3);
    }

    @Test
    void get(){
        testList.add(15);
        testList.add(22);
        testList.add(32);
        assertEquals(testList.get(0),15);
        assertEquals(testList.get(1),22);
        assertEquals(testList.get(2),32);

    }


    @Test
    void set(){
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
        testList.add(32);
        testList.add(22);
        testList.add(15);
        testList.add(18);

        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        fromList.add(15);
        fromList.add(18);
        fromList.add(22);
        fromList.add(32);
        testList.sort(Comparator.naturalOrder());
        assertIterableEquals(testList,fromList);
    }


    @Test
    void descendingSort(){
        testList.add(15);
        testList.add(18);
        testList.add(32);
        testList.add(22);


        MyOwnArrayList<Integer> fromList= new MyOwnArrayList<>();
        fromList.add(32);
        fromList.add(22);
        fromList.add(18);
        fromList.add(15);


        testList.sort(Comparator.reverseOrder());
        assertIterableEquals(testList,fromList);
    }

    @Test
    void cloneTest(){
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
