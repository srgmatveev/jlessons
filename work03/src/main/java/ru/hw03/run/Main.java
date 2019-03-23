package ru.hw03.run;

import java.util.*;

public class Main {
    public static void main(String... args){
        String hello = "Hello";
        Formatter formatter = new Formatter();
        formatter.format("%s",hello);
        System.out.println(formatter);
        Collection<String> eee = new ArrayList<>();
        eee.add("sss");
        eee.add("eee");
        eee.add("www");
        Collections.sort((List<String>) eee, (a,b)->b.compareTo(a));
        System.out.println(eee);

    }
}
