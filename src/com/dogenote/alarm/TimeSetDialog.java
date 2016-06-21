package com.dogenote.alarm;

import java.util.Calendar;

import com.example.dogenote.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.dogenote.alarm.Utils;

public class TimeSetDialog extends Dialog {

	private TimeSetDialog timeSetDialog = null;
	public Calendar calendar;
	TimePicker timePicker;
	Button dateSetButton, positiveButton, negativeButton;
	String date;
	public String alerttime = null;
	public String alerttime_trans;
	TimeInfo timeInfo = new TimeInfo();
	public TimeSetDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.timeset);
		
		timeSetDialog = this;
		this.setTitle("Please choose time");
		
		calendar = Calendar.getInstance();
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		dateSetButton = (Button) findViewById(R.id.dateButton);
		positiveButton = (Button) findViewById(R.id.positiveButton);
		negativeButton = (Button) findViewById(R.id.negativeButton);
		
		//初始化时间设置
		
		calendar.setTimeInMillis(System.currentTimeMillis());
		dateSetButton.setText(Utils.toDateString(calendar));
		int hour  = calendar.get(calendar.HOUR_OF_DAY);
		int minute = calendar.get(calendar.MINUTE);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);		
		
		
		
	
	dateSetButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			new DatePickerDialog(getContext(), new OnDateSetListener(){
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					//设置日期
					calendar.set(year, monthOfYear, dayOfMonth);
					date = Utils.toDateString(calendar);
					dateSetButton.setText(date);
				}}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
		}
	});
	positiveButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
			calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
			alerttime = calendar.getTimeInMillis()+"";
			timeInfo = new TimeInfo();
			timeInfo.setAlerttime(alerttime);
			alerttime_trans = timeInfo.getAlerttime();
			timeSetDialog.cancel();
			}
	});
	negativeButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			timeSetDialog.cancel();
		}
	});
	
	}
}
