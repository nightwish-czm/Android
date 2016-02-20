package c.zmck.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtil {

	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("config", context.MODE_PRIVATE);
	}
	
	public static void writeString(Context context,String key,String value){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}
	public static String readString(Context context,String key){
		return getSharedPreferences(context).getString(key, null);
	}
	
	
	public static void writeBoolean(Context context,String key,boolean value){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	
	public static boolean readBoolean(Context context,String key){
		return getSharedPreferences(context).getBoolean(key, false);
	}
	
}

