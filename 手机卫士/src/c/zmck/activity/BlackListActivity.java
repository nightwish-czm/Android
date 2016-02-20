package c.zmck.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import c.zmck.db.BlackDBDao;
import c.zmck.entity.BlackEntity;
import c.zmck.mobilesafe.R;

public class BlackListActivity extends Activity {
		protected static final int ADD_BLACK_REQUESTCODE = 1;
		protected static final String UPDATE_BLACK_ACTION = "suibian";
		protected static final int UPDATE_BLACK_REQUESTCODE = 0;
		protected static final String ADD_BLACK_ACTION = "yesuibian";
		protected static final String BLACK_NUM = "也是随便";
		protected static final String BLACK_TYPE = "不能随便了";
		protected static final String UPDATE_BALAK_POSITION = "HOU";
		private static final int PART_LIMIT_COUNT = 10;
		private ListView lvBlackList;
		private ImageView ivAddBlack;
		private List<BlackEntity> allBlacks=new ArrayList<BlackEntity>();
		private BlackDBDao dbDao;
		private BlackListAdapter adapter;
		private ProgressBar pbLoading;
		private ImageView ivBlackEmpty;
		private int offset = 0;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			initView();
			initData();
			initListener();
		}

		@Override
		protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		pbLoading.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SystemClock.sleep(1000);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pbLoading.setVisibility(View.GONE);
						if(requestCode==UPDATE_BLACK_REQUESTCODE){
							if(resultCode==AddBlackActivity.UPDATE_RESULTCODE){
								if(data!=null){
									Log.d("zmck", "带数据回来了");
									int type = data.getIntExtra(BLACK_TYPE, 0);
									int position = data.getIntExtra(UPDATE_BALAK_POSITION, 0);
									BlackEntity entity = allBlacks.get(position);
									entity.type = type;
									Log.d("zmck", allBlacks.toString());
									adapter.notifyDataSetChanged();
									}
								return;
							}
						}else if(requestCode==ADD_BLACK_REQUESTCODE){}
								if(resultCode==AddBlackActivity.ADD_RESULTCODE){
									if(data!=null){
										String num = data.getStringExtra(BLACK_NUM);
										int type = data.getIntExtra(BLACK_TYPE, 0);
										BlackEntity entity = new BlackEntity();
										entity.num = num;
										entity.type = type;
										allBlacks.add(entity);
										adapter.notifyDataSetChanged();
										if(adapter.getCount()!=0){
											ivBlackEmpty.setVisibility(View.GONE);
										}
									}
								}
					}
				});
			}
		}).start();
		
		}
		
		private void initListener() {

			lvBlackList.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					int lastVisiblePosition = lvBlackList.getLastVisiblePosition();
					if(lastVisiblePosition==(allBlacks.size()-1)/*&&scrollState==OnScrollListener.SCROLL_STATE_IDLE*/){
						pbLoading.setVisibility(View.VISIBLE);
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								SystemClock.sleep(1000);
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										offset+=10;
										List<BlackEntity> partBlacks = dbDao.getPartBlacks(PART_LIMIT_COUNT,
												offset);
										if(partBlacks.size()>0){
										allBlacks.addAll(partBlacks);
										adapter.notifyDataSetChanged();
										pbLoading.setVisibility(View.GONE);
										}else{
											Toast.makeText(BlackListActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
											pbLoading.setVisibility(View.GONE);
										}
									}
								});
							}
						}).start();
					}
				}
			});
			
			lvBlackList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					BlackEntity item = allBlacks.get(position);
					Intent intent = new Intent(BlackListActivity.this,AddBlackActivity.class);
					intent.setAction(UPDATE_BLACK_ACTION);
					intent.putExtra(BLACK_NUM, item.num);
					intent.putExtra(BLACK_TYPE, item.type);
					intent.putExtra(UPDATE_BALAK_POSITION, position);
					startActivityForResult(intent, UPDATE_BLACK_REQUESTCODE);
				}
			});
			
			ivAddBlack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(BlackListActivity.this,AddBlackActivity.class);
					intent.setAction(ADD_BLACK_ACTION);
					startActivityForResult(intent, ADD_BLACK_REQUESTCODE);
					
				}
			});
		}

		private void initData() {
			dbDao = new BlackDBDao(BlackListActivity.this);
			List<BlackEntity> itemBlacks = dbDao.getPartBlacks(PART_LIMIT_COUNT, offset);
			if(itemBlacks!=null){
			allBlacks.addAll(itemBlacks);
			}
//			allBlacks = dbDao.getAllBlacks();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					SystemClock.sleep(1000);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pbLoading.setVisibility(View.GONE);
							adapter = new BlackListAdapter();
							lvBlackList.setAdapter(adapter);
							if (adapter.getCount()==0) {
								ivBlackEmpty.setVisibility(View.VISIBLE);
							}
						}
					});
				}
			}).start();
//			adapter = new BlackListAdapter();
//				lvBlackList.setAdapter(adapter);
		}

		private void initView() {
			setContentView(R.layout.activity_blacklist);
			lvBlackList = (ListView) findViewById(R.id.lv_black_list);
			ivAddBlack = (ImageView) findViewById(R.id.iv_add_black);
			pbLoading = (ProgressBar) findViewById(R.id.pb_blacklist_loading);
			ivBlackEmpty = (ImageView) findViewById(R.id.iv_black_empty);
		}
		
		class BlackListAdapter extends BaseAdapter{

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return allBlacks.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return allBlacks.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				final int itemPosition = position;
				LayoutInflater inflater = LayoutInflater.from(BlackListActivity.this);
				ViewHolder holder;
				BlackEntity blackEntity = allBlacks.get(position);
				if(convertView==null){
					holder = new ViewHolder();
					convertView = inflater.inflate(R.layout.lv_blacks_item, parent, false);
					holder.tvNum = (TextView) convertView.findViewById(R.id.tv_blacklist_num);
					holder.tvType = (TextView) convertView.findViewById(R.id.tv_blacklist_type);
					holder.ivDel = (ImageView) convertView.findViewById(R.id.iv_blacklist_del);
					convertView.setTag(holder);
				}else{
					holder = (ViewHolder) convertView.getTag();
				}
				holder.tvNum.setText(blackEntity.num);
				switch(blackEntity.type){
				
				case AddBlackActivity.CALL:
					holder.tvType.setText("电话");
					break;
				case AddBlackActivity.SMS:
					holder.tvType.setText("短信");
					break;
				case AddBlackActivity.ALL:
					holder.tvType.setText("电话+短信");
					break;
				}
				
				holder.ivDel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						BlackEntity blackEntity = allBlacks.get(itemPosition);
						boolean deleteBlack = dbDao.deleteBlack(blackEntity);
						if(deleteBlack){
							allBlacks.remove(blackEntity);
							if(adapter!=null){
								if(adapter.getCount()==0){
									ivBlackEmpty.setVisibility(View.VISIBLE);
								}
								adapter.notifyDataSetChanged();
							}
							Toast.makeText(BlackListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(BlackListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
				
				return convertView;
			}
			
		}
		
		class ViewHolder{
			public TextView tvNum;
			public TextView tvType;
			public ImageView ivDel;
		}
}
