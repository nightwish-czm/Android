package c.zmck.activity;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import c.zmck.constants.Constants;
import c.zmck.entity.VersionEntity;
import c.zmck.mobilesafe.R;
import c.zmck.utils.PackageUtil;
import c.zmck.utils.SharePreferenceUtil;

public class SplashActivity extends Activity {
	
	private TextView tv;
	private static final int UPDATE = 1;
	private static final int LOAD_MAIN = 2;
	Handler handler = new Handler(){
		public void handleMessage(Message message) {
			
			switch(message.what){
			
			case UPDATE :
				AlertDialog.Builder builder = new Builder(SplashActivity.this);
				builder.setTitle("检查到新的版本！");
				builder.setMessage("是否立即更新？");
				builder.setCancelable(false);
				builder.setPositiveButton("确定", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						update();
					}

				});
				builder.setNegativeButton("取消",new OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						
						loadMain();
					};
				} );
				builder.show();
				break;
				
			case CANCELED:
				loadMain();
				
				break;
				
			case LOAD_MAIN:
				loadMain();
			}
			
		};
	};
	private static final int INSTALL_SUCCESS = 3;
	private static final int CANCELED = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
//		加载splash界面信息
		tv = (TextView) findViewById(R.id.tv_splash);
		PackageInfo info = PackageUtil.getVersion(this);
		tv.setText(info.versionName+" "+info.versionCode);
		ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "alpha", 0.0f,1.0f,0.0f);
		animator.setDuration(3000);
		animator.setRepeatCount(-1);
		animator.start();
		//如果设置自动更新就查询，不设置直接进入主界面
		if(SharePreferenceUtil.readBoolean(SplashActivity.this, Constants.AUTO_UPDATE_APP)){
		checkUpdate();
		}else{
			loadMain();
		}
	}

	private void loadMain() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this,HomeActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}
		}, 2000);
		
	}

	private void checkUpdate() {
		//		联网检查更新
		new Thread(){
			private HttpURLConnection connection;

			public void run() {
				try {
					URL url = new URL(Constants.VERSION_PATH);
				    connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(2000);
					connection.setReadTimeout(2000);
					connection.connect();
					InputStream inputStream = connection.getInputStream();
					VersionEntity remoteVersion = parseJson(inputStream);
					System.out.println(remoteVersion.getVersionName());
					if((PackageUtil.getVersion(SplashActivity.this).versionCode)<remoteVersion.getVersionCode()){
						handler.sendEmptyMessage(UPDATE);
					}else {
						handler.sendEmptyMessage(LOAD_MAIN);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					loadMain();
				}finally {
					connection.disconnect();
				}
				
			};
			
		}.start();
	}

	private void update() {
		// TODO Auto-generated method stub
		
		final ProgressDialog progress = new ProgressDialog(SplashActivity.this);
		progress.setTitle("正在下载..");
		progress.setCancelable(false);
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.show();
		HttpUtils httpUtils = new HttpUtils();
		String target = Environment.getExternalStorageDirectory()+"/"+new Date().getTime()+".apk";
		httpUtils.download(Constants.APK_PATH, target, new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setDataAndType(Uri.fromFile(arg0.result),"application/vnd.android.package-archive" );
				//intent.setData(Uri.fromFile(arg0.result));
				SplashActivity.this.startActivityForResult(intent, 0);
				
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				super.onLoading(total, current, isUploading);
				
				progress.setMax((int) total);
				progress.setProgress((int) current);
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		
		if(RESULT_CANCELED == resultCode){
			handler.sendEmptyMessage(CANCELED);
		}
		
		
	}
	
	private VersionEntity parseJson(InputStream inputStream) {
		// TODO Auto-generated method stub
		String versionName = null;
		int versionCode = 0;
		String message = null;
		VersionEntity remoteVersion = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
			StringBuffer buffer = new StringBuffer();
			String temp = null;
			while((temp = reader.readLine())!=null){
				buffer.append(temp);
			}
			JSONObject jsonObject = new JSONObject(buffer.toString());
			versionName = jsonObject.getString("versionName");
			versionCode = jsonObject.getInt("versionCode");
			message = jsonObject.getString("message");
			remoteVersion = new VersionEntity(versionName, versionCode, message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("zmck", versionName+versionCode+message);
		return remoteVersion;
		
	}
	
}
