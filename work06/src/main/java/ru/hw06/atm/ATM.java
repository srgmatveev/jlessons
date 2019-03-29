package ru.hw06.atm;

import ru.hw06.Denomination.IDenomination;

import java.util.Map;
import java.util.SortedMap;

/**
 * Automation Teller Machine interface
 */
public interface ATM {

    /**
     * @param amount
     * @return SortedMap<Integer, Integer> needed amount for withdraw by denominates.
     */
    public Map<IDenomination, Integer> withDraw(int amount);

    /**
     * @return int lost amount in Automation Teller Machine
     */
    public int getTotalAmount();

}
