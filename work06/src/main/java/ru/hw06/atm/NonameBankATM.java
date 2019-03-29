package ru.hw06.atm;


import ru.hw06.Denomination.IDenomination;

import java.util.Map;

public class NonameBankATM implements ATM {

    @Override
    public Map<IDenomination, Integer> withDraw(int amount) {
        return null;
    }

    @Override
    public int getTotalAmount() {
        return 0;
    }
}
