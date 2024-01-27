package strategies;

import models.Transaction;

import java.util.List;
import java.util.Map;

public interface SettlementStrategy {

    //input is hashmap(a balancesheet) and output is list of transactions.
    List<Transaction> settleup(Map<String,Integer> balanceSheet);
}
