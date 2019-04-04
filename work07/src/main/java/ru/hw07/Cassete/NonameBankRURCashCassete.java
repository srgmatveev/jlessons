package ru.hw07.Cassete;

import ru.hw07.Denomination.Denomination;
import ru.hw07.Denomination.Denominations;
import ru.hw07.Denomination.IDenomination;
import ru.hw07.Denomination.IDenominations;

public class NonameBankRURCashCassete extends CashCassete {


    public NonameBankRURCashCassete(IDenominations denominations) {
        super(denominations);
    }

    public NonameBankRURCashCassete() {
        Initialize();

    }

    private void Initialize(){
      IDenominations denominations = addDenominations();
      this.setDenominations(denominations);
        for (IDenomination denomination : denominations.getDenominations())
            dispencer.put(denomination.getNominal(), 0);
    }


    private IDenominations addDenominations(){
        IDenominations denominations = new Denominations();
        denominations.addDenomination(new Denomination(10,"Десять"));
        denominations.addDenomination(new Denomination(5,"Пять"));
        denominations.addDenomination(new Denomination(50,"Пятьдесят"));
        denominations.addDenomination(new Denomination(100,"Cто"));
        denominations.addDenomination(new Denomination(500,"Пятьсот"));
        denominations.addDenomination(new Denomination(1000,"Тысяча"));
        denominations.addDenomination(new Denomination(5000,"Пять тысяч"));
        return denominations;

    }
}
