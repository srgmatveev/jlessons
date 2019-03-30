package ru.hw06;

import ru.hw06.Denomination.IDenomination;
import ru.hw06.Denomination.IDenominations;
import ru.hw06.atm.ATM;
import ru.hw06.atm.NonameBankATM;
import ru.hw06.Cassete.ICashCassete;
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
    }


    private static void printOut(Map<Integer,Integer> amount){
        if (amount==null) return;
        for(Map.Entry<Integer,Integer> entry : amount.entrySet()){
            System.out.printf("Banknote = %d, Quantity = %d\n",entry.getKey(),entry.getValue());
        }
    }


   private static void printTotalAmount(ATM nonameBank){
       System.out.printf("Total Amount = %s RUR.\n",nonameBank.getTotalAmount());
   }
}
