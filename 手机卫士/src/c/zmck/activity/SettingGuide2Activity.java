package c.zmck.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;

public class SettingGuide2Activity extends BaseSettingGuideActivity {
	
	private Button btnSim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		initListener();
	}
	
	
	private void initListener() {
		
		btnSim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String simSerialNumber = tm.getSimSerialNumber();
				String simNum = SharePreferenceUtil.readString(SettingGuide2Activity.this,
						Constants.SJFD_BIND_SIM);
				
				if(TextUtils.isEmpty(simNum)){
					SharePreferenceUtil.writeString(SettingGuide2Activity.this,
							Constants.SJFD_BIND_SIM, simSerialNumber);
					changeToLock();
				}else{
					SharePreferenceUtil.writeString(SettingGuide2Activity.this, Constants.SJFD_BIND_SIM, "");
					changeToUnlock();
				}
				
			}
		});
	}


	private void initView() {
		setContentView(R.layout.activity_settingguide2);
		btnSim = (Button) findViewById(R.id.btn_sim);
		String simNum = SharePreferenceUtil.readString(this, Constants.SJFD_BIND_SIM);
		if(TextUtils.isEmpty(simNum)){
			changeToUnlock();
		}else{
			changeToLock();
		}
	}


	private void changeToLock() {
		Drawable drawable = getResources().getDrawable(R.drawable.lock);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		btnSim.setCompoundDrawables(null, null, drawable, null);
	}


	private void changeToUnlock() {
		Drawable drawable = getResources().getDrawable(R.drawable.unlock);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		btnSim.setCompoundDrawables(null, null,drawable, null);
	}


	@Override
	public boolean moveToPre() {
		Intent intent = new Intent();
		intent.setClass(this, SettingGuide1Activity.class);
		startActivity(intent);
		return false;
	}

	@Override
	public boolean moveToNext() {
		if(TextUtils.isEmpty(SharePreferenceUtil.readString(this, Constants.SJFD_BIND_SIM))){
			Toast.makeText(this, "Çë°ó¶¨sim¿¨", Toast.LENGTH_LONG).show();
			return true;
		}
		Intent intent = new Intent();
		intent.setClass(this, SettingGuide3Activity.class);
		startActivity(intent);
		return false;
	}
}
