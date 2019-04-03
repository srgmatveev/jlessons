package ru.hw07.Memento;

import ru.hw07.Cassete.ICashCassete;

import java.util.TreeMap;

public class NonameBankMemento implements IMemento<ICashCassete> {
    ICashCassete cassete;

    @Override
    public IMemento save(ICashCassete cassete) {
        this.cassete = cassete;
        return this;
    }

    @Override
    public ICashCassete restore() {
        return this.cassete;
    }
}
