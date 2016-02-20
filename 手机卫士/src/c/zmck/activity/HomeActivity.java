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

	private String[] TITLES = { "�ֻ�����", "ɧ������", "����ܼ�", "���̹���", "����ͳ��", "�ֻ�ɱ��",
			"��������", "���ù���" };
	private String[] DES = { "Զ�̶�λ", "ȫ��ɧ������", "�����������", "�������н���", "����һĿ��Ȼ",
			"�����޴�����", "ϵͳ������", "���ߴ�ȫ" };
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
	//�O��gridview�O ��
	private void setGVLisener() {
		// TODO Auto-generated method stub
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//ִ���ֻ�����
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
	//ִ���ֻ�����
	protected void performSJFD() {

		String psw = (String) SharePreferenceUtil.readString(HomeActivity.this, Constants.SJFD_PSW);
		if(TextUtils.isEmpty(psw)){
			showSetPswDialog();//��ʾ�������öԻ���
		}else{
			//showPswDialog();//��ʾ��������Ի���
			enterSettingGuide();
		}
		
	}
	//��ʾ��������Ի���
	private void showPswDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		final View view = View.inflate(HomeActivity.this, R.layout.view_pswdialog, null);
		builder.setView(view);
		builder.setNegativeButton("ȡ��", null);
		builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText edPsw = (EditText) view.findViewById(R.id.edPsw);
				String Psw = edPsw.getText().toString();
				String sjfdPsw = (String) SharePreferenceUtil.readString(HomeActivity.this, Constants.SJFD_PSW);
				if(!Psw.equals(sjfdPsw)){
					Toast.makeText(HomeActivity.this, "�������", 1).show();
				}else if(Psw.equals(sjfdPsw)){
					enterSettingGuide();
				}
				
			}});
		
		builder.show();
		
	}
	//��ʾ�ֻ���������Ի���
	private void showSetPswDialog() {
		
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		final View view = View.inflate(HomeActivity.this, R.layout.view_setpswdialog, null);
		builder.setView(view);
		builder.setNegativeButton("ȡ��", null);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//��ȡ�������������
				EditText edFirstPsw = (EditText) view.findViewById(R.id.first_psw);
				String firstPsw = edFirstPsw.getText().toString();
				EditText edSecondPsw = (EditText) view.findViewById(R.id.second_psw);
				String secondPsw = edSecondPsw.getText().toString();
				//�ж�����
				if(TextUtils.isEmpty(firstPsw)||TextUtils.isEmpty(secondPsw)){
					Toast.makeText(HomeActivity.this, "���벻��Ϊ�գ�������", 1).show();;
				}//end if
				else if(!firstPsw.equals(secondPsw)){
					Toast.makeText(HomeActivity.this, "�����������벻һ�£�����������", 1).show();
				}else if(firstPsw.equals(secondPsw)){
					SharePreferenceUtil.writeString(HomeActivity.this, Constants.SJFD_PSW, firstPsw);
					enterSettingGuide();
				}
			}
			
		});
		builder.show();
		
	}
	
	//�������ý���
	protected void enterSettingGuide() {
		Intent intent = new Intent();
		intent.setClass(HomeActivity.this, SettingGuide1Activity.class);
		startActivity(intent);
		
	}
	//�O�ð��o�c���¼�
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



	//��ʼ��list
	private void initlist() {
		for(int index=0;index<TITLES.length;index++){
			list.add(new HomeItemEntity(TITLES[index], DES[index], IMAGES[index]));
		}
	}



	//�O��logo�Ӯ�
	private void setAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(heiMa, "alpha", 0.1f,1.0f,0.1f);
		animator.setRepeatCount(-1);
		animator.setDuration(3000);
		animator.start();
	}
	
	//�O��gridview���m����
	private void setGVAdapter() {
		
		gv.setAdapter(new MyGVadapter());
		
	}
	//�Զ��x�m����
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
