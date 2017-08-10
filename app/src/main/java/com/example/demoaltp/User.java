package com.example.demoaltp;


import android.text.method.DateTimeKeyListener;

import java.sql.Date;

public class User {
	private String username;
	private int sotranthang;
	private int tongsotran;
	private String date_join;
	private int vcoin;

	public void setDate_join(String date_join) {
		this.date_join = date_join;
	}

	public void setSotranthang(int sotranthang) {
		this.sotranthang = sotranthang;
	}

	public void setTongsotran(int tongsotran) {
		this.tongsotran = tongsotran;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVcoin(int vcoin) {
		this.vcoin = vcoin;
	}

	public String getDate_join() {
		return date_join;
	}

	public int getSotranthang() {
		return sotranthang;
	}

	public int getTongsotran() {
		return tongsotran;
	}

	public int getVcoin() {
		return vcoin;
	}

	public String getUsername() {
		return username;
	}
	public User(){

	}

}
