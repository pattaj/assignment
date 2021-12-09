package com.jtp.atm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jtp.atm.model.Bank;

@Component
public class BankRowMapper implements RowMapper{

    @Override
    public Bank mapRow(ResultSet resultSet, int row) throws SQLException {
        String bankType = resultSet.getString("type");
        int bankValue = resultSet.getInt("value");
        int amount = resultSet.getInt("amount");

        Bank bank = new Bank(bankType, bankValue);
        bank.setAmount(amount);

        return bank;
    }
}
