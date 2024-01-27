package strategies;

import models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoundRobin implements SettlementStrategy{
    @Override
    public List<Transaction> settleup(Map<String, Integer> balanceSheet) {
        List<Transaction> res=new ArrayList<>();
        int n=balanceSheet.size();
        //get the list of users in an arraylist
        ArrayList<String> users=new ArrayList<>(balanceSheet.keySet());

        for(int i=0; i<=n-2; i++){//n-1 transactions
           String currentUser=users.get(i);
           String currentPlusOneUser=users.get(i+1);

           int balanceCurrent=balanceSheet.get(currentUser);
           int balanceCurrentPlusOne=balanceSheet.get(currentPlusOneUser);

           Transaction t=null;
           if(balanceCurrent>0){
               //paid more-> now receive
               t=new Transaction(currentPlusOneUser,currentUser,balanceCurrent);
               balanceCurrentPlusOne=balanceCurrentPlusOne+balanceCurrent;
           }
           else if(balanceCurrent<0){
               //paid less-> now give
               t=new Transaction(currentUser,currentPlusOneUser,-balanceCurrent);
               balanceCurrentPlusOne=balanceCurrentPlusOne+balanceCurrent;
           }
           //update the map
            balanceSheet.put(currentUser,0);
            balanceSheet.put(currentPlusOneUser,balanceCurrentPlusOne);
            if(t!=null) {
                res.add(t);
            }
        }
        return res;
    }
}
