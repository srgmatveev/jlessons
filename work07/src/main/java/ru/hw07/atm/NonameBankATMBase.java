package ru.hw07.atm;


import ru.hw07.Cassete.ICashCassete;
import ru.hw07.Memento.IMemento;
import ru.hw07.Memento.NonameBankMemento;

import java.util.Map;

public class NonameBankATMBase<T> implements ATM<T,Integer, ICashCassete> {
    ICashCassete cassete;
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
    public void setCassete(ICashCassete cassete) {
        this.cassete = cassete;
    }

    @Override
    public void restore(IMemento mememnto) {

    }

    @Override
    public IMemento save() {
        return null;
    }


    void refund(Map<T, Integer> cashAmount){

        if (cashAmount==null) return;
        Map<T, Integer> refundCassete = (Map<T, Integer>) cassete.getDispencer();

        for(Map.Entry<T, Integer> entry : cashAmount.entrySet()) {
           refundCassete.put(entry.getKey(), refundCassete.get(entry.getKey()) + entry.getValue());
        }
        }
}
