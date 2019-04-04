package ru.hw07.Cassete;

import ru.hw07.Denomination.IDenomination;
import ru.hw07.Denomination.IDenominations;

import java.util.Map;
import java.util.TreeMap;

public class CashCassete implements ICashCassete<IDenomination, Integer, Integer, IDenominations> {

    Map<Integer, Integer> dispencer;
    IDenominations denominations;
    public Map<Integer, Integer> getDispencer() {
        return dispencer;
    }

    public void setDenominations(IDenominations denominations) {
        this.denominations = denominations;
    }

    public CashCassete(){this.dispencer = new TreeMap<>((a, b) -> b - a);}
    public CashCassete(ICashCassete cassete) {
        this();
        this.Init((IDenominations) cassete.getDenominations(), cassete.getDispencer());
        }

    public CashCassete(IDenominations denominations) {
        Map<Integer,Integer> map =null;
        this.Init(denominations, map);

    }

    void Init(IDenominations denominations, Map<Integer, Integer> map){
        this.denominations = denominations;
        for (IDenomination denomination : denominations.getDenominations())
        { Integer val = 0;
            if(map != null){
              val = map.get(denomination.getNominal());
            }

            dispencer.put(denomination.getNominal(), val);
        }
    }

    public String toString() {
        return super.toString();
    }

    @Override
    public ICashCassete addDenominationAmount(IDenomination denomination, Integer Amount) {
        int beforeAmount = 0;
        if (dispencer.containsKey(denomination.getNominal())) {
            beforeAmount = dispencer.get(denomination.getNominal());
        }

        dispencer.put(denomination.getNominal(), beforeAmount + Amount);
        return this;
    }

    @Override
    public IDenominations getDenominations() {
        return (IDenominations) this.denominations;
    }
}
