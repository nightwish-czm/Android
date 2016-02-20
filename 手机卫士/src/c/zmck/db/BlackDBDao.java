package c.zmck.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import c.zmck.entity.BlackEntity;

public class BlackDBDao {
	private BlackOpenHelper openHelper;
	private SQLiteDatabase database;
	
	public BlackDBDao(Context context) {
		super();
		openHelper = new BlackOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}

	public boolean insert(BlackEntity entity){
		ContentValues values = new ContentValues();
		values.put(BlackListDB.BlackListFiled.SQL_NUM, entity.num);
		values.put(BlackListDB.BlackListFiled.SQL_TYPE, entity.type);
		long insert = database.insert(BlackListDB.BlackListFiled.TABLE_NAME, null, values);
		
		if(insert!=-1){
			return true;
		}else{
		return false;
		}
	}
	
	public List<BlackEntity> getAllBlacks(){
		List<BlackEntity> blackList = new ArrayList<BlackEntity>();
		Cursor query = database.query(BlackListDB.BlackListFiled.TABLE_NAME,
				null, null, null, null, null, null);
		if(query!=null){
			while(query.moveToNext()){
				BlackEntity entity = new BlackEntity();
				String num = query.getString(query.getColumnIndex(BlackListDB.BlackListFiled.SQL_NUM));
				entity.num = num;
				int type = query.getInt(query.getColumnIndex(BlackListDB.BlackListFiled.SQL_TYPE));
				entity.type = type;
				blackList.add(entity);
			}
		}
		return blackList;
		}
	
	public List<BlackEntity> getPartBlacks(int count,int offset){
		List<BlackEntity> blackList = new ArrayList<BlackEntity>();
		String sql = "select * from blackList limit "+offset+","+count;
		Cursor query = database.rawQuery(sql, null);
		if(query!=null){
			while(query.moveToNext()){
				String num = query.getString(query.getColumnIndex(BlackListDB.BlackListFiled.SQL_NUM));
				int type = query.getInt(query.getColumnIndex(BlackListDB.BlackListFiled.SQL_TYPE));
				BlackEntity entity = new BlackEntity();
				entity.num = num;
				entity.type = type;
				blackList.add(entity);
			}
		}
		return blackList;
	}
	
		
	public boolean deleteBlack(BlackEntity entity){
		
		int delete = database.delete(BlackListDB.BlackListFiled.TABLE_NAME,
				BlackListDB.BlackListFiled.SQL_NUM+"=?", new String[]{entity.num});
		if(delete!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateBlack(BlackEntity entity){
		ContentValues values = new ContentValues();
		values.put(BlackListDB.BlackListFiled.SQL_TYPE, entity.type);
		int update = database.update(BlackListDB.BlackListFiled.TABLE_NAME, values,
				BlackListDB.BlackListFiled.SQL_NUM+"=?", new String[]{entity.num});
		if(update==0){
			return false;
		}else{
		return true;
		}
	}
}
