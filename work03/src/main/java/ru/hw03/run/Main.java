package ru.hw03.run;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

public class Main {
    public static void main(String... args){
        String hello = "Hello";
        Formatter formatter = new Formatter();
        formatter.format("%s",hello);
        System.out.println(formatter);
        List<String> eee = new ArrayList<>();
        eee.add("sss");
        eee.add("eee");
        eee.add("www");
        eee.sort((a, b) -> a.compareTo(b));
        System.out.println(eee);

    }
}
