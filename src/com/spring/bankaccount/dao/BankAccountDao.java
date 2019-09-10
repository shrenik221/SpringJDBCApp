package com.spring.bankaccount.dao;

import java.util.List;
import com.spring.bankaccount.Account;

public interface BankAccountDao {

	public void save(Account account); // Create Account

	public Account getByAccNum(int accNum); // Get Account details

	public List<Account> getAll(); // Display List

	public void deposit(int accNum); // Deposit data

	public void withdrawal(int accNum); // Withdraw with accNum

	public void deleteByAccNum(int id); // delete by accNum
}
