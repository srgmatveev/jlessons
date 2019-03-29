package ru.hw06.Denomination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DenominationsTest {
    private IDenominations denominations;
    private Optional<Method> addDenomination;

    @BeforeEach
    void setUp() {
        denominations = new Denominations();
    }

    @Test
    void addDenomination() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        IDenomination denomination = new Denomination(1000, "Thousand");
        assertTrue((boolean) denominations.addDenomination(denomination));
        IDenomination denomination1 = new Denomination(1000, "One Thousand");
        assertFalse((boolean) denominations.addDenomination(denomination1));
        IDenomination denomination2 = new Denomination(5, "Five");
        assertTrue((boolean) denominations.addDenomination(denomination2));
        IDenomination denomination3 = new Denomination(10, "Ten");
        assertTrue((boolean) denominations.addDenomination(denomination3));
        IDenomination denomination4 = new Denomination(100, "Hundred");
        assertTrue((boolean) denominations.addDenomination(denomination4));

        List<String> nominalNames = new ArrayList<>();
        List<Integer> nominals = new ArrayList<>();

        List<String> testNominalNames = Arrays.asList("Thousand", "Hundred", "Ten", "Five");
        List<Integer> testNominals = Arrays.asList(1000, 100, 10, 5);
        for (IDenomination item : denominations.getDenominations()) {
            nominalNames.add(item.getNominalName());
            nominals.add(item.getNominal());
        }


        assertIterableEquals(nominalNames, testNominalNames);
        assertIterableEquals(nominals, testNominals);
    }


    @Test
    void getDenominations() {
    }
}