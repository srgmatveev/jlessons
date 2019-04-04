package ru.hw07.Cassete;


import java.util.Map;

public interface ICashCassete<U,T, W, Y> {

    String toString();
    ICashCassete addDenominationAmount(U denomination, T Amount);
    Y getDenominations();
    void setDenominations(Y y);
    Map<W, T> getDispencer();

    }

