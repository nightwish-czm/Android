package c.zmck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import c.zmck.mobilesafe.R;

public class SettingGuide5Activity extends BaseSettingGuideActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settingguide5);
	}
	

	@Override
	public boolean moveToNext() {

		Intent intent = new Intent();
		intent.setClass(this, SettingGuide4Activity.class);
		startActivity(intent);
		return true;
	}

	@Override
	public boolean moveToPre() {

		Intent intent = new Intent();
		intent.setClass(this, SettingGuide4Activity.class);
		startActivity(intent);
		return false;
	}
	
	
	
	
}
