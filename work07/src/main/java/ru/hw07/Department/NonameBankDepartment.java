package ru.hw07.Department;

import java.util.*;

import ru.hw07.Memento.IMemento;
import ru.hw07.atm.ATM;

public class NonameBankDepartment extends DepartmentBase {
    Collection<ATM> atms;
    Map<ATM, IMemento> mementos;

    @Override
    public long getTotalAmount() {
        return atms.stream().mapToLong(ATM::getTotalAmount).sum();
    }

    @Override
    public boolean addATM(ATM atm) {
        if (atms.contains(atm)) return true;
        return atms.add(Objects.requireNonNull(atm));
    }

    public NonameBankDepartment() {
        this.atms = new ArrayList<>();
        this.mementos = new HashMap<>();
    }

    public void restoreAtms() {
        atms.stream().forEach(atm -> mementos.get(atm).restore());
    }

    @Override
    public void saveAtms() {
        mementos.clear();
        atms.stream().forEach(atm -> mementos.put(atm,
                atm.save(atm.getCassete())
                )
        );


    }
}

