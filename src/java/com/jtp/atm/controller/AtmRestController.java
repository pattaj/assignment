package com.jtp.atm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jtp.atm.exception.AccountNotExistException;
import com.jtp.atm.exception.InsufficientBalanceException;
import com.jtp.atm.exception.InsufficientNoteException;
import com.jtp.atm.exception.InvalidAmountException;
import com.jtp.atm.exception.InvalidAccountAndPinException;
import com.jtp.atm.model.Account;
import com.jtp.atm.model.ResponseWrapper;
import com.jtp.atm.repository.BankJdbcRepository;
import com.jtp.atm.util.Constant;



@RestController
public class AtmRestController {

	private final Logger logger = LoggerFactory.getLogger(AtmRestController.class);
	
    @Autowired
    AtmController atmController;

    @Autowired
    Account account;

    @Autowired
    BankJdbcRepository bankJdbcRepository;
    
    
    /**
     * 
     * @param amount
     * @param account
     * @param pin
     * @return 
     * @throws InsufficientNoteException
     * @throws InvalidAmountException
     * @throws InsufficientBalanceException
     * @throws InvalidAccountAndPinException
     * @throws AccountNotExistException 
     */
    @SuppressWarnings("unused")
	@GetMapping("/dispense/{account}/{pin}/{amount}")
    public ResponseEntity<ResponseWrapper>  dispense(
            @PathVariable("account") int acct,
            @PathVariable("pin") int pin,
            @PathVariable("amount") int amount)
            throws InsufficientNoteException, InvalidAmountException, 
            InsufficientBalanceException, InvalidAccountAndPinException, AccountNotExistException {
        
    	 	logger.info("inside dispense!");
    	 	
    	 
             try
             {
            	 account = bankJdbcRepository.findAccount(acct);
                	
                	logger.info("account no -- " + account.getAccountNumber());
                     logger.info("pin -- " + account.getPin());
                     logger.info("amount withdrew -- " + amount);
                     logger.info("opening balance -- " + account.getOpeningBalance());
                
            	 
            	 if(account != null) {
             		if (account.getAccountNumber()!= acct) {
             			
             			logger.info("Account exist but account number entered is invalid!");
                 		throw new InvalidAccountAndPinException("Invalid account number");
             		}
             		if(account.getPin() != pin) {
             			
             			logger.info("Account exist, but pin is invalid!");
             			throw new InvalidAccountAndPinException("Invalid pin number");
             		}
             		
             			return atmController.calculateBank(amount,account);
            		 
            	 }
            	 
           
              }catch (EmptyResultDataAccessException e) {
            	 throw new AccountNotExistException("Account doesn't exist or null");
             }
    	 	
    	 	
    	 return null;
		
    	
    }

    /**
     * 
     * @param account
     * @param pin
     * @return
     * @throws InvalidAccountAndPinException
     * @throws AccountNotExistException 
     */
    @GetMapping("/checkBalance/{account}/{pin}")
    public ResponseEntity<ResponseWrapper> checkBalance(
    		@PathVariable("account") int acct,
    		@PathVariable("pin") int pin) throws InvalidAccountAndPinException, AccountNotExistException {
    	
    	Integer bal =0;
    	Double maxWithdrawAmt =0.00;
    	
    	try {
    		
    		account = bankJdbcRepository.findAccount(acct);
        	if(account != null ) {
        	
        		if(account.getAccountNumber()!=acct) {
        			logger.info("Account exist but account number entered is invalid!");
        			throw new InvalidAccountAndPinException("Invalid account number");
        		}
        		if(account.getPin() != pin) {
        		
        			logger.info("Account exist, but pin is invalid!");
        			throw new InvalidAccountAndPinException("Invalid pin number");
        		}
        		
        	}  else {
        		
        		logger.info("Account doesnt exist!");
        		throw new AccountNotExistException("Account doesn't exist or null");
        	}
        	
        	bal = bankJdbcRepository.getAccountBalance(acct);
        	maxWithdrawAmt = account.getOverdraft() + account.getOpeningBalance();
        	
    	 }
        catch (EmptyResultDataAccessException e) {
       	 throw new AccountNotExistException("Account doesn't exist or null");
    		
    	}
    	
    	
        ResponseWrapper responseWrapper = new ResponseWrapper();

        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);
        responseWrapper.setResponseDesc("Current balance is:  " +bal.toString()
		+ " and your maxmium withdrawal amount including overdraft ["
        		+ account.getOverdraft() + "] is ["+ maxWithdrawAmt + "].");
        responseWrapper.setResponseStatus(Constant.SUCCESS);
       

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
       
    	
        return response ;
    
    	
    }

}
