package ru.hw06.ru.hw05;

import ru.hw06.Denomination.IDenomination;

public interface ICashCassete<T> {

    public String toString();
    public  ICashCassete addDenominationAmount(IDenomination denomination, T Amount);

}
