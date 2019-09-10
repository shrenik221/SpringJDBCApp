package com.spring.bankaccount.dao;

import java.util.List;
import java.util.Scanner;

import com.spring.bankaccount.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class BankAccountDaoImpl implements BankAccountDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Account account) {
		String query = "insert into Account (accnum, accname, type, balance) values (?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, account.getAccNum());
			ps.setString(2, account.getAccName());
			ps.setString(3, account.getType());
			ps.setFloat(4, account.getBalance());
			int out = ps.executeUpdate();
			if (out != 0) {
				System.out.println("Account saved with id=" + account.getAccNum());
			} else
				System.out.println("Account failed to save with id=" + account.getAccNum());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Account getByAccNum(int accNum) {
		String query = "select accname, type ,balance from Account where accnum = ?";
		Account account = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, accNum);
			rs = ps.executeQuery();
			if (rs.next()) {
				account = new Account();
				account.setAccNum(accNum);
				account.setAccName(rs.getString("accname"));
				account.setType(rs.getString("type"));
				account.setBalance(rs.getFloat("balance"));
				System.out.println("Account Found::" + account);
			} else {
				System.out.println("No Account found with AccNum=" + accNum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return account;
	}

	@Override
	public List<Account> getAll() {
		String query = "select accnum, accname, type,balance from Account";
		List<Account> accList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Account acc = new Account();
				acc.setAccNum(rs.getInt("accnum"));
				acc.setAccName(rs.getString("accname"));
				acc.setType(rs.getString("type"));
				acc.setBalance(rs.getFloat("balance"));
				accList.add(acc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accList;
	}

	@Override
	public void deposit(int accNum) {
		float previousBalance = 0;
		float newBalance = 0;
		Scanner object = new Scanner(System.in);
		System.out.println("Enter the amount you want to deposit:");
		float amount = object.nextFloat();
		String queryOne = "select balance from Account where accnum=?";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(queryOne);
			ps.setInt(1, accNum);
			rs = ps.executeQuery();
			if ((rs.next())) {
				Account acc = new Account();
				acc.setAccNum(accNum);
				acc.setBalance(rs.getFloat("balance"));
				previousBalance = rs.getFloat("balance");
			} else {
				System.out.println("Account number not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newBalance = previousBalance + amount;
		String query = "update Account set balance=? where accnum=?";
		Connection con2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		try {
			con2 = dataSource.getConnection();
			ps2 = con.prepareStatement(query);
			ps2.setFloat(1, newBalance);
			ps2.setInt(2, accNum);
			int out = ps2.executeUpdate();
			if (out != 0) {
				System.out.println("Account updated with accNum  " + accNum);
			} else
				throw new Exception("Account not found");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps2.close();
				con2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void withdrawal(int accNum) {
		float previousBalance = 0;
		float newBalance = 0;
		Scanner object = new Scanner(System.in);
		System.out.println("Enter the amount you want to withdraw:");
		float amount = object.nextFloat();
		String queryOne = "select balance from Account where accnum=?";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(queryOne);
			ps.setInt(1, accNum);
			rs = ps.executeQuery();
			if ((rs.next())) {
				Account acc = new Account();
				acc.setAccNum(accNum);
				acc.setBalance(rs.getFloat("balance"));
				previousBalance = rs.getFloat("balance");
			} else {
				System.out.println("Account number not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newBalance = previousBalance - amount;
		String query = "update Account set balance=? where accnum=?";
		Connection con2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps2 = null;
		try {
			con2 = dataSource.getConnection();
			ps2 = con.prepareStatement(query);
			ps2.setFloat(1, newBalance);
			ps2.setInt(2, accNum);
			int out = ps2.executeUpdate();
			if (out != 0) {
				System.out.println("Account updated with accNum  " + accNum);
			} else
				throw new Exception("Account not found");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps2.close();
				con2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteByAccNum(int accNum) {
		String query = "delete from Account where accnum=?";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataSource.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, accNum);
			int out = ps.executeUpdate();
			if (out != 0) {
				System.out.println("Account deleted with id=" + accNum);
			} else
				System.out.println("No Account found with id=" + accNum);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
