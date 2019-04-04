package ru.hw07.atm;


import ru.hw07.Cassete.CashCassete;
import ru.hw07.Cassete.NonameBankRURCashCassete;
import ru.hw07.Cassete.ICashCassete;
import ru.hw07.Memento.IMemento;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class NonameBankATM extends NonameBankATMBase<Integer> {

    ICashCassete cassete;

    public ICashCassete getCassete() {
        return cassete;
    }

    public NonameBankATM() {
        this.cassete = new NonameBankRURCashCassete();
        super.setCassete(this.cassete);
    }

    @Override
    public Map<Integer, Integer> withDrawAmount(int amount) {
        Optional<Integer> maxValue = maxBanknote(cassete, amount);
        if (!maxValue.isPresent()) return null;
        Optional<Integer> minValue = minBanknote(cassete, amount);
        if (!minValue.isPresent()) return null;
        //System.out.printf("min = %d, max = %d\n", minValue.get(), maxValue.get());
        return getBanknots(minValue, maxValue, amount);
    }

    private Map<Integer, Integer> getBanknots(Optional<Integer> minVal, Optional<Integer> maxVal, int amount) {
        Map<Integer, Integer> cashAmount = new TreeMap<>((a, b) -> b - a);

        int lost = amount;

        for (Object entry : cassete.getDispencer().entrySet()) {
            Map.Entry<Integer, Integer> item = (Map.Entry<Integer, Integer>) entry;
            if (item.getKey() > maxVal.get() || item.getValue() == 0) continue;

            int reminder = (int) Math.floor(lost / item.getKey());
            if (reminder == 0) continue;
            else {
                if (reminder >= item.getValue()) {
                    reminder = item.getValue();
                }
            }
            lost = lost - reminder * item.getKey();
            item.setValue(item.getValue() - reminder);
            cashAmount.put(item.getKey(), reminder);
            if (lost == 0) break;
        }
        if (lost > 0) {
            System.out.println("Недостаточно купюр для выдачи");
            refund(cashAmount);
            return null;
        }
        return cashAmount;
    }




    private Optional<Integer> maxBanknote(ICashCassete cashCassete, int amount) {

        for (Object entry : cassete.getDispencer().entrySet()) {
            Map.Entry<Integer, Integer> item = (Map.Entry<Integer, Integer>) entry;
            if (item.getKey() <= amount && item.getValue() > 0) return Optional.ofNullable(item.getKey());
        }

        System.out.println("Сумма меньше, чем самая маленькая купюра");
        return Optional.empty();
    }

    private Optional<Integer> minBanknote(ICashCassete cashCassete, int amount) {

        int minValue = ((TreeMap<Integer, Integer>) cassete.getDispencer()).lastEntry().getKey();
        if (minValue > amount) {
            System.out.println("Сумма меньше, чем самая маленькая купюра");
            return Optional.empty();
        }
        return Optional.ofNullable(minValue);
    }

    @Override
    public int getTotalAmount() {

        int Amount = 0;
        for (Object item : cassete.getDispencer().entrySet()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) item;
            Amount += entry.getKey() * entry.getValue();
        }
        return Amount;
    }

    @Override
    public void restore(IMemento memento) {
        this.cassete = new CashCassete((ICashCassete) memento.restore());
    }

    @Override
    public IMemento save() {
        ICashCassete cashCassete = new CashCassete(this.cassete);
        memento.save(cashCassete);
        return memento;
    }


}
