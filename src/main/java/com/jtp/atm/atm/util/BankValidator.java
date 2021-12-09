package com.jtp.atm.util;

import java.util.List;

import com.jtp.atm.exception.InsufficientBalanceException;
import com.jtp.atm.exception.InsufficientNoteException;
import com.jtp.atm.exception.InvalidAmountException;

public class BankValidator {

	/**
	 * 
	 * @param amount
	 * @param openingBal
	 * @param overdraft
	 * @return
	 * @throws InvalidAmountException
	 * @throws InsufficientBalanceException
	 */
    public static boolean validateInputAmount(Double amount, Double openingBal, Double overdraft) 
    		throws InvalidAmountException, InsufficientBalanceException {
        
    	Double totalRemaining = openingBal + overdraft;
    	if (amount > totalRemaining){
            throw new InsufficientBalanceException("Balance is less than amount entered");
        } else if(amount < 100) {
        	throw new InvalidAmountException("Amount is less than Minimum withdraw limit amount");
        }
        return true;
    }

    /**
     * 
     * @param amount
     * @param bankAmount
     * @param bankValues
     * @return
     * @throws InsufficientBalanceException
     */
    public static boolean verifyBankBalance(Double amount, int[] bankAmount, int[] bankValues) throws InsufficientBalanceException {
        int totalAmt = 0;
        for(int i = 0; i < bankAmount.length; i++){
            totalAmt += bankValues[i]*bankAmount[i];
        }
        if (totalAmt < amount){
            throw new InsufficientBalanceException("Remaining balance less than dispensed amount");
        }
        return true;
    }

    /**
     * 
     * @param bankLeft
     * @param totalBankNeeded
     * @return
     * @throws InsufficientNoteException
     */
    public static int[] verifyRemainingBankNotes(int[] bankLeft, List<int[]> totalBankNeeded) throws InsufficientNoteException {
       
    	for (int[] bankNeeded:totalBankNeeded) {
            int[] resultBankSet = verifyRemainingBankNotes(bankLeft, bankNeeded);
            if (resultBankSet != null) {
                return resultBankSet;
            }
        }
        throw new InsufficientNoteException("Insufficient note number. Try dispensing a different amount.");
    }

    /**
     * 
     * @param bankLeft
     * @param bankNeeded
     * @return
     */
    private static int[] verifyRemainingBankNotes(int[] bankLeft, int[] bankNeeded){
        for (int i = 0; i < bankLeft.length; i++) {
            if(bankLeft[i] < bankNeeded[i] && bankNeeded[i] > 0){
                return null;
            }
        }
        return bankNeeded;
    }
}
