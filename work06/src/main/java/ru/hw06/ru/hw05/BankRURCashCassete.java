package ru.hw06.ru.hw05;

import ru.hw06.Denomination.Denomination;
import ru.hw06.Denomination.Denominations;
import ru.hw06.Denomination.IDenomination;
import ru.hw06.Denomination.IDenominations;

public class BankRURCashCassete extends CashCassete {

    public BankRURCashCassete(IDenominations denominations) {
        super(denominations);
        Initialize();
    }

    private void Initialize(){
        IDenominations denominations = new Denominations();
        denominations.addDenomination(new Denomination(10,"Десять"));
        denominations.addDenomination(new Denomination(5,"Пять"));
        denominations.addDenomination(new Denomination(50,"Пятьдесят"));
        denominations.addDenomination(new Denomination(100,"Cто"));
        denominations.addDenomination(new Denomination(500,"Пятьсот"));
        denominations.addDenomination(new Denomination(1000,"Тысяча"));
        denominations.addDenomination(new Denomination(5000,"Пять тысяч"));
    }

}
