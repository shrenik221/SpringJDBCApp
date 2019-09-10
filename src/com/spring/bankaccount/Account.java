package com.spring.bankaccount;

public class Account {
	private String accName;
	private int accNum;
	private float balance;
	private String type;

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "{AccName = "+accName +",Balance = "+balance+",type="+type+",AccNum ="+accNum +"}";
	}
}
