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
    Map<IDenomination, Integer> cassete = new TreeMap<>((a, b) -> b.getNominal() - a.getNominal());

    public VipCashCassete() {
    }

    public VipCashCassete(IDenominations denominations) {
        for (IDenomination denomination : denominations.getDenominations())
            cassete.put(denomination, 0);
    }

    @Override
    public ICashCassete addDenominationAmount(IDenomination denomination, Integer Amount) {
        if (cassete.containsKey(denomination))
            cassete.put(denomination, cassete.get(denomination) + Amount);
        else
            cassete.put(denomination, Amount);

        return this;
    }

    @Override
    public IDenominations getDenominations() {
        IDenominations denominations = new Denominations();
        cassete.forEach((k,v)->denominations.addDenomination(k));
        return denominations;
    }

    @Override
    public void setDenominations(IDenominations denominations) {

    for(IDenomination item: denominations.getDenominations()){
        if(!cassete.containsKey(item)) cassete.put(item,0);
    }

    }

    @Override
    public Map<IDenomination, Integer> getCassete() {
        return this.cassete;
    }
}
