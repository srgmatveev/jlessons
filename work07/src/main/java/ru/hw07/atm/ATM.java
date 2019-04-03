package ru.hw07.atm;

import ru.hw07.Memento.IMemento;

import java.util.Map;

/**
 * Automation Teller Machine interface
 */
public interface ATM<T,U, W,Y> {

    /**
     * @param amount
     * @return SortedMap<T, U> needed amount for withdraw by denominates.
     */
    public Map<T, U> withDrawAmount(int amount);

    /**
     * @return int lost amount in Automation Teller Machine
     */
    public int getTotalAmount();


    public W getCassete();

    Y restore();

    IMemento save(Y y);
}
