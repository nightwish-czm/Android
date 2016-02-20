package c.zmck.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import c.zmck.constants.Constants;
import c.zmck.entity.ContactEntity;
import c.zmck.mobilesafe.R;
import c.zmck.utils.ContactsUtil;

public class SelectSafeNumActivity extends Activity {

	public static final int RESPONSE_CODE = 110;
	private ListView lv;
	private List<ContactEntity> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initAdapter();
		initListener();
	}


	private void initListener() {
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ContactEntity contactEntity = list.get(position);
				String num = contactEntity.num;
				Intent intent = new Intent();
				intent.putExtra(Constants.SJFD_SAFE_NUM, num);
				setResult(RESPONSE_CODE, intent);
				finish();
			}
		});
	}


	private void initAdapter() {
		list = ContactsUtil.getAllContacts(SelectSafeNumActivity.this);
		System.out.println(list);
		lv.setAdapter(new MyAdapter());
	}


	private void initView() {
		setContentView(R.layout.activity_safenum_select);
		lv = (ListView) findViewById(R.id.lv_contacts);
	}
	
	class MyAdapter extends BaseAdapter{

		private LayoutInflater inflater;

		
		public MyAdapter(){
			inflater = (LayoutInflater) SelectSafeNumActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
//			View view;
			ViewHolder viewHolder = new ViewHolder();
			if(convertView==null){
				convertView = inflater.inflate(R.layout.lv_contacts_item,parent,false);
			}/*else{
				view = convertView;
			}*/
			
			viewHolder.contactName = (TextView) convertView.findViewById(R.id.tv_contact_name);
			viewHolder.contactNum = (TextView) convertView.findViewById(R.id.tv_contact_num);
			viewHolder.contactImg = (ImageView) convertView.findViewById(R.id.iv_contact_img);
			ContactEntity contactEntity = list.get(position);
			System.out.println(contactEntity);
			viewHolder.contactName.setText(contactEntity.name);
			viewHolder.contactNum.setText(contactEntity.num);
			viewHolder.contactImg.setImageBitmap(ContactsUtil.getContactbmpById(SelectSafeNumActivity.this,
					contactEntity.id));
			return convertView;
		}
		
	}
	
	class ViewHolder{
		
		public TextView contactName;
		public TextView contactNum;
		public ImageView contactImg;
	}
}
