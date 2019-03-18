package ru.hw02.memory;

import ru.hw02.jagent.ObjectSizeFetcher;

public class ObjectMeter {
    public static void main(String [] args) {
        System.out.println(ObjectSizeFetcher.getObjectSize(new ObjectMeter()));
    }
}
