package com.jadecross.operator.guestbook.customresources;

public class GuestbookSpec {
	public String image;
	public Integer size;
	public Integer port;
	public MySqlDB db;

	public class MySqlDB {
		public String host;
		public Integer port;
		public String database;
		public String userid;
		public String password;
	}
}