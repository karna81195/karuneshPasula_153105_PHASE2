
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService
{

	private WalletRepo repo= new WalletRepoImpl();
	Customer customer;
	Map<String, Customer> data;
	
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() 
	{

	}

	public WalletServiceImpl(Map<String, Customer> data) {
		this.data=data;
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) 
	{
		if(name!=null&&!isValidMobile(mobileNo)&&!isValidAmount(amount))
			throw new InvalidInputException("Something went wrong");
		else
		{
			Wallet wallet=new Wallet(amount);
			customer=new Customer();
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
			repo.save(customer);
		}
		return customer;			
	}

	
	
	public Customer showBalance(String mobileNo) 
	{
		if(!isValidMobile(mobileNo))
			throw new InvalidInputException("Incorrect mobile number");
		else
		{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			
			throw new InvalidInputException("Invalid mobile no ");
		}
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) 
	{
		if(!isValidMobile(mobileNo)&&!isValidAmount(amount))
			throw new InvalidInputException("Something went wrong");
		else
		{
			Customer customer=repo.findOne(mobileNo);
			if(customer!=null)
			{
			Wallet wal=customer.getWallet();
			wal.setBalance(wal.getBalance().add(amount));
			repo.update(customer,"deposit");
			return customer;
			}
			else
				throw new InvalidInputException("Account with mobile no not found");
		}
		
	}
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) 
	{
		if(!isValidMobile(mobileNo)&&!isValidAmount(amount))
			throw new InvalidInputException("Something went wrong");
		
		else
		{
			Customer customer=repo.findOne(mobileNo);
			if(customer!=null)
			{
			Wallet wal=customer.getWallet();
			if(wal.getBalance().compareTo(amount)>0)
			{
			wal.setBalance(wal.getBalance().subtract(amount));
			repo.update(customer, "Withdraw");
			return customer;
			}
			else 
			{
				throw new InsufficientBalanceException("Insuff bal");
			}
			}
			else
				throw new InvalidInputException("Account with mobile no not found");
		}
	}
	
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) 
	{
		if(!isValidMobile(sourceMobileNo)&&!isValidMobile(targetMobileNo)&&!isValidAmount(amount))
			throw new InvalidInputException("Something went wrong");
		else
		{
			Customer customer1=repo.findOne(sourceMobileNo);
			Customer customer2=repo.findOne(targetMobileNo);
			if(customer1!=null&&customer2!=null)
			{
				Wallet wal1=customer1.getWallet();
				Wallet wal2=customer2.getWallet();
				BigDecimal balance1=wal1.getBalance();
				BigDecimal balance2=wal2.getBalance();
				if(balance1.compareTo(amount)>0)
				{
					wal1.setBalance(balance1.subtract(amount));
					repo.update(customer1,"fund transfered");
					wal2.setBalance(balance2.add(amount));
					repo.update(customer2,"fund received");
					return customer1;
				}
				else
				{
					throw new InsufficientBalanceException("Insuff bal");
				}
			}
			else
			{
				throw new InvalidInputException("Account with mobile no not found");
				
			}
			
		}
		
	}

	
	
	@Override
	public List<Transactions> recentTransactions(String mobileNo) {
		if(!isValidMobile(mobileNo))
			throw new InvalidInputException("Something went wrong");
		
		else
			
		{
			List<Transactions> recent=repo.recentTransactions(mobileNo);
			if(recent!=null)
				return recent;
			else
			{
				throw new InvalidInputException("no recent transactions");
			}
		} 
	}
	
	//validation
	
	private boolean isValidAmount(BigDecimal amount)
	{
		BigDecimal num=new BigDecimal("0");
		if(amount.compareTo(num)>0)
			return true;
		else 
			return false;
	}
	private boolean isValidMobile(String mobileNo) 
	{
		if(String.valueOf(mobileNo).matches("[1-9][0-9]{9}")) 
			return true;		
		else 
			return false;
	}

	

	
}
