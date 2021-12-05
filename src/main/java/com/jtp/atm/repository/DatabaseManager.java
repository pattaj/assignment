package com.jtp.atm.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jtp.atm.model.Bank;
import com.jtp.atm.util.BankNotes;

@Component
public class DatabaseManager {

    private final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    @Autowired
    BankJdbcRepository bankJdbcRepository;

    private int[] bankValues;

    public int[] getBankAmount(){
        List<Bank> banks = bankJdbcRepository.findAll();
        bankValues = new int[banks.size()];

        int bankAmount[] = new int[banks.size()];
        for (int i = 0; i< banks.size(); i++) {
            bankAmount[i] = banks.get(i).getAmount();
            bankValues[i] = banks.get(i).getValue();
        }
        logger.info("Got data from database : {}", banks);
        return bankAmount;
    }

    public int[] getBankValues(){
        return bankValues;
    }

    public void updateBalanceAmt(int[] updatedBankAmt, int[] bankValues){
        for (int i = 0; i< updatedBankAmt.length; i++) {
            int bankValue = bankValues[i];
            String bankType = BankNotes.getBankType(bankValue);
            bankJdbcRepository.update(new Bank(bankType, bankValue), updatedBankAmt[i]);
        }
    }
}
