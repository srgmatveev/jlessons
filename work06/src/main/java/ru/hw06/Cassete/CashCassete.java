package ru.hw06.Cassete;

import ru.hw06.Denomination.IDenomination;
import ru.hw06.Denomination.IDenominations;

import java.util.Map;
import java.util.TreeMap;

public class CashCassete implements ICashCassete<IDenomination, Integer, Integer, IDenominations> {

    Map<Integer, Integer> cassete = new TreeMap<>((a, b) -> b - a);
    IDenominations denominations;
    public Map<Integer, Integer> getCassete() {
        return cassete;
    }

    public void setDenominations(IDenominations denominations) {
        this.denominations = denominations;
    }

    public CashCassete() {

    }

    public CashCassete(IDenominations denominations) {
        this.denominations = denominations;
        for (IDenomination denomination : denominations.getDenominations())
            cassete.put(denomination.getNominal(), 0);

    }

    public String toString() {
        return super.toString();
    }

    @Override
    public ICashCassete addDenominationAmount(IDenomination denomination, Integer Amount) {
        int beforeAmount = 0;
        if (cassete.containsKey(denomination.getNominal())) {
            beforeAmount = cassete.get(denomination.getNominal());
        }

        cassete.put(denomination.getNominal(), beforeAmount + Amount);
        return this;
    }

    @Override
    public IDenominations getDenominations() {
        return (IDenominations) this.denominations;
    }
}
