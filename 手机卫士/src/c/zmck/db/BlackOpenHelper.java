package c.zmck.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackOpenHelper extends SQLiteOpenHelper {

	
	
	public BlackOpenHelper(Context context) {
		super(context, BlackListDB.DB_NAME, null, BlackListDB.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(BlackListDB.BlackListFiled.CREATE_TABLE_BLACKLIST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(BlackListDB.BlackListFiled.DROP_TABLE_BLAKLIST);
		db.execSQL(BlackListDB.BlackListFiled.CREATE_TABLE_BLACKLIST);
	}

}
