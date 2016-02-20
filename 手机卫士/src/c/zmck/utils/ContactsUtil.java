package c.zmck.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import c.zmck.entity.ContactEntity;

public class ContactsUtil {
	private static Uri CONTACTS_URI = Uri.parse("content://com.android.contacts/raw_contacts");
	private static Uri CONTACTS_DATA_URI = Uri.parse("content://com.android.contacts/data");
	private static ContentResolver resolver;
	public static List<ContactEntity> getAllContacts(Context context){
		ContentResolver resolver = context.getContentResolver();
		Cursor query = resolver.query(CONTACTS_URI, new String[]{"_id","display_name"}, null, null, null);
		List<ContactEntity> list = new ArrayList<ContactEntity>();
		
		if(query!=null){
			while(query.moveToNext()){
				int queryId = query.getInt(query.getColumnIndex("_id"));
				String name = query.getString(query.getColumnIndex("display_name"));
				ContactEntity contact = new ContactEntity();
				contact.id = queryId;
				contact.name = name;
				
				Cursor query2 = resolver.query(CONTACTS_DATA_URI, new String[]{"data1","mimetype"},
						"raw_contact_id=?", new String[]{String.valueOf(queryId)}, null);
				
				if(query2!=null){
					while(query2.moveToNext()){
						String data1 = query2.getString(query2.getColumnIndex("data1"));
						String mimetype = query2.getString(query2.getColumnIndex("mimetype"));
						if(mimetype.equals("vnd.android.cursor.item/phone_v2")){
							contact.num = data1;
							list.add(contact);
						}
					}	
				}
			}
		}
		
		return list;
	}
	
	public static Bitmap getContactbmpById(Context context,int Id){
		resolver = context.getContentResolver();
		Cursor query = resolver.query(CONTACTS_DATA_URI, new String[]{"data15"}, "raw_contact_id=?",
				new String[]{String.valueOf(Id)}, null);
		
		if(query!=null){
			while(query.moveToNext()){
				byte[] image = query.getBlob(query.getColumnIndex("data15"));
				if(image==null){
					continue;
				}
				Bitmap bitmap = new BitmapFactory().decodeByteArray(image, 0, image.length);
				return bitmap;
			}
		}
		
		return null;}
}
