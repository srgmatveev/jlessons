package ru.hw05.main;

import ru.hw05.framework.TestFramework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class Main {
    public static void main(String... args) throws Exception {

        Collection<String> packagesList = addPackageNames(args);
        TestFramework.run(packagesList.toArray(new String[packagesList.size()]));
    }


    static Collection<String> addPackageNames(String... items) {

        Collection<String> packagesList = new TreeSet<String>();

        final String DEFAULT_TEST_PACKAGE_NAME = "ru.hw05.test";

        if (items.length == 0)
            packagesList.add(DEFAULT_TEST_PACKAGE_NAME);
        else {
            for (String item : items)
                packagesList.add(item);
        }

        return packagesList;
    }
}
