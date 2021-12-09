package com.jtp.atm.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.jtp.atm.model.Account;
import com.jtp.atm.model.ResponseWrapper;
import com.jtp.atm.repository.BankJdbcRepository;
import com.jtp.atm.repository.DatabaseManager;
import com.jtp.atm.util.BankValidator;
import com.jtp.atm.util.Constant;


@Controller
public class AtmController {

    private final Logger logger = LoggerFactory.getLogger(AtmController.class);

    @Autowired
    DatabaseManager databaseManager;

    
    @Autowired
    BankJdbcRepository bankJdbcRepository;
    
    /**
     * 
     * @param amount
     * @param account
     * @return
     */
    public ResponseEntity<ResponseWrapper> calcBankAmount(double amount,Account account){
    	
    	logger.info("inside calcBankAmount");
    	
        String responseCode;
        String responseDesc;
        String responseStatus;
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            int[] bankAmounts = databaseManager.getBankNoteAmount();
            int[] bankValues = databaseManager.getBankValues();

            BankValidator.validateInputAmount(amount, account.getOpeningBalance(), account.getOverdraft());
            BankValidator.verifyBankBalance(amount, bankAmounts, bankValues);

            List<int[]> bankList = findBanks(amount, new int[bankAmounts.length], bankAmounts, bankValues, 0);
            int[] selectedBankList = BankValidator.verifyRemainingBankNotes(bankAmounts, bankList);
            int[] updatedBankList = updatedBankBalance(bankAmounts, selectedBankList);
            databaseManager.updateBalanceAmt(updatedBankList, bankValues);
            
          
             bankJdbcRepository.updateAccountBal(account.getAccountNumber(), account.getOpeningBalance() - amount);
            
            int bal = (int) (account.getOpeningBalance() - amount);
            int maxAmount = (int) (account.getOpeningBalance()+ account.getOverdraft());
            
            logger.info("account No -- " + account.getAccountNumber());
            logger.info("pin -- " + account.getPin());
            logger.info("amount withdrew -- " + amount);
            logger.info("Max amount withdrawal: "+ maxAmount);
            logger.info("overdraft -- " + account.getOverdraft());
            logger.info("balance after withdrawal -- " + bal);
            
            responseWrapper.setResponseBody(selectedBankList, bankValues);
            responseCode = Constant.SUCCESS_CODE;
            responseDesc = Constant.REMAINING_BALANCE + bal;
            responseStatus = Constant.SUCCESS;
        } catch (Exception e){
            logger.error("Got Exception while processing : {}", e.getMessage());
            responseCode = Constant.FAIL_CODE;
            responseDesc = e.getMessage();
            responseStatus = Constant.FAIL;
        }

        responseWrapper.setResponseCode(responseCode);
        responseWrapper.setResponseDesc(responseDesc);
        responseWrapper.setResponseStatus(responseStatus);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }
    
  
    
    /**
     * 
     * @param amount
     * @param currentBankAmt
     * @param balanceBankAmt
     * @param bankValues
     * @param position
     * @return
     */
    public List<int[]> findBanks(Double amount, int[] currentBankAmt, int[] balanceBankAmt, int[] bankValues, int position){
        List<int[]> bankCombination = new ArrayList<>();
        int totalAmt = calCurrentTotalAmt(currentBankAmt, bankValues);
        if (totalAmt < amount) {
            for (int i = position; i < currentBankAmt.length; i++) {
                if (balanceBankAmt[i] > currentBankAmt[i]) {
                    int newCurrentBankAmt[] = currentBankAmt.clone();
                    newCurrentBankAmt[i]++;
                    List<int[]> resultList = findBanks(amount, newCurrentBankAmt, balanceBankAmt, bankValues, i);
                    if (resultList!=null) {
                        bankCombination.addAll(resultList);
                    }
                }
            }
        } else if (totalAmt == amount){
            bankCombination.add(currentBankAmt);
        }
        return bankCombination;
    }

    
    /**
     * 
     * @param balanceBanks
     * @param banks
     * @return
     */
   public int[] updatedBankBalance(int[] balanceBanks, int[] banks){
        for (int i = 0; i < banks.length; i++){
            balanceBanks[i] -= banks[i];
        }
        return balanceBanks;
    }

    /**
     * 
     * @param bankAmounts
     * @param bankValues
     * @return
     */
    public int calCurrentTotalAmt(int[] bankAmounts, int[] bankValues){
        int totalAmt = 0;
        for (int i = 0; i< bankAmounts.length; i++){
            totalAmt += bankAmounts[i]*bankValues[i];
        }
        return totalAmt;
    }
}
