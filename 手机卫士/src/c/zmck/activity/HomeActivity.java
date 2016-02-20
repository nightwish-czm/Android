package c.zmck.activity;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.entity.HomeItemEntity;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;

public class HomeActivity extends Activity {

	private String[] TITLES = { "手机防盗", "骚扰拦截", "软件管家", "进程管理", "流量统计", "手机杀毒",
			"缓存清理", "常用工具" };
	private String[] DES = { "远程定位", "全面骚扰拦截", "管理您的软件", "管理运行进程", "流量一目了然",
			"病毒无处藏身", "系统快如火箭", "工具大全" };
	private int[] IMAGES = { R.drawable.sjfd, R.drawable.srlj, R.drawable.rjgj,
			R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd, R.drawable.hcql,
			R.drawable.cygj };
	private GridView gv;
	private ImageView heiMa;
	private List<HomeItemEntity> list=new ArrayList<HomeItemEntity>();
	private ImageView setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		heiMa = (ImageView) findViewById(R.id.iv_heima);
			setAnimation();
			
		initlist();
			
		gv = (GridView) findViewById(R.id.gridView);
		setGVAdapter();
		setGVLisener();
		setting = (ImageView) findViewById(R.id.iv_setting);
		clickSetting();
	}
	//設置gridview監聽器
	private void setGVLisener() {
		// TODO Auto-generated method stub
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//执行手机防盗
					performSJFD();
					break;
					
				case 1:
					performSRLJ();
					break;
				}
			}
		});
	}
	protected void performSRLJ() {
		Intent intent = new Intent(this,BlackListActivity.class);
		startActivity(intent);
	}
	//执行手机防盗
	protected void performSJFD() {

		String psw = (String) SharePreferenceUtil.readString(HomeActivity.this, Constants.SJFD_PSW);
		if(TextUtils.isEmpty(psw)){
			showSetPswDialog();//显示密码设置对话框
		}else{
			//showPswDialog();//显示密码输入对话框
			enterSettingGuide();
		}
		
	}
	//显示密码输入对话框
	private void showPswDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		final View view = View.inflate(HomeActivity.this, R.layout.view_pswdialog, null);
		builder.setView(view);
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText edPsw = (EditText) view.findViewById(R.id.edPsw);
				String Psw = edPsw.getText().toString();
				String sjfdPsw = (String) SharePreferenceUtil.readString(HomeActivity.this, Constants.SJFD_PSW);
				if(!Psw.equals(sjfdPsw)){
					Toast.makeText(HomeActivity.this, "密码错误", 1).show();
				}else if(Psw.equals(sjfdPsw)){
					enterSettingGuide();
				}
				
			}});
		
		builder.show();
		
	}
	//显示手机防盗密码对话框
	private void showSetPswDialog() {
		
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		final View view = View.inflate(HomeActivity.this, R.layout.view_setpswdialog, null);
		builder.setView(view);
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//获取两次输入的密码
				EditText edFirstPsw = (EditText) view.findViewById(R.id.first_psw);
				String firstPsw = edFirstPsw.getText().toString();
				EditText edSecondPsw = (EditText) view.findViewById(R.id.second_psw);
				String secondPsw = edSecondPsw.getText().toString();
				//判断密码
				if(TextUtils.isEmpty(firstPsw)||TextUtils.isEmpty(secondPsw)){
					Toast.makeText(HomeActivity.this, "密码不能为空，请输入", 1).show();;
				}//end if
				else if(!firstPsw.equals(secondPsw)){
					Toast.makeText(HomeActivity.this, "两次输入密码不一致，请重新输入", 1).show();
				}else if(firstPsw.equals(secondPsw)){
					SharePreferenceUtil.writeString(HomeActivity.this, Constants.SJFD_PSW, firstPsw);
					enterSettingGuide();
				}
			}
			
		});
		builder.show();
		
	}
	
	//进入设置界面
	protected void enterSettingGuide() {
		Intent intent = new Intent();
		intent.setClass(HomeActivity.this, SettingGuide1Activity.class);
		startActivity(intent);
		
	}
	//設置按鈕點擊事件
	private void clickSetting() {

		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});
		
	}



	//初始化list
	private void initlist() {
		for(int index=0;index<TITLES.length;index++){
			list.add(new HomeItemEntity(TITLES[index], DES[index], IMAGES[index]));
		}
	}



	//設置logo動畫
	private void setAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(heiMa, "alpha", 0.1f,1.0f,0.1f);
		animator.setRepeatCount(-1);
		animator.setDuration(3000);
		animator.start();
	}
	
	//設置gridview的適配器
	private void setGVAdapter() {
		
		gv.setAdapter(new MyGVadapter());
		
	}
	//自定義適配器
class MyGVadapter extends BaseAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TITLES.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return new HomeItemEntity(TITLES[position], DES[position], IMAGES[position]);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=null;
		HomeItemEntity homeItem=list.get(position);
		if(convertView==null){
			view = HomeActivity.this.getLayoutInflater().inflate(R.layout.gv_item, parent, false);
		}//end if
		else{
			view=convertView;
		}
		
		TextView title = (TextView) view.findViewById(R.id.gv_item_title);
		TextView des = (TextView) view.findViewById(R.id.gv_item_describe);
		ImageView image = (ImageView) view.findViewById(R.id.gv_item_image);
		
		title.setText(homeItem.title);
		des.setText(homeItem.describe);
		image.setImageResource(homeItem.imageId);
		
		return view;
	}

	}
}
