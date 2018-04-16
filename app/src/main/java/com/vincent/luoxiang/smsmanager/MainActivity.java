package com.vincent.luoxiang.smsmanager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener
{

    private static final int ONE_DAY = 1000 * 60 * 60 * 24;
    //【铁路客服】订单EF18430720,罗享您已购4月15日G6533次5车2A号,广州南站14:55开。
    private static final String SMS_TMPLATE = "【铁路客服】订单EF%d,%s您已购%s月%s日%s次%s车%s,%s站%s开。";
    private EditText mEtAdd;
    private EditText mEtContent;
    private View mSend;
    private  boolean mModeNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
        initListener();
    }


    /**
     * 初始化view
     */
    public void initView() {
        mEtAdd = (EditText) findViewById(R.id.et_address);
        mEtContent = (EditText)findViewById(R.id.et_content);
        mSend = findViewById(R.id.btn_send);
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化监听
     */
    public void initListener() {
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(mEtAdd.getText().toString()) || TextUtils.isEmpty(mEtContent.getText().toString())){
            return;
        }
        // 需要 使用 后门程序 --- 需要 使用 contentResolver
        ContentResolver resolver = getContentResolver();

        // content://
        Uri uri = Uri.parse("content://sms");

        ContentValues values = new ContentValues();

        // address, date, type, body
        values.put("address", mEtAdd.getText().toString());
        values.put("date", System.currentTimeMillis() - ONE_DAY);
        values.put("type", "1");
        values.put("body", mEtContent.getText().toString());

        resolver.insert(uri, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_mode){

        }
        return super.onOptionsItemSelected(item);
    }
}
