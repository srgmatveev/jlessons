package ru.hw06.Denomination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Denominations implements IDenominations {

    public Collection<IDenomination> getDenominations() {
        return denominations;
    }

    private Collection<IDenomination> denominations = new ArrayList<>();

    public Denominations() {
    }

    public boolean addDenomination(IDenomination denomination) {

        if (this.denominations != null) {
            for (IDenomination item : denominations) {
                if (item.getNominal() == denomination.getNominal())
                    return false;
            }
        }

        if (denomination == null) return false;
        denominations.add(denomination);
        sorted();
        return true;
    }

    private void sorted(){
        Collections.sort((List<IDenomination>)denominations,(a,b)->b.getNominal()-a.getNominal());
    }
}
