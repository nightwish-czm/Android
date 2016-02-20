package c.zmck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;
import c.zmck.view.SettingItemView;
import c.zmck.view.SettingItemView.OnItemClickListener;

public class SettingActivity extends Activity{
	private SettingItemView autoUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();//初始化View
		initListener();//初始化监听
		
	}
	private void initView() {
		setContentView(R.layout.activity_setting);
		autoUpdate = (SettingItemView) findViewById(R.id.auto_update);
		if(SharePreferenceUtil.readBoolean(getApplicationContext(), Constants.AUTO_UPDATE_APP)){
			autoUpdate.setSettingItemChecked(true);
		}else{
			autoUpdate.setSettingItemChecked(false);
		}
	}

	public void autoUpdate(View view){
		autoUpdate.changeSettingItemChecked();
		SharePreferenceUtil.writeBoolean(getApplicationContext(),
				Constants.AUTO_UPDATE_APP, autoUpdate.getSettingItemChecked());
	}
	
	private void initListener() {
//		autoUpdate.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				Toast.makeText(getApplicationContext(), "----", Toast.LENGTH_LONG).show();
//				autoUpdate.changeSettingItemChecked();
//				SharePreferenceUtil.writeBoolean(getApplicationContext(),
//						Constants.AUTO_UPDATE_APP, autoUpdate.getSettingItemChecked());
//			}
//		});
		
		autoUpdate.setOnItemClick(new OnItemClickListener() {
			
			@Override
			public void onItemClick(View view) {
				Toast.makeText(getApplicationContext(), "----", Toast.LENGTH_LONG).show();
				autoUpdate.changeSettingItemChecked();
				SharePreferenceUtil.writeBoolean(getApplicationContext(),
						Constants.AUTO_UPDATE_APP, autoUpdate.getSettingItemChecked());
			}
		});
	}

	
	
	
}
