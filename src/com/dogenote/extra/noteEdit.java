package com.dogenote.extra;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dogenote.alarm.*;

import java.security.KeyStore.PrivateKeyEntry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dogenote.dogenote.MainActivity;
import com.example.dogenote.R;

public class noteEdit extends Activity implements OnClickListener {
	private TextView tv_date;
	private EditText et_content;
	private ImageView btn_ok;
	private ImageView btn_cancel;
	private NoteDateBaseHelper DBHelper;
	private TimeSetDialog timeSetDialog = null;
	private TimeInfo timeInfo;
	private String content;
	private Date date;
//	private static String alerttime;
	Calendar calendar = null;
//	protected String alerttime = "";

	public int enter_state = 0;// 鐢ㄦ潵鍖哄垎鏄柊寤轰竴涓猲ote杩樻槸鏇存敼鍘熸潵鐨刵ote
	public String last_content;// 鐢ㄦ潵鑾峰彇edittext鍐呭

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		InitView();
	}

	private void InitView() {
		tv_date = (TextView) findViewById(R.id.tv_date);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_ok = (ImageView) findViewById(R.id.btn_ok);
		btn_cancel = (ImageView) findViewById(R.id.btn_cancel);
		DBHelper = new NoteDateBaseHelper(this);

		// 鑾峰彇姝ゆ椂鏃跺埢鏃堕棿
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = sdf.format(date);
		tv_date.setText(dateString);

		// 鎺ユ敹鍐呭鍜宨d
		Bundle myBundle = this.getIntent().getExtras();
		last_content = myBundle.getString("info");
		enter_state = myBundle.getInt("enter_state");
		et_content.setText(last_content);

		btn_cancel.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_ok:
			SQLiteDatabase db = DBHelper.getReadableDatabase();
			// 鑾峰彇edittext鍐呭
			content = et_content.getText().toString();

			// 娣诲姞涓�涓柊鐨勬棩蹇�
			if (enter_state == 0) {
				if (!content.equals("")) {
					// 鑾峰彇姝ゆ椂鏃跺埢鏃堕棿
					date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String dateString = sdf.format(date);

					// 鍚戞暟鎹簱娣诲姞淇℃伅
					ContentValues values = new ContentValues();
					values.put("content", content);
					values.put("date", dateString);
					values.put("alerttime", timeSetDialog.alerttime_trans);
					db.insert("note", null, values);
					alertSet();
					finish();
				} else {
					Toast.makeText(noteEdit.this, "Please input content!", Toast.LENGTH_SHORT).show();
				}
			}
			// 鏌ョ湅骞朵慨鏀逛竴涓凡鏈夌殑鏃ュ織
			else {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String dateString = sdf.format(date);
				
				
				ContentValues values = new ContentValues();
				values.put("content", content);
				values.put("alerttime", timeSetDialog.alerttime_trans);
				db.update("note", values, "content = ? and alerttime = ? ", new String[] { last_content, timeSetDialog.alerttime_trans });
				finish();
			}
			Intent intent = new Intent();
			intent.setClass(this, com.dogenote.dogenote.MainActivity.class);
			startActivity(intent);

			break;
		case R.id.btn_cancel:
			Intent intentAt = new Intent();
			intentAt.setClass(this, com.dogenote.dogenote.MainActivity.class);
			startActivity(intentAt);
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it
		// is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so
		// long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		// case R.id.action_about:
		//
		// return true;
		case R.id.alarm:
			timeSetDialog = new TimeSetDialog(noteEdit.this);

			timeSetDialog.show();


		}
		return false;

	}
	private void alertSet(){
		Intent intent = new Intent("android.intent.action.ALARMRECEIVER");
		intent.putExtra("datetime", date);
	    intent.putExtra("content", content);
	    intent.putExtra("alerttime",timeSetDialog.alerttime_trans);
	    calendar = timeSetDialog.calendar;
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
	    //setRepeating()这里第二个参数不能设置成现在时间，否则闹钟会设置完就开启
	    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),(24 * 60 * 60 * 1000), pendingIntent);
	}
}
