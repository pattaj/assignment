package com.jtp.atm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.jtp.atm.model.Account;


@Component
public class AccountRowMapper implements RowMapper {

	
	    @Override
	    public Account mapRow(ResultSet resultSet, int row) throws SQLException {
	        String accName = resultSet.getString("name");
	        int pin = resultSet.getInt("pin");
	        double openingBalance = resultSet.getDouble("opening_balance");
	        double overDraft = resultSet.getDouble("overdraft");
	        int account = resultSet.getInt("account");
	        Account accountDetails = new Account(account, pin, accName, openingBalance, overDraft);
     

	        return accountDetails;
	    }
	}

	

