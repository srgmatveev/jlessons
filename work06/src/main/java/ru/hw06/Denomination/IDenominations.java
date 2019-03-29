package ru.hw06.Denomination;

import java.util.Collection;

public interface IDenominations {
    public Collection<IDenomination> getDenominations();
    public boolean addDenomination(IDenomination denomination);

}
