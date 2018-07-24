package com.cg.mypaymentapp.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.util.DBUtil;

public class WalletRepoImpl implements WalletRepo{

	private Map<String, Customer> data; 
	
	 List<Transactions> recent= new ArrayList<Transactions>();
	 
	
	public WalletRepoImpl()
	{
		data=new HashMap<String, Customer>();
	}
	
	public boolean save(Customer customer) 
	{		
		
		try(Connection con= DBUtil.getConnection())
		{
			
			PreparedStatement pstm= con.prepareStatement("insert into customers values(?,?,?)");
			pstm.setString(1, customer.getMobileNo());
			pstm.setString(2, customer.getName());
			pstm.setBigDecimal(3, customer.getWallet().getBalance());
			pstm.execute();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return true;
		
	}

	public Customer findOne(String mobileNo) 
	{
		Customer customer=null;
		try(Connection con=DBUtil.getConnection())
		{
			PreparedStatement pstm=con.prepareStatement("select * from customers where mobileNo=?");
			pstm.setString(1, mobileNo);
			
			ResultSet res=pstm.executeQuery();
			
			if(res.next()==false)
			{
				throw new SQLDataException("sql exception");
			}
			 customer= new Customer();
			 Wallet wallet= new Wallet();
			 
			 customer.setMobileNo(res.getString(1));
			 customer.setName(res.getString(2));
			 wallet.setBalance(res.getBigDecimal(3));
			 customer.setWallet(wallet);
		}
		catch(Exception e) 
		{
			
			System.out.println(e.getMessage());
		}
		return customer;
		}
	
	public void update(Customer customer,String transactionType) 
	{
		try(Connection con=DBUtil.getConnection())
		{
			PreparedStatement pstm=con.prepareStatement("update customers set balance=? where mobileNo=?");
			pstm.setBigDecimal(1, customer.getWallet().getBalance());
			pstm.setString(2,customer.getMobileNo());
			pstm.executeQuery();
			
			long millis=System.currentTimeMillis();
			Date date=new java.sql.Date(millis);
			
			PreparedStatement pstm1=con.prepareStatement("insert into recenttransactions values(?,?,?,?)");
			pstm1.setString(1, customer.getMobileNo());
			pstm1.setString(2, transactionType);
			pstm1.setDate(3, date);
			pstm1.setBigDecimal(4, customer.getWallet().getBalance());
			try {
					pstm1.execute();
					
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Transactions> recentTransactions(String mobileNo) 
	{
		try(Connection con=DBUtil.getConnection()) 
		{
			
			
			PreparedStatement pstm=con.prepareStatement("select * from recentTransactions where mobileno=? order by amount desc");
			pstm.setString(1, mobileNo);
			
		
			ResultSet res = pstm.executeQuery();
			
				if(res.next()==false)
				{
					throw new InvalidInputException("not found");
				}
				
				recent.add(new Transactions(res.getString(1),res.getString(2),res.getDate(3),res.getBigDecimal(4)));
				while(res.next())
				{
					recent.add(new Transactions(res.getString(1),res.getString(2),res.getDate(3),res.getBigDecimal(4)));
				}
				
		}
			
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return recent ;
	}
	
}
