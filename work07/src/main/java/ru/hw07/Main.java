package ru.hw07;

import ru.hw07.Denomination.IDenomination;
import ru.hw07.Denomination.IDenominations;
import ru.hw07.atm.ATM;
import ru.hw07.atm.NonameBankATM;
import ru.hw07.Cassete.ICashCassete;
import ru.hw07.atm.NonameBankVipATM;

import java.util.Locale;
import java.util.Map;

public class Main {

    public static void main(String... args) {
        ATM nonameBank = new NonameBankATM();
        ICashCassete nonameBankCashCassete = (ICashCassete) nonameBank.getCassete();

        IDenominations denominations = (IDenominations) nonameBankCashCassete.getDenominations();
        for (IDenomination denomination : denominations.getDenominations()) {
            nonameBankCashCassete.addDenominationAmount(denomination, 100);
        }

        printTotalAmount(nonameBank);

        Map<Integer,Integer> amount = nonameBank.withDrawAmount(6500);

        printOut(amount);
        printTotalAmount(nonameBank);

        amount = nonameBank.withDrawAmount(4);
        printOut(amount);
        printTotalAmount(nonameBank);


        amount = nonameBank.withDrawAmount(6);
        printOut(amount);
        printTotalAmount(nonameBank);

        System.out.println("VIP ATM");
        ATM nonameBankVip = new NonameBankVipATM();
        ICashCassete nonameBankVipCashCassete = (ICashCassete) nonameBankVip.getCassete();
        IDenominations denominationsVip = (IDenominations) nonameBankVipCashCassete.getDenominations();
        for (IDenomination denomination : denominationsVip.getDenominations()) {
            nonameBankVipCashCassete.addDenominationAmount(denomination,100);
        }

        printTotalAmount(nonameBankVip);

        Map<IDenomination,Integer> amountVip = nonameBankVip.withDrawAmount(6500);
        printOut(amountVip);

        amountVip = nonameBankVip.withDrawAmount(4);
        printOut(amountVip);
        printTotalAmount(nonameBankVip);


        amountVip = nonameBankVip.withDrawAmount(6);
        printOut(amountVip);
        printTotalAmount(nonameBankVip);
    }


    private static <T> void  printOut(Map<T,Integer> amount){
        if (amount==null) return;
        for(Map.Entry<T,Integer> entry : amount.entrySet()){
            System.out.printf("Banknote = %s, Quantity = %d\n",entry.getKey().toString(),entry.getValue());
        }
    }


   private static void printTotalAmount(ATM nonameBank){
       System.out.printf("Total Amount = %s RUR.\n",nonameBank.getTotalAmount());
   }
}
