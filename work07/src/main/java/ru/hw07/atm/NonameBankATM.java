package ru.hw07.atm;


import com.sun.javafx.collections.MappingChange;
import org.omg.PortableInterceptor.INACTIVE;
import ru.hw07.Cassete.NonameBankRURCashCassete;
import ru.hw07.Cassete.ICashCassete;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class NonameBankATM implements ATM<Integer, Integer, ICashCassete> {

    ICashCassete cassete;

    public ICashCassete getCassete() {
        return cassete;
    }

    public NonameBankATM() {
        this.cassete = new NonameBankRURCashCassete();
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

        for (Object entry : cassete.getCassete().entrySet()) {
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


    private void refund(Map<Integer, Integer> cashAmount){
        if (cashAmount==null) return;
        Map<Integer, Integer> refundCassete = (Map<Integer, Integer>) cassete.getCassete();

        for(Map.Entry<Integer, Integer> entry : cashAmount.entrySet()){
           refundCassete.put(entry.getKey(),refundCassete.get(entry.getKey())+entry.getValue());
        }
    }

    private Optional<Integer> maxBanknote(ICashCassete cashCassete, int amount) {

        for (Object entry : cassete.getCassete().entrySet()) {
            Map.Entry<Integer, Integer> item = (Map.Entry<Integer, Integer>) entry;
            if (item.getKey() <= amount && item.getValue() > 0) return Optional.ofNullable(item.getKey());
        }

        System.out.println("Сумма меньше, чем самая маленькая купюра");
        return Optional.empty();
    }

    private Optional<Integer> minBanknote(ICashCassete cashCassete, int amount) {

        int minValue = ((TreeMap<Integer, Integer>) cassete.getCassete()).lastEntry().getKey();
        if (minValue > amount) {
            System.out.println("Сумма меньше, чем самая маленькая купюра");
            return Optional.empty();
        }
        return Optional.ofNullable(minValue);
    }

    @Override
    public int getTotalAmount() {

        int Amount = 0;
        for (Object item : cassete.getCassete().entrySet()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) item;
            Amount += entry.getKey() * entry.getValue();
        }
        return Amount;
    }
}
