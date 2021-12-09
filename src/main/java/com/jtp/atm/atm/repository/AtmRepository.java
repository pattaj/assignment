
  package com.jtp.atm.repository;
  
  import java.util.List;
  
  
  import org.springframework.data.jpa.repository.Query; import
  org.springframework.stereotype.Repository;
  
  import com.jtp.atm.model.Account;
  
  @Repository 
  public interface AtmRepository {
  
	  @Query(value="SELECT * FROM ACCOUNT", nativeQuery = true) 
	  public List<Account> getAllAcct();
	  
	  @Query(value="UPDATE ACCOUNT SET OPENING_BALANCE=? WHERE ACCOUNT_NUMBER=?", nativeQuery = true)
	  public Double updateAccountAmount(Account bankAccount, int amount);
	  
  
  }
 