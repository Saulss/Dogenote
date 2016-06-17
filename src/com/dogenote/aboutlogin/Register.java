package com.dogenote.aboutlogin;

import com.dogenote.web.WebService;
import com.example.dogenote.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{

	// 登陆按钮
	private Button logbtn;
	// 调试文本，注册文本
	private TextView infotv, regtv;
	// 显示用户名和密码
	EditText username, password;
	// 创建等待框
	private ProgressDialog dialog;
	// 返回的数据
	private String info;
	// 返回主线程更新数据
	private static Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// 获取控件
		username = (EditText) findViewById(R.id.re_edituser);
		password = (EditText) findViewById(R.id.re_editpasswd);
		logbtn = (Button) findViewById(R.id.re_btregister);
//		regtv = (TextView) findViewById(R.id.register);
//		infotv = (TextView) findViewById(R.id.info);
	    
	
	

		// 设置按钮监听器
		logbtn.setOnClickListener(this);
//		regtv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
			dialog = new ProgressDialog(this);
			dialog.setTitle("prompt");
			dialog.setMessage(" please wait...");
			dialog.setCancelable(false);
			dialog.show();
			// 创建子线程，分别进行Get和Post传输
			new Thread(new MyThread()).start();
			
	}

	// 子线程接收数据，主线程修改数据
	public class MyThread implements Runnable {
		@Override
		public void run() {
			info = WebService.executeHttpGet("RegLet", username.getText().toString(), password.getText().toString());
			// info =
			// WebServicePost.executeHttpPost(username.getText().toString(),
			// password.getText().toString());
			handler.post(new Runnable() {
				@Override
				public void run() {
					// 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
//					infotv.setText(info);
					Toast toast = Toast.makeText(Register.this, info, Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					dialog.dismiss();
					
				}
			});
		}
	}

	// 检测网络
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
}