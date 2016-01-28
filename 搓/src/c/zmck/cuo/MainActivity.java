package c.zmck.cuo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
	float scaleX;
	float scaleY;
	ImageView imageView;
	Bitmap coverBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.before);
		
		coverBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		
		Canvas canvas = new Canvas(coverBitmap);
		Paint paint = new Paint();
		Matrix matrix = new Matrix();
		matrix.setScale(1, 1);
		canvas.drawBitmap(bitmap, matrix, paint);
		
		imageView.setImageBitmap(coverBitmap);
		
		int imageWidth = bitmap.getWidth();
		int imageHeigth = bitmap.getHeight();
		
		//WindowManager manager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		int phoneWidth = this.getWindowManager().getDefaultDisplay().getWidth();
		int phoneHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		
		
		
		scaleX = imageWidth/(float)phoneWidth;
		scaleY = imageHeigth/(float)phoneHeight;
		
		
		
		imageView.setOnTouchListener(new MyOnTouchListener());
	}

	class MyOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
				
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_DOWN:
				
				int rawX = (int) event.getRawX();
				int rawY = (int) event.getRawY();
				
				int realX = (int) (rawX*scaleX);
				int realY = (int) (rawY*scaleY);
				
				for(int i = -10;i<=10;i++){
					
					for (int j = -10;j<=10;j++){
						setImage(realX,realY,i,j);
					}
				}
				imageView.setImageBitmap(coverBitmap);
				break;
			}
			
			
			
			return true;
		}

		private void setImage(int realX, int realY, int i, int j) {
			// TODO Auto-generated method stub
			int changeX = realX+i;
			int changeY = realY+j;
			if(changeX<0){
				changeX = 0;
			}else if(changeY<0){
				changeY = 0;
			}else if(changeX>coverBitmap.getWidth()-1){
				changeX = coverBitmap.getWidth()-1;
			}else if(changeY>coverBitmap.getHeight()-1){
				changeY = coverBitmap.getHeight()-1;
			}
			coverBitmap.setPixel(changeX, changeY, Color.TRANSPARENT);
		}}
	
}
