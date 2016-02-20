package c.zmck.activity;

import org.w3c.dom.Text;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;

public class SettingGuide3Activity extends BaseSettingGuideActivity {
	public static final int REQUEST_CODE = 0;
	private EditText etSafeNum;
	private Button btnContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initListener();
	}
	
	
	
	
	private void initListener() {

		btnContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingGuide3Activity.this, SelectSafeNumActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE){
			if(resultCode==SelectSafeNumActivity.RESPONSE_CODE){
				if(data!=null){
					String safeNum = data.getStringExtra(Constants.SJFD_SAFE_NUM);
					etSafeNum.setText(safeNum);
					etSafeNum.setSelection(safeNum.length());
				}
			}
		}
	}


	private void initView() {
		setContentView(R.layout.activity_settingguide3);
		etSafeNum = (EditText) findViewById(R.id.et_safe_num);
		btnContact = (Button) findViewById(R.id.btn_contact);
		String safeNum = SharePreferenceUtil.readString(this, Constants.SJFD_SAFE_NUM);
		if(!TextUtils.isEmpty(safeNum)){
		etSafeNum.setText(safeNum);
		etSafeNum.setSelection(safeNum.length());
		}
	}




	@Override
	public boolean moveToNext() {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(etSafeNum.getText().toString())){
			Toast.makeText(this, "«Î ‰»Î∞≤»´∫≈¬Î", Toast.LENGTH_LONG).show();
			return true;
		}else{
		SharePreferenceUtil.writeString(this, Constants.SJFD_SAFE_NUM, etSafeNum.getText().toString());
		Intent intent = new Intent();
		intent.setClass(this, SettingGuide4Activity.class);
		startActivity(intent);
		return false;
		}
	}

	@Override
	public boolean moveToPre() {
			Intent intent = new Intent();
			intent.setClass(this, SettingGuide2Activity.class);
			startActivity(intent);
			return false;
		}
	}
	

