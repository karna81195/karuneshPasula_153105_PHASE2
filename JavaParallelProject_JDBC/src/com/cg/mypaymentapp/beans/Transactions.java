package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;
import java.util.Date;


public class Transactions 
{
	String phoneNumber;
	Date tdate;
	String transactionType;
	BigDecimal amount;
	
	
	
	public Transactions() {
		super();
		
	}
	@Override
	public String toString() {
		return "Transactions [phoneNumber=" + phoneNumber + ", date=" + tdate + ", transactionType=" + transactionType
				+ ", amount=" + amount + "]";
	}

	public Transactions(String phoneNumber, String transactionType, Date date, BigDecimal amount) {
		super();
		this.phoneNumber = phoneNumber;
		this.tdate = date;
		this.transactionType = transactionType;
		this.amount = amount;
	}




	public Transactions(String phoneNumber,  String transactionType, BigDecimal amount) {
		super();
		this.phoneNumber = phoneNumber;
	
		this.transactionType = transactionType;
		this.amount = amount;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDate() {
		return tdate;
	}

	public void setDate(Date date) {
		this.tdate = date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
