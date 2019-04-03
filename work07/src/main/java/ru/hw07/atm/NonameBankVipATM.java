package ru.hw07.atm;

import ru.hw07.Cassete.CashCassete;
import ru.hw07.Cassete.ICashCassete;
import ru.hw07.Cassete.NonameBankRURVipCashCassete;
import ru.hw07.Denomination.IDenomination;
import ru.hw07.Memento.IMemento;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

public class NonameBankVipATM extends NonameBankATMBase<IDenomination> {
    ICashCassete cassete;

    public NonameBankVipATM() {
        this.cassete = new NonameBankRURVipCashCassete();
    }

    @Override
    public Map<IDenomination, Integer> withDrawAmount(int amount) {
        Optional<Integer> maxValue = maxBanknote(cassete, amount);
        if (!maxValue.isPresent()) return null;
        Optional<Integer> minValue = minBanknote(cassete, amount);
        if (!minValue.isPresent()) return null;
        System.out.printf("min = %d, max = %d\n", minValue.get(), maxValue.get());
        return getBanknots(minValue, maxValue, amount);
    }


    private Optional<Integer> maxBanknote(ICashCassete cashCassete, int amount) {

        for (Object entry : cassete.getCassete().entrySet()) {
            Map.Entry<IDenomination, Integer> item = (Map.Entry<IDenomination, Integer>) entry;
            if (item.getKey().getNominal() <= amount && item.getValue() > 0) return Optional.ofNullable(item.getKey().getNominal());
        }

        System.out.println("Сумма меньше, чем самая маленькая купюра");
        return Optional.empty();
    }

    private Optional<Integer> minBanknote(ICashCassete cashCassete, int amount) {

        int minValue = ((TreeMap<IDenomination, Integer>) cassete.getCassete()).lastEntry().getKey().getNominal();
        if (minValue > amount) {
            System.out.println("Сумма меньше, чем самая маленькая купюра");
            return Optional.empty();
        }
        return Optional.ofNullable(minValue);
    }


    private Map<IDenomination, Integer> getBanknots(Optional<Integer> minVal, Optional<Integer> maxVal, int amount) {
        Map<IDenomination, Integer> cashAmount = new TreeMap<>((a, b) -> b.getNominal() - a.getNominal());

        int lost = amount;

        for (Object entry : cassete.getCassete().entrySet()) {
            Map.Entry<IDenomination, Integer> item = (Map.Entry<IDenomination, Integer>) entry;
            if (item.getKey().getNominal() > maxVal.get() || item.getValue() == 0) continue;

            int reminder = (int) Math.floor(lost / item.getKey().getNominal());
            if (reminder == 0) continue;
            else {
                if (reminder >= item.getValue()) {
                    reminder = item.getValue();
                }
            }
            lost = lost - reminder * item.getKey().getNominal();
            item.setValue(item.getValue() - reminder);
            cashAmount.put(item.getKey(), reminder);
            if (lost == 0) break;
        }
        if (lost > 0) {
            System.out.println("Недостаточно купюр для выдачи");
            refund(cashAmount, cassete);
            return null;
        }
        return cashAmount;
    }


    @Override
    public int getTotalAmount() {
        Stream<Map.Entry<IDenomination, Integer>> stream = cassete.getCassete().entrySet().stream();
        return stream.map(x->x.getValue()*x.getKey().getNominal()).reduce(0,(x,y)->x+y);
    }

    @Override
    public ICashCassete getCassete() {
        return this.cassete;
    }


}
