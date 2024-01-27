import models.ExpensePaidBy;
import models.ExpenseSharedBy;
import models.Transaction;
import strategies.GreedySettlement;
import strategies.RoundRobin;
import strategies.SettlementStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        //these data(paidBy, sharedBy) will come from repositories
        //real input is group name
        //we will get the following two lists by navigating following repositories
        //Group, GroupExpense, Expense, ExpensePaidBy, ExpenseSharedBy
        List<ExpensePaidBy> paidBy=new ArrayList<>();
        paidBy.add(new ExpensePaidBy("A",5000));
        paidBy.add(new ExpensePaidBy("B",5000));
        paidBy.add(new ExpensePaidBy("A",5000));
        paidBy.add(new ExpensePaidBy("B",5000));
        paidBy.add(new ExpensePaidBy("C",5000));

        List<ExpenseSharedBy> sharedBy=new ArrayList<>();
        sharedBy.add(new ExpenseSharedBy("A",2000));
        sharedBy.add(new ExpenseSharedBy("B",2000));
        sharedBy.add(new ExpenseSharedBy("C",3000));
        sharedBy.add(new ExpenseSharedBy("D",3000));
        sharedBy.add(new ExpenseSharedBy("A",4000));
        sharedBy.add(new ExpenseSharedBy("B",4000));
        sharedBy.add(new ExpenseSharedBy("C",4000));
        sharedBy.add(new ExpenseSharedBy("D",3000));

        //settle(paidBy, sharedBy,new RoundRobin());
        settle(paidBy, sharedBy,new GreedySettlement());
    }
    public static void settle(List<ExpensePaidBy> paidBy, List<ExpenseSharedBy> sharedBy, SettlementStrategy settlementStrategy){
      //pass two lists and also pass settlement strategy
      //create balanceSheet using paidBy,sharedBy list
      HashMap<String, Integer> balanceSheet =new HashMap<>();
      for(ExpensePaidBy payer: paidBy){
          if(balanceSheet.containsKey(payer.getName())){
              balanceSheet.put(payer.getName(),balanceSheet.get(payer.getName())+payer.getContri());
          }
          else{
              balanceSheet.put(payer.getName(),payer.getContri());
          }
      }
        for(ExpenseSharedBy sharer: sharedBy){
            if(balanceSheet.containsKey(sharer.getName())){
                balanceSheet.put(sharer.getName(),balanceSheet.get(sharer.getName())-sharer.getContri());
            }
            else{
                balanceSheet.put(sharer.getName(),-sharer.getContri());
            }
        }

        //invoke settlement strategy
        List<Transaction> transactions= settlementStrategy.settleup(balanceSheet);

        //print
        System.out.println(transactions);
    }
}
