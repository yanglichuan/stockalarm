package com.ad.block.osaadblock.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ad.block.osaadblock.receiver.TickTimeReceiver;
import com.ad.block.osaadblock.utils.NotificationUtils;

public class BlockService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        registerTickReceiver();
        super.onCreate();
    }


    private BroadcastReceiver receiver;
    private void registerTickReceiver(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        receiver = new TickTimeReceiver();
        try {
            registerReceiver(receiver,filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static final int ONGOING_NOTIFICATION = 200;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tip = null;
        tip="股票监控服务已经开启";
        Notification notification = NotificationUtils.creatNotify(BlockService.this,tip);

        startForeground(ONGOING_NOTIFICATION,notification);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
        if(receiver != null){
            try {
                unregisterReceiver(receiver);
                receiver = null;
            }catch (Exception e){
            }
        }
        super.onDestroy();
    }




}


