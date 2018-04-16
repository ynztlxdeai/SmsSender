package com.vincent.luoxiang.smsmanager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener
{

    private static final int ONE_DAY = 1000 * 60 * 60 * 24;
    //【铁路客服】订单EF18430720,罗享您已购4月15日G6533次5车2A号,广州南站14:55开。
    private static final String SMS_TMPLATE = "【铁路客服】订单EF%s,%s您已购%s月%s日%s次%s车%s,%s站%s开。";
    private EditText mEtAdd;
    private EditText mEtContent;
    private View mSend;
    private  boolean mModeNormal;
    private EditText mName;
    private EditText mMonth;
    private EditText mDay;
    private EditText mNum;
    private EditText mCar;
    private EditText mSeat;
    private EditText mStart;
    private EditText mTime;
    private View mLl;
    private String mDefaultSmsApp;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        mName = (EditText) findViewById(R.id.et_name);
        mMonth = (EditText) findViewById(R.id.et_month);
        mDay = (EditText) findViewById(R.id.et_day);
        mNum = (EditText) findViewById(R.id.et_num);
        mCar = (EditText) findViewById(R.id.et_car);
        mSeat = (EditText) findViewById(R.id.et_seat);
        mStart = (EditText) findViewById(R.id.et_start);
        mTime = (EditText) findViewById(R.id.et_time);

        mSend = findViewById(R.id.btn_send);
        mLl = findViewById(R.id.ll_input_tamplate);
    }

    /**
     * 初始化数据
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initData() {
        mDefaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);
        Intent intent        = new Intent( "android.provider.Telephony.ACTION_CHANGE_DEFAULT");
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, this.getPackageName());
        startActivity(intent);
    }

    /**
     * 初始化监听
     */
    public void initListener() {
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!mModeNormal){
            if (TextUtils.isEmpty(mEtAdd.getText().toString()) ||
                    TextUtils.isEmpty(mEtContent.getText().toString())){
                return;
            }
        }else {
            if (TextUtils.isEmpty(mEtAdd.getText().toString()) ||
                    TextUtils.isEmpty(mName.getText().toString()) ||
                    TextUtils.isEmpty(mMonth.getText().toString()) ||
                    TextUtils.isEmpty(mDay.getText().toString()) ||
                    TextUtils.isEmpty(mNum.getText().toString()) ||
                    TextUtils.isEmpty(mCar.getText().toString()) ||
                    TextUtils.isEmpty(mSeat.getText().toString()) ||
                    TextUtils.isEmpty(mStart.getText().toString()) ||
                    TextUtils.isEmpty(mTime.getText().toString())
                    ){



                return;
            }
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
        String content = "";
        if (!mModeNormal){
            content = mEtContent.getText().toString();
        }else {
            Random random = new Random();
            StringBuffer str = new StringBuffer();
            for(int i=0;i<8;i++){
                str.append(random.nextInt(10));
            }
            content = String.format(SMS_TMPLATE , str.toString() ,
                                    mName.getText().toString() ,
                                    mMonth.getText().toString() ,
                                    mDay.getText().toString() ,
                                    mNum.getText().toString() ,
                                    mCar.getText().toString() ,
                                    mSeat.getText().toString() ,
                                    mStart.getText().toString() ,
                                    mTime.getText().toString()
            );
        }

        values.put("body", content);

        Uri uri1 = resolver.insert(uri, values);
        System.out.println(uri1.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_mode){
            mModeNormal = !mModeNormal;
            if (mModeNormal){
                mLl.setVisibility(View.VISIBLE);
                mEtContent.setVisibility(View.GONE);
            }else {
                mLl.setVisibility(View.GONE);
                mEtContent.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
