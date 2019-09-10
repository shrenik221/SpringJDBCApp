package com.spring.bankaccount.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.bankaccount.Account;
import com.spring.bankaccount.dao.BankAccountDao;

public class BankAccountJDBCApp {
	public static void main(String[] args) {
		final AtomicInteger count = new AtomicInteger(100000);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring.xml");
		BankAccountDao accountDao = ctx.getBean("accountDao", BankAccountDao.class);
		Scanner object = new Scanner(System.in);
		String accName = null;
		float balance = 1000;
		int accNum = 0;
		String type = null;
		int choice = 0;
		boolean traverse = true;
		Account account = new Account();
		while (traverse) {

			System.out.println("\n\nOptions Menu");
			System.out.println("1.Create Account:");
			System.out.println("2.Delete Account");
			System.out.println("3.Manage Account");
			System.out.println("4.Display All Accounts");
			System.out.println("5. Exit");
			System.out.println("Enter your choice:");
			choice = object.nextInt();
			switch (choice) {
			case 1:
				try {
					System.out.println("Enter the name of your account:");
					accName = object.next();
					System.out.println("Enter the account type(savings or current): ");
					type = object.next();
					accNum = count.incrementAndGet();
					account.setAccNum(accNum);
					account.setAccName(accName);
					account.setBalance(balance);
					account.setType(type);
					accountDao.save(account);
				} catch (Exception e) {
					System.out.println(e);
				}
				traverse = true;
				break;

			case 2:
				try {

					System.out.println("Enter Account Number to delete:");
					accNum = object.nextInt();
					accountDao.deleteByAccNum(accNum);
				} catch (Exception e) {
					System.out.println(e);
				}
				traverse = true;
				break;

			case 3:
				while (traverse) {
					try {
						System.out.println("Options:");
						System.out.println("1. Deposit");
						System.out.println("2. Withdrawal");
						System.out.println("Enter your choice:");
						choice = object.nextInt();
						switch (choice) {
						case 1:
							System.out.println("Enter your account number:");
							accNum = object.nextInt();
							accountDao.deposit(accNum);
							traverse = true;
							break;
						case 2:
							System.out.println("Enter your account number:");
							accNum = object.nextInt();
							accountDao.withdrawal(accNum);
							traverse = true;
							break;
						default:
							System.out.println("Wrong choice...Enter Again");
						}

					} catch (Exception e) {
						System.out.println(e);
					}
				}
				traverse = true;
				break;
			case 4:
				List<Account> list = new ArrayList<Account>();
				list = accountDao.getAll();
				for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
				}
				traverse = true;
				break;
			case 5:
				traverse = false;
				return;
			default:
				System.out.println("Wrong choice...Enter Again");

			}
		}
	}
}
