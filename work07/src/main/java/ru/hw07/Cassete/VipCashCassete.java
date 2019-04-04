package ru.hw07.Cassete;

import ru.hw07.Denomination.Denominations;
import ru.hw07.Denomination.IDenomination;
import ru.hw07.Denomination.IDenominations;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class VipCashCassete implements ICashCassete<IDenomination, Integer, IDenomination, IDenominations> {
    Map<IDenomination, Integer> dispencer ;

    public VipCashCassete() {
        this.dispencer = new TreeMap<>((a, b) -> b.getNominal() - a.getNominal());
    }

    public VipCashCassete(IDenominations denominations) {
        for (IDenomination denomination : denominations.getDenominations())
            dispencer.put(denomination, 0);
    }

    public VipCashCassete(ICashCassete cassete) {
     this();
       IDenominations denominations = (IDenominations) cassete.getDenominations();
       Map<IDenomination,Integer> map = cassete.getDispencer();
        for (IDenomination denomination : denominations.getDenominations()){
            dispencer.put(denomination,map.get(denomination));
        }

    }

    @Override
    public ICashCassete addDenominationAmount(IDenomination denomination, Integer Amount) {
        if (dispencer.containsKey(denomination))
            dispencer.put(denomination, dispencer.get(denomination) + Amount);
        else
            dispencer.put(denomination, Amount);

        return this;
    }

    @Override
    public IDenominations getDenominations() {
        IDenominations denominations = new Denominations();
        dispencer.forEach((k,v)->denominations.addDenomination(k));
        return denominations;
    }

    @Override
    public void setDenominations(IDenominations denominations) {

    for(IDenomination item: denominations.getDenominations()){
        if(!dispencer.containsKey(item)) dispencer.put(item,0);
    }

    }

    @Override
    public Map<IDenomination, Integer> getDispencer() {
        return this.dispencer;
    }
}
