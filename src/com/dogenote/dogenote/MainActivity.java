package com.dogenote.dogenote;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dogenote.extra.NoteDateBaseHelper;
import com.dogenote.extra.noteEdit;
import com.example.dogenote.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener, OnItemLongClickListener, OnItemClickListener {

	private ListView listview;
	private SimpleAdapter simple_adapter;
	private List<Map<String, Object>> dataList;
	private TextView tv_content;
	private NoteDateBaseHelper DbHelper;
	private SQLiteDatabase DB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_content = (TextView) findViewById(R.id.tv_content);
		listview = (ListView) findViewById(R.id.listview);
		dataList = new ArrayList<Map<String, Object>>();
		DbHelper = new NoteDateBaseHelper(this);
		DB = DbHelper.getReadableDatabase();

		listview.setOnItemClickListener(this);// ?
		listview.setOnItemLongClickListener(this);
		
		ImageView btAccount = (ImageView) findViewById(R.id.imgBtAccount);
		ImageView btEdit = (ImageView) findViewById(R.id.imgBtEditnew);

		btAccount.setOnClickListener(this);
		btEdit.setOnClickListener(this);
	

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_about:

			return true;
		
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgBtEditnew:
			Intent intentEdit = new Intent();
			intentEdit.setClass(MainActivity.this, com.dogenote.extra.noteEdit.class);
			Bundle bundle = new Bundle();
			bundle.putString("info", "");
			bundle.putInt("enter_state", 0);
			intentEdit.putExtras(bundle);
			startActivity(intentEdit);
			finish();
		case R.id.imgBtSchedule:
			break;
		case R.id.imgBtAccount:
			Intent intentAt = new Intent();
			intentAt.setClass(MainActivity.this, com.dogenote.aboutlogin.LoginActivity.class);
			startActivity(intentAt);
			finish();
		}
	}

	// �����½��ʼǺ��б�Ĵ���

	// ��activity��ʾ��ʱ�����listview
	@Override
	protected void onStart() {
		super.onStart();
		RefreshNotesList();
	}


	// ˢ��listview
	public void RefreshNotesList() {
		// ���dataList�Ѿ��е����ݣ�ȫ��ɾ��
		// ���Ҹ���simp_adapter
		int size = dataList.size();
		if (size > 0) {
			dataList.removeAll(dataList);
			simple_adapter.notifyDataSetChanged();
		}

		// �����ݿ��ȡ��Ϣ
		Cursor cursor = DB.query("note", null, null, null, null, null, null);
		startManagingCursor(cursor);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("content"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tv_content", name);
			map.put("tv_date", date);
			dataList.add(map);
		}
		simple_adapter = new SimpleAdapter(this, dataList, R.layout.item, new String[] { "tv_content", "tv_date" },
				new int[] { R.id.tv_content, R.id.tv_date });
		listview.setAdapter(simple_adapter);
	}

	// ���listview��ĳһ��ĵ�������¼�
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// ��ȡlistview�д˸�item�е�����
		String content = listview.getItemAtPosition(arg2) + "";
		String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));

		Intent myIntent = new Intent(MainActivity.this, noteEdit.class);
		Bundle bundle = new Bundle();
		bundle.putString("info", content1);
		bundle.putInt("enter_state", 1);
		myIntent.putExtras(bundle);
		startActivity(myIntent);

	}

	// ���listview��ĳһ�ʱ��ĵ���¼�
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		Builder builder = new Builder(this);
		builder.setTitle("ɾ������־");
		builder.setMessage("ȷ��ɾ����");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ��ȡlistview�д˸�item�е�����
				// ɾ�����к�ˢ��listview������
				String content = listview.getItemAtPosition(arg2) + "";
				String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));
				DB.delete("note", "content = ?", new String[] { content1 });
				RefreshNotesList();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create();
		builder.show();
		return true;
	}
}
