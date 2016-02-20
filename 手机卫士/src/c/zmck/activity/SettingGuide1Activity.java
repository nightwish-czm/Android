package c.zmck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import c.zmck.mobilesafe.R;

public class SettingGuide1Activity extends BaseSettingGuideActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingguide1);
	}
	
	public void moveToNext(View view){
		
	}

	@Override
	public boolean moveToNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, SettingGuide2Activity.class);
		startActivity(intent);
		return false;
	}

	@Override
	public boolean moveToPre() {
		// TODO Auto-generated method stub
		return true;
	}
}
