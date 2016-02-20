package c.zmck.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class EllipsizeTextView extends TextView {

	public EllipsizeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EllipsizeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if(focused){
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		if(hasWindowFocus){
		super.onWindowFocusChanged(hasWindowFocus);
		}
	}

}
