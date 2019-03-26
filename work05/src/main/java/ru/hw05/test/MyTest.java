package ru.hw05.test;

import ru.hw05.annotations.After;
import ru.hw05.annotations.Before;
import ru.hw05.annotations.Test;

public class MyTest {
    @Before
    private  void setUp(){
        System.out.println("Before method run");
    }

    @After
    private void tearDown(){
        System.out.println("After method run");
    }

    @Test
    private void firstTest() {
        System.out.println("Test for FirstTest");

    }

    @Test
    private void secondTest() {
        System.out.println("Test for SecondTest");

    }
}
