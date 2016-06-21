package com.dogenote.alarm;

import java.io.IOException;

import com.dogenote.aboutlogin.LoginActivity;
import com.dogenote.aboutlogin.RegisterActivity;
import com.dogenote.dogenote.MainActivity;
import com.dogenote.extra.noteEdit;
import com.example.dogenote.R;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.NotificationManager;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.view.Window;
import android.view.WindowManager;

public class NotificationActivity extends Activity implements OnClickListener{
	
	public static NotificationActivity context = null;
	private MediaPlayer player = new MediaPlayer();
	WakeLock mWakelock;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//需要在AndroidManifest里面设置权限，唤醒屏幕
		PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.FULL_WAKE_LOCK, "AlertDialog");
		        mWakelock.acquire();
//		final Window win = getWindow();
//		win.addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//		        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
//		        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//屏幕解锁，需要设置权限
		KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);  
		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("AlertDialog");  
		keyguardLock.disableKeyguard();  
		context = this;
		try{
			Uri localUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM);
			if((player != null) && (localUri != null))
			{
					player.setDataSource(context,localUri);
					player.prepare();
					player.setLooping(false);
					player.start();
			}
			
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(this)
			        .setSmallIcon(R.drawable.dogenote)
			        .setContentTitle("DogeNotes")
			        .setContentText(getIntent().getStringExtra("content"));
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(this, MainActivity.class);
			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			// Adds the back stack for the Intent (but not the Intent itself)
//			stackBuilder.addParentStack(LoginActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(1, mBuilder.build());
			
			

		}catch (IllegalArgumentException localIllegalArgumentException)
	    {
		      localIllegalArgumentException.printStackTrace();
		    }
		    catch (SecurityException localSecurityException)
		    {
		      localSecurityException.printStackTrace();
		    }
		    catch (IllegalStateException localIllegalStateException)
		    {
		      localIllegalStateException.printStackTrace();
		    } catch (IOException e) 
		    {
				e.printStackTrace();
		    }
		}
//	private void showNotification() {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onClick(DialogInterface dialog, int which) {
//		// TODO Auto-generated method stub
//		switch(which){
//		case DialogInterface.BUTTON1:
//		{
//			Intent intent = new Intent(NotificationActivity.this, noteEdit.class);
//			Bundle b = new Bundle();
//			b.putString("date",getIntent().getStringExtra("date"));
//			b.putString("content", getIntent().getStringExtra("content"));
//			b.putString("alerttime",getIntent().getStringExtra("alerttime"));
//			intent.putExtra("android.intent.extra.INTENT", b);
//			startActivity(intent);                                //启动转到的Activity
//			finish();
//		}
//		case DialogInterface.BUTTON2:
//		{
////			mWakelock.release();
//			player.stop();
//			finish();
//		}
//	  }
//	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
//		player.stop();
		Intent intent = new Intent(NotificationActivity.this, noteEdit.class);
		Bundle b = new Bundle();
		b.putString("date",getIntent().getStringExtra("date"));
		b.putString("content", getIntent().getStringExtra("content"));
		b.putString("alerttime",getIntent().getStringExtra("alerttime"));
		intent.putExtra("android.intent.extra.INTENT", b);
		startActivity(intent);                                //启动转到的Activity
		finish();
	}
}