package ru.hw07.atm;

import ru.hw07.Cassete.ICashCassete;
import ru.hw07.Memento.IMemento;
import ru.hw07.Memento.NonameBankMemento;

import java.util.Map;

public class NonameBankATMBase<T> implements ATM<T,Integer, ICashCassete,ICashCassete> {
    IMemento memento = new NonameBankMemento();
    @Override
    public Map withDrawAmount(int amount) {
        return null;
    }

    @Override
    public int getTotalAmount() {
        return 0;
    }

    @Override
    public ICashCassete getCassete() {
        return null;
    }

    @Override
    public ICashCassete restore() {
        return (ICashCassete) memento.restore();
    }

    @Override
    public IMemento save(ICashCassete cassete) {
        memento.save(cassete);
        return memento;
    }

    void refund(Map<T, Integer> cashAmount, ICashCassete cassete){

        if (cashAmount==null) return;
        Map<T, Integer> refundCassete = (Map<T, Integer>) cassete.getCassete();

        for(Map.Entry<T, Integer> entry : cashAmount.entrySet()) {
           refundCassete.put(entry.getKey(), refundCassete.get(entry.getKey()) + entry.getValue());
        }
        }
}
