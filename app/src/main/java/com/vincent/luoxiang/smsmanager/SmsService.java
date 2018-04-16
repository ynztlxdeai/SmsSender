package com.vincent.luoxiang.smsmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * projectName: 	    SmsSender
 * packageName:	        com.vincent.luoxiang.smsmanager
 * className:	        SmsService
 * author:	            Luoxiang
 * time:	            2018/4/16	下午1:50
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    luoxiang
 * upDate:	            2018/4/16
 * upDateDesc:	        TODO
 */


public class SmsService
        extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
