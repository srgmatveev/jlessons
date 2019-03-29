package ru.hw06.Denomination;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DenominationTest {
    private IDenomination denomination;

    @BeforeEach
    void setUp() {
        denomination = new Denomination();
    }

    @Test
    void getNominal() {
        denomination.setNominal(1000);
        assertEquals(denomination.getNominal(), 1000);
    }

    @Test
    void setNominal() {

        denomination.setNominal(1000);
        assertEquals(denomination.getNominal(), 1000);
    }

    @Test
    void getNominalName() {
        denomination.setNominalName("Thousand");
        assertEquals(denomination.getNominalName(), "Thousand");
    }

    @Test
    void setNominalName() {
        denomination.setNominalName("Thousand");
        assertEquals(denomination.getNominalName(), "Thousand");

    }
}