package ru.hw07.Department;

import ru.hw07.atm.ATM;

import java.util.Collection;

public abstract class DepartmentBase {
    public abstract long getTotalAmount();
    public abstract boolean addATM(final ATM atm);
    public abstract void restoreAtms();
    public abstract  void saveAtms();


}
