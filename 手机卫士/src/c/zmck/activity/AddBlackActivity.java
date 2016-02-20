package c.zmck.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import c.zmck.db.BlackDBDao;
import c.zmck.entity.BlackEntity;
import c.zmck.mobilesafe.R;

public class AddBlackActivity extends Activity {
		private EditText etBlackNum;
		private RadioGroup rgType;
		private BlackEntity blackEntity;
		public static final int CALL = 55;
		public static final int SMS = 66;
		public static final int ALL = 88;
		protected static final int UPDATE_RESULTCODE = 0;
		protected static final int ADD_RESULTCODE = 1;
		private Button btnOkBlack;
		private Button btnCancelBlack;
		private String action;
		private int position;
		private RadioButton rbCall;
		private RadioButton rbSms;
		private RadioButton rbAll;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			initView();
			initData();
			initListener();
		}

		private void initData() {
			blackEntity = new BlackEntity();
			Intent intent = getIntent();
			action = intent.getAction();
			position = intent.getIntExtra(BlackListActivity.UPDATE_BALAK_POSITION, 0);
			int type = intent.getIntExtra(BlackListActivity.BLACK_TYPE, 66);
			if(action.equals(BlackListActivity.UPDATE_BLACK_ACTION)){
				btnOkBlack.setText("更改");
				etBlackNum.setText(intent.getStringExtra(BlackListActivity.BLACK_NUM));
				etBlackNum.setEnabled(false);
				switch(type){
				case  CALL:
					rbCall.setChecked(true);
					break;
				case  SMS:
					rbSms.setChecked(true);
					break;
				case  ALL:
					rbAll.setChecked(true);
					break;
				
				}
			}
		}

		private void initListener() {
			
			btnCancelBlack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AddBlackActivity.this.finish();
				}
			});
			
			btnOkBlack.setOnClickListener(new OnClickListener() {
				
				private String edNum;
				private int checkedRadioButtonId;

				@Override
				public void onClick(View v) {
					getData();
					if(TextUtils.isEmpty(edNum)){
						Toast.makeText(AddBlackActivity.this, "请输入拦截号码", Toast.LENGTH_SHORT).show();
						return;
					}else{
						if(checkedRadioButtonId==-1){
							Toast.makeText(AddBlackActivity.this, "请选择拦截类型", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					BlackDBDao blackDBDao = new BlackDBDao(AddBlackActivity.this);
					Intent intent = new Intent();
					if(action.equals(BlackListActivity.ADD_BLACK_ACTION)){
						
						boolean insert = blackDBDao.insert(blackEntity);
						
						if(insert){
							intent.putExtra(BlackListActivity.BLACK_NUM, blackEntity.num);
							intent.putExtra(BlackListActivity.BLACK_TYPE, blackEntity.type);
							setResult(ADD_RESULTCODE, intent);
							finish();
							Toast.makeText(AddBlackActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(AddBlackActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
						}
					}else if(action.equals(BlackListActivity.UPDATE_BLACK_ACTION)){
						boolean updateBlack = blackDBDao.updateBlack(blackEntity);
						if(updateBlack){
							intent.putExtra(BlackListActivity.BLACK_TYPE, blackEntity.type);
							intent.putExtra(BlackListActivity.UPDATE_BALAK_POSITION, position);
							setResult(UPDATE_RESULTCODE, intent);
							finish();
							Toast.makeText(AddBlackActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(AddBlackActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
						}
					}
				}

				private void getData() {
					edNum = etBlackNum.getText().toString().trim();
					blackEntity.num = edNum;
					
					checkedRadioButtonId = rgType.getCheckedRadioButtonId();
					
					switch(checkedRadioButtonId){
					
					case R.id.rb_call:
						blackEntity.type = CALL;
						break;
					case R.id.rb_sms:
						blackEntity.type = SMS;
						break;
					case R.id.rb_all:
						blackEntity.type = ALL;
						break;
						
					default:
						break;
					}
				}
			});
		}


		private void initView() {
			setContentView(R.layout.activity_add_black);
			etBlackNum = (EditText) findViewById(R.id.et_black_num);
			rgType = (RadioGroup) findViewById(R.id.rg_type);
			rbCall = (RadioButton) findViewById(R.id.rb_call);
			rbSms = (RadioButton) findViewById(R.id.rb_sms);
			rbAll = (RadioButton) findViewById(R.id.rb_all);
			btnOkBlack = (Button) findViewById(R.id.btn_ok_black);
			btnCancelBlack = (Button) findViewById(R.id.btn_cancel_black);
		}
}
