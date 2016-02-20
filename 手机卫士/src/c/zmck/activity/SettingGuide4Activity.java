package c.zmck.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import c.zmck.constants.Constants;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;

public class SettingGuide4Activity extends BaseSettingGuideActivity {
	
	private Button btnAdmin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initListener();
	}
	

	private void initListener() {
		btnAdmin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private void initView() {
		setContentView(R.layout.activity_settingguide4);
		btnAdmin = (Button) findViewById(R.id.btn_admin);
		boolean isAdmin = SharePreferenceUtil.readBoolean(SettingGuide4Activity.this, Constants.SJFD_ADMIN);
		if(isAdmin){
			changeTOAdmin();
		}else{
			changeTOUnadmin();
		}
	}


	private void changeTOUnadmin() {
		Drawable drawable = getResources().getDrawable(R.drawable.admin_inactivated);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
		btnAdmin.setCompoundDrawables(null, null, drawable, null);
	}


	private void changeTOAdmin() {
		Drawable drawable = getResources().getDrawable(R.drawable.admin_activated);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		btnAdmin.setCompoundDrawables(null, null, drawable, null);
	}


	@Override
	public boolean moveToNext() {
		Intent intent = new Intent();
		intent.setClass(this, SettingGuide5Activity.class);
		startActivity(intent);
		return false;
	}

	@Override
	public boolean moveToPre() {

		Intent intent = new Intent();
		intent.setClass(this, SettingGuide3Activity.class);
		startActivity(intent);
		return false;
	}
	
	
	
	
}
