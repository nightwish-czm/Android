package c.zmck.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import c.zmck.mobilesafe.R;

public abstract class BaseSettingGuideActivity extends Activity {

	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO Auto-generated method stub
					float x1 = e1.getX();
					float x2 = e2.getX();
					if(x1>(x2+200)){
						next(null);
						return true;
					}else if(x2>(x1+200)){
						pre(null);
						return true;
					}
					return true;
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}
	
	public void next(View view) {
		if(moveToNext()){
			return;
		}
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.next_out);
	}

	public abstract boolean moveToNext();

	public void pre(View view) {
		if(moveToPre()){
			return;
			}
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
	}
	
	public abstract boolean moveToPre();;
}
