package ru.hw07;

import ru.hw07.Denomination.IDenomination;
import ru.hw07.Denomination.IDenominations;
import ru.hw07.Department.DepartmentBase;
import ru.hw07.Department.NonameBankDepartment;
import ru.hw07.atm.ATM;
import ru.hw07.atm.NonameBankATM;
import ru.hw07.Cassete.ICashCassete;
import ru.hw07.atm.NonameBankVipATM;

import java.util.Map;

public class Main {

    public static void main(String... args) {

        System.out.println("Simple ATM");
        ATM nonameBank = new NonameBankATM();
        ICashCassete nonameBankCashCassete = (ICashCassete) nonameBank.getCassete();

        IDenominations denominations = (IDenominations) nonameBankCashCassete.getDenominations();
        for (IDenomination denomination : denominations.getDenominations()) {
            nonameBankCashCassete.addDenominationAmount(denomination, 100);
        }

        printTotalAmount(nonameBank);

        System.out.println("VIP ATM");

        ATM nonameBankVip = new NonameBankVipATM();
        ICashCassete nonameBankVipCashCassete = (ICashCassete) nonameBankVip.getCassete();
        IDenominations denominationsVip = (IDenominations) nonameBankVipCashCassete.getDenominations();
        for (IDenomination denomination : denominationsVip.getDenominations()) {
            nonameBankVipCashCassete.addDenominationAmount(denomination,100);
        }

        printTotalAmount(nonameBank);

        DepartmentBase nonameBankDepartment = new NonameBankDepartment();
        nonameBankDepartment.addATM(nonameBank);
        nonameBankDepartment.addATM(nonameBankVip);
        printTotalDepartmentAmount(nonameBankDepartment);

        nonameBankDepartment.saveAtms();

        Map<Integer,Integer>  amount = nonameBank.withDrawAmount(6500);
        printOut(amount);
        printTotalAmount(nonameBank);
        printTotalDepartmentAmount(nonameBankDepartment);
        nonameBankDepartment.restoreAtms();
        printTotalDepartmentAmount(nonameBankDepartment);


        amount = nonameBank.withDrawAmount(6700);
        printTotalDepartmentAmount(nonameBankDepartment);
        nonameBankDepartment.restoreAtms();
        printTotalDepartmentAmount(nonameBankDepartment);
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

    private static void printTotalDepartmentAmount(DepartmentBase department){
        System.out.printf("Department Total Amount = %s RUR.\n",department.getTotalAmount());
    }

}
