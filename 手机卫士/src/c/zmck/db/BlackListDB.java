package c.zmck.db;

public class BlackListDB {

	public static final String DB_NAME = "BlackList.db";
	
	public static final int DB_VERSION = 4;
	
	public class BlackListFiled{
		
		public static final String TABLE_NAME = "blackList";
		
		public static final String SQL_ID = "id";
		
		public static final String SQL_NUM = "num";
		
		public static final String SQL_TYPE = "type";
		
		public static final String CREATE_TABLE_BLACKLIST = "create table blackList(id integer "
				+ "primary key autoincrement,num varchar(20) unique,type integer)";
		
		public static final String DROP_TABLE_BLAKLIST = "drop table blackList";
	}
}
