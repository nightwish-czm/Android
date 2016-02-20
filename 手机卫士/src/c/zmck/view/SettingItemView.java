package c.zmck.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.mobilesafe.R;
import c.zmck.utils.SharePreferenceUtil;

public class SettingItemView extends RelativeLayout {

	private boolean settingItemChecked;
	private ImageView ivSettingItem;

	public SettingItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		TypedArray styledAttributes = context.obtainStyledAttributes(attrs,R.styleable.settingItemStyle);
		
		String title = (String) styledAttributes.getText(R.styleable.settingItemStyle_settingTitle);
		int bG = styledAttributes.getInt(R.styleable.settingItemStyle_settingBG, 0);
		settingItemChecked = styledAttributes.getBoolean(R.styleable.settingItemStyle_isChecked, true);
		
		View.inflate(context, R.layout.view_setting_item, this);
		
		ivSettingItem = (ImageView) findViewById(R.id.iv_setting_item);
		tvSettingItem = (TextView) findViewById(R.id.tv_setting_item);
		
		tvSettingItem.setText(title);
		
		switch(bG){
		case 0:
			tvSettingItem.setBackgroundResource(R.drawable.selector_settingitem_top);
			break;
			
		case 1:
			tvSettingItem.setBackgroundResource(R.drawable.selector_settingitem_middle);
			break;
			
		case 2:
			tvSettingItem.setBackgroundResource(R.drawable.selector_settingitem_bottom);
			break;
		}
		
		styledAttributes.recycle();
		setClickable(true);
		setChecked(settingItemChecked);
	}

	private void setChecked(boolean settingItemChecked) {
		if(settingItemChecked){
			ivSettingItem.setImageResource(R.drawable.on);
		}else {
			ivSettingItem.setImageResource(R.drawable.off);
		}
	}
	
	public boolean getSettingItemChecked(){
		return settingItemChecked;
		}
	
	public void setSettingItemChecked(boolean settingItemChecked){
		this.settingItemChecked = settingItemChecked;
		setChecked(settingItemChecked);
	}
	
	public void changeSettingItemChecked(){
		setSettingItemChecked(!settingItemChecked);
	}
	
	
	public interface OnItemClickListener{
		public void onItemClick(View view);
	}
	private OnItemClickListener mOnItemClickListener;
	private TextView tvSettingItem;
	public void setOnItemClick(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
		if (this.mOnItemClickListener != null) {
			tvSettingItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(v);
//					Toast.makeText(getApplicationContext(), "----", Toast.LENGTH_LONG).show();
//					autoUpdate.changeSettingItemChecked();
//					SharePreferenceUtil.writeBoolean(getApplicationContext(),
//							Constants.AUTO_UPDATE_APP, autoUpdate.getSettingItemChecked());
				}
			});
		}
	}
	
}
