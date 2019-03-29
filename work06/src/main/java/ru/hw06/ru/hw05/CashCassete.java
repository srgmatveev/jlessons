package ru.hw06.ru.hw05;

import ru.hw06.Denomination.Denominations;
import ru.hw06.Denomination.IDenomination;
import ru.hw06.Denomination.IDenominations;

import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

public class CashCassete implements ICashCassete<Integer> {
    IDenominations denominations;
    Map<IDenomination, Integer> cassete = new HashMap<>();


    public  CashCassete(IDenominations denominations){
        this.denominations = denominations;
    }

    public boolean addDenomination(IDenomination denomination){
        return denominations.addDenomination(denomination);
    }

    public String toString() {
        return super.toString();
    }

    @Override
    public ICashCassete addDenominationAmount(IDenomination denomination, Integer Amount) {
        addDenomination(denomination);
        cassete.put(denomination, Amount);
        return this;
    }
}
