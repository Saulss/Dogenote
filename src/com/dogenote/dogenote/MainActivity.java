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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dogenote.aboutlogin.InfoActivity;
import com.dogenote.aboutlogin.LoginActivity;
import com.dogenote.aboutlogin.UserService;
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
		  private UserService userService;
		  
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					setContentView(R.layout.activity_main);
					
					tv_content = (TextView) findViewById(R.id.tv_content);
					listview = (ListView) findViewById(R.id.listview);
					dataList = new ArrayList<Map<String, Object>>();
					DbHelper = new NoteDateBaseHelper(this);
					DB = DbHelper.getReadableDatabase();
					
					userService = new UserService(this);
					
					listview.setOnItemClickListener(this);// ?
					listview.setOnItemLongClickListener(this);
					
					ImageView btAccount = (ImageView) findViewById(R.id.imgBtAccount);
					ImageView btEdit = (ImageView) findViewById(R.id.imgBtEditnew);
					
					btAccount.setOnClickListener(this);
					btEdit.setOnClickListener(this);
					
		  }
		  
		  @Override
		  public boolean onCreateOptionsMenu(Menu menu) {
					// Inflate the menu; this adds items to the action bar if it
					// is present.
					// getMenuInflater().inflate(R.menu.main, menu);
					MenuInflater inflater = getMenuInflater();
					inflater.inflate(R.menu.main, menu);
					
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
					case R.id.accounts:
						boolean change_view = userService.ACSelect();
						Intent intentAt = new Intent();
						if (change_view) {
								  intentAt.setClass(this, InfoActivity.class);
								  this.startActivity(intentAt);
								  // this.finish();
						} else {
								  
								  intentAt.setClass(MainActivity.this,
													  com.dogenote.aboutlogin.LoginActivity.class);
								  startActivity(intentAt);
								  // finish();
						}
						break;
					case R.id.edit:
						Intent intentEdit = new Intent();
						intentEdit.setClass(MainActivity.this, com.dogenote.extra.noteEdit.class);
						Bundle bundle = new Bundle();
						bundle.putString("info", "");
						bundle.putInt("enter_state", 0);
						intentEdit.putExtras(bundle);
						startActivity(intentEdit);
						// finish();
						break;  
					case R.id.interesting:
						Toast.makeText(this, " ギリギリ愛 ~キリキリ舞", Toast.LENGTH_SHORT).show();
						break;
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
										// finish();
							  case R.id.imgBtSchedule:
										break;
							  case R.id.imgBtAccount:
										boolean change_view = userService.ACSelect();
										Intent intentAt = new Intent();
										if (change_view) {
												  intentAt.setClass(this, InfoActivity.class);
												  this.startActivity(intentAt);
												  // this.finish();
										} else {
												  
												  intentAt.setClass(MainActivity.this,
																	  com.dogenote.aboutlogin.LoginActivity.class);
												  startActivity(intentAt);
												  // finish();
										}
										
							  default:
										break;
					}
		  }
		  
		  // **********************************************************鍏充簬鏂板缓绗旇鍜屽垪琛ㄧ殑浠ｇ爜******************************************
		  
		  // 鍦╝ctivity鏄剧ず鐨勬椂鍊欐洿鏂癓istView
		  @Override
		  protected void onStart() {
					super.onStart();
					RefreshNotesList();
		  }
		  
		  // 鍒锋柊ListView
		  public void RefreshNotesList() {
					// 濡傛灉dataList宸茬粡鏈夌殑鍐呭锛屽叏閮ㄥ垹鎺�
					// 骞朵笖鏇存柊simp_adapter
					int size = dataList.size();
					if (size > 0) {
							  dataList.removeAll(dataList);
							  simple_adapter.notifyDataSetChanged();
					}
					
					// 浠庢暟鎹簱璇诲彇淇℃伅
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
					simple_adapter = new SimpleAdapter(this, dataList, R.layout.item,
										new String[] { "tv_content", "tv_date" },
										new int[] { R.id.tv_content, R.id.tv_date });
					listview.setAdapter(simple_adapter);
		  }
		  
		  // 鐐瑰嚮ListView涓煇涓�椤圭殑鐐瑰嚮鐩戝惉浜嬩欢
		  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// 鑾峰彇ListView涓涓猧tem涓殑鍐呭
					String content = listview.getItemAtPosition(arg2) + "";
					String content1 = content.substring(content.indexOf("=") + 1, content.indexOf(","));
					
					Intent myIntent = new Intent(MainActivity.this, noteEdit.class);
					Bundle bundle = new Bundle();
					bundle.putString("info", content1);
					bundle.putInt("enter_state", 1);
					myIntent.putExtras(bundle);
					startActivity(myIntent);
					
		  }
		  
		  // 鐐瑰嚮ListView涓煇涓�椤归暱鏃堕棿鐨勭偣鍑讳簨浠�
		  @Override
		  public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
					Builder builder = new Builder(this);
					builder.setTitle("Do you want to delete this note?");
					builder.setMessage("Are you sure?");
					builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
							  @Override
							  public void onClick(DialogInterface dialog, int which) {
										// 鑾峰彇ListView涓涓猧tem涓殑鍐呭
										// 鍒犻櫎璇ヨ鍚庡埛鏂癓istView鐨勫唴瀹�
										String content = listview.getItemAtPosition(arg2) + "";
										String content1 = content.substring(content.indexOf("=") + 1,
															content.indexOf(","));
										DB.delete("note", "content = ?", new String[] { content1 });
										RefreshNotesList();
							  }
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
							  @Override
							  public void onClick(DialogInterface dialog, int which) {
							  }
					});
					builder.create();
					builder.show();
					return true;
		  }
}
