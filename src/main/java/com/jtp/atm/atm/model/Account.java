package com.jtp.atm.model;

import org.springframework.stereotype.Component;

@Component
public class Account {

	private Integer accountNumber;
	private Integer pin;
	private String name;
	private Double openingBalance;
	private Double overdraft;

	
	public Account() {
		
	}
	
	public Account(Integer accountNumber, Integer pin, String name, Double openingBalance, Double overdraft) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.name = name;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public Double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}

}
