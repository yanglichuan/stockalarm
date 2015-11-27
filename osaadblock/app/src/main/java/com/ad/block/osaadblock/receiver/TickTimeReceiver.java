package com.ad.block.osaadblock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ad.block.osaadblock.event.BlockEvent;

import de.greenrobot.event.EventBus;

public class TickTimeReceiver extends BroadcastReceiver {
    public int iTickCount =0;

    public final int timeDis = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        iTickCount ++;
        if(iTickCount == 3600){
            iTickCount = 0;
        }
        if(iTickCount % timeDis == 0){
            //通知更新ui
            //通知各个activity
            EventBus.getDefault().post(new BlockEvent());
        }

    }  //如果无网络连接activeInfo为null

}


