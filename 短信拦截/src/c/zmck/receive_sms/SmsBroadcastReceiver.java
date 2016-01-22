package c.zmck.receive_sms;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.provider.Telephony.Sms;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		
		for(Object pdu:pdus){
			SmsMessage message =  SmsMessage.createFromPdu((byte[]) pdu);
			String body = message.getMessageBody();
			String tel = message.getOriginatingAddress();
			
			
			
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(tel, null, body, null, null);
			
			AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
			String path = "http://192.168.43.190:8080/test/smsreceiver";
			
			RequestParams params = new RequestParams();
			params.put("TEL", tel);
			params.put("BODY", body);
			asyncHttpClient.post(path, params,new ResponseHandlerInterface() {
				
				@Override
				public void setUseSynchronousMode(boolean useSynchronousMode) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setRequestURI(URI requestURI) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void setRequestHeaders(Header[] requestHeaders) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBody) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendStartMessage() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendRetryMessage(int retryNo) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendResponseMessage(HttpResponse response) throws IOException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendProgressMessage(int bytesWritten, int bytesTotal) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendFinishMessage() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void sendCancelMessage() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean getUseSynchronousMode() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public URI getRequestURI() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public Header[] getRequestHeaders() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			
			
			
		}
		
		
		
		
	}

}
