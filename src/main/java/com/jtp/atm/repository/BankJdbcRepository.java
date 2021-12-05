package com.jtp.atm.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jtp.atm.model.Account;
import com.jtp.atm.model.Bank;

@Repository
public class BankJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BankRowMapper bankRowMapper;
   
    @Autowired
    AccountRowMapper accountRowMapper;

    @SuppressWarnings("unchecked")
	public List<Bank> findAll(){
        return jdbcTemplate.query("select * from bank", bankRowMapper);
    }

    @SuppressWarnings("deprecation")
	public Bank findByType(String type){
        return jdbcTemplate.queryForObject("select * from bank where type=?",
                new Object[] { type },
                new BeanPropertyRowMapper<>(Bank.class));
    }

    public int update(Bank bank, int amount) {
        return jdbcTemplate.update("update bank set amount = ? where type = ?",
                amount, bank.getType());
    }
    
    public int updateAccountBal(Integer account, double d) {
    	return jdbcTemplate.update("update account set opening_balance  = ? where account_number = ?",
                d,account);
    }
    
    @SuppressWarnings("deprecation")
	public Integer getAccountBalance(Integer account) {
    	return jdbcTemplate.queryForObject("select opening_balance from account where account_number = ? ",
    			new Object[] { account },
                Integer.class);
    }
    
    @SuppressWarnings("deprecation")
	public Account findAccount(Integer account){
        return jdbcTemplate.queryForObject("select * from account where account_number = ?",new Object[] { account }, new BeanPropertyRowMapper<>(Account.class));
    }

    
    
}
