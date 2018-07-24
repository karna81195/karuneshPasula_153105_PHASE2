package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client 
{
	
	private WalletService walletService;
	Scanner console=new Scanner(System.in);
	public Client()
	{
		walletService=new WalletServiceImpl();
	}
	
	public void menu() throws InvalidInputException,InsufficientBalanceException
	{
		
		System.out.println("Welcome To MyPayment App");
		System.out.println("1)Create an Account");
		System.out.println("2)Show Balance");
		System.out.println("3)Deposit");
		System.out.println("4)Withdraw");
		System.out.println("5)Fund Transfer");
		System.out.println("6)Recent Transactions");
		System.out.println("7)Exit");
		
		int choice=console.nextInt();
		Customer customer;
		List<Transactions> recent;
		switch (choice) {
		case 1:
			
			System.out.print("\tEnter your Name    	 	: ");
			String name=console.next();
			
			System.out.print("\tEnter your Phone Number	 	: ");
			String mobileNo=console.next();
			
			System.out.print("\tEnter initial balance   	: ");
			BigDecimal amount=console.nextBigDecimal();
			
			customer=walletService.createAccount(name, mobileNo, amount);
			System.out.println("Bank Account successfully created");
			break;

		case 2:
			System.out.println("Enter Phone Number");
			String phoneNumber=console.next();
			
			customer=walletService.showBalance(phoneNumber);
			BigDecimal balance=customer.getWallet().getBalance();
			System.out.println("Amount in your account is: "+balance);
			break;
			
		case 3:
			System.out.println("Enter Phone Number");
			String phoneNumber1=console.next();
			System.out.println("Enter Amount");
			BigDecimal amount1=console.nextBigDecimal();
			customer=walletService.depositAmount(phoneNumber1, amount1);
			
			break;
			
		case 4:
			System.out.println("Enter Phone Number");
			String phoneNumber2=console.next();
			System.out.println("Enter Amount");
			BigDecimal amount2=console.nextBigDecimal();
			customer=walletService.withdrawAmount(phoneNumber2, amount2);
			break;
			
		case 5:
			System.out.println("Enter Your Phone Number");
			String sourceMobileNo=console.next();
			System.out.println("Enter Beneficiary Phone Number");
			String targetMobileNo=console.next();
			System.out.print("\tEnter Amount   	: ");
			BigDecimal amount3=console.nextBigDecimal();
			customer=walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount3);
			break;
			
		case 6:
			System.out.println("Last transactions");
			System.out.println("Enter your number to view recent transactions");
			String mobileNo1=console.next();
			recent=walletService.recentTransactions(mobileNo1);
			System.out.println(recent);
			break;
			
		case 7:
			
			System.out.println("Thank you, Please visit again");
			System.exit(0);
			break;
		}
	}
	public static void main(String[] args) 
	{
		//PropertyConfigurator.configure("log4j.properties");
		Client client =new Client();
		while(true)
		{
			try {
				client.menu();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
