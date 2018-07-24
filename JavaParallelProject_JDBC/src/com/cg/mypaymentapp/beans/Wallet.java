package com.cg.mypaymentapp.beans;
import java.math.BigDecimal;

public class Wallet {
	
	private int wallet_id;
private BigDecimal balance;



public Wallet(int wallet_id, BigDecimal balance) {
	super();
	this.wallet_id = wallet_id;
	this.balance = balance;
}

public Wallet(BigDecimal balance) {
	super();
	this.balance = balance;
}

public Wallet() {
	// TODO Auto-generated constructor stub
}

public int getWallet_id() {
	return wallet_id;
}

public void setWallet_id(int wallet_id) {
	this.wallet_id = wallet_id;
}

public BigDecimal getBalance() {
	return balance;
}

public void setBalance(BigDecimal balance) {
	this.balance = balance;
}

@Override
	public String toString() {
	return ", balance="+balance;
}

}
