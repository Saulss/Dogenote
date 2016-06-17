package com.dogenote.extra;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.dogenote.R;

public class noteEdit extends Activity implements OnClickListener {
    private TextView tv_date;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private NoteDateBaseHelper DBHelper;
    public int enter_state = 0;//�����������½�һ��note���Ǹ���ԭ����note
    public String last_content;//������ȡedittext����

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        InitView();
    }

    private void InitView() {
        tv_date = (TextView) findViewById(R.id.tv_date);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        DBHelper = new NoteDateBaseHelper(this);

        //��ȡ��ʱʱ��ʱ��
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = sdf.format(date);
        tv_date.setText(dateString);

        //�������ݺ�id
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
                // ��ȡedittext����
                String content = et_content.getText().toString();

                // ���һ���µ���־
                if (enter_state == 0) {
                    if (!content.equals("")) {
                        //��ȡ��ʱʱ��ʱ��
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String dateString = sdf.format(date);

                        //�����ݿ������Ϣ
                        ContentValues values = new ContentValues();
                        values.put("content", content);
                        values.put("date", dateString);
                        db.insert("note", null, values);
                        finish();
                    } else {
                        Toast.makeText(noteEdit.this, "������������ݣ�", Toast.LENGTH_SHORT).show();
                    }
                }
                // �鿴���޸�һ�����е���־
                else {
                    ContentValues values = new ContentValues();
                    values.put("content", content);
                    db.update("note", values, "content = ?", new String[]{last_content});
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
}
