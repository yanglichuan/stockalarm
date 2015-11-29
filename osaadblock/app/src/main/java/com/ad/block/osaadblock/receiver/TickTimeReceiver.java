package com.ad.block.osaadblock.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ad.block.osaadblock.HttpRequestManager;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.utils.NotificationUtils;
import com.ad.block.osaadblock.utils.StockNewSettingUtils;
import com.ad.block.osaadblock.utils.StockUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;

public class TickTimeReceiver extends BroadcastReceiver {
    public int iTickCount =0;

    public int timeDis = 1;

    private Context mContent;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContent = context;
        if(mLL == null){
            mLL = new IResultListner() {
                @Override
                public void onResult(final String realCode,
                                     final float buyprice,
                                     final float low,
                                     final float high,
                                     String result) {
                    todoYujing(realCode,buyprice,low,high,result);
                }
            };
        }

        timeDis = StockNewSettingUtils.getTimeBetween(context);

        iTickCount ++;
        if(iTickCount == 3600){
            iTickCount = 0;
        }
        if(iTickCount % timeDis == 0){
            //通知更新ui
            //通知各个activity
            EventBus.getDefault().post(new BlockEvent());

            pullFromNetWrap();
            //提醒时间

            notifyTime();
        }
    }  //如果无网络连接activeInfo为null



    private void notifyTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int xx =  calendar.get(Calendar.HOUR_OF_DAY);
        int xx2 = calendar.get(Calendar.MINUTE) + xx*60;

        if(xx2 > (8*60+55) && xx2 < (8*60+59)){
            if(StockNewSettingUtils.getTime_0900(mContent)){
                String tip = null;
                tip = "开盘时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(999, notification);
            }
        }

        if(xx2 > (11*60+25) && xx2 < (11*60+29)){
            if(StockNewSettingUtils.getTime_1130(mContent)){
                String tip = null;
                tip = "上午结束时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(999, notification);
            }
        }

        if(xx2 > (12*60+55) && xx2 < (12*60+59)){
            if(StockNewSettingUtils.getTime_1300(mContent)){
                String tip = null;
                tip = "下午开盘时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(999, notification);
            }
        }
        if(xx2 > (14*60+55) && xx2 < (14*60+59)){
            if(StockNewSettingUtils.getTime_1500(mContent)){
                String tip = null;
                tip = "下午结束时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(999, notification);
            }
        }
        if(xx2 > (14*60+25) && xx2 < (14*60+29)){
            if(StockNewSettingUtils.getTime_1430(mContent)){
                String tip = null;
                tip = "神奇时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(999, notification);
            }
        }
    }

    private void pullFromNetWrap(){
        String str = StockUtils.getStockCode1(mContent);
        String real = StockNewSettingUtils.getStockExchange(mContent,str) + str;
        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice1(mContent));
            float low =Float.parseFloat(StockUtils.getStockKuisun1(mContent));
            float hight = Float.parseFloat(StockUtils.getStockYingli1(mContent));
            pullFromNet(real,ff,low,hight,mLL);
        }catch (Exception e){
            e.printStackTrace();
        }

        str = StockUtils.getStockCode2(mContent);
        real = StockNewSettingUtils.getStockExchange(mContent,str) + str;

        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice2(mContent));
            float low =Float.parseFloat(StockUtils.getStockKuisun2(mContent));
            float hight = Float.parseFloat(StockUtils.getStockYingli2(mContent));
            pullFromNet(real,ff,low,hight,mLL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private IResultListner mLL;

    static interface IResultListner{
        void onResult(final String realCode,
                      final float buyprice,
                      final float low,
                      final float high,
                      String result);
    }
    private void todoYujing(final String realCode,
                            final float buyprice,
                            final float low,
                            final float high,
                            final String res){

                if (!TextUtils.isEmpty(res)) {
                    String[] splits = res.split(",");
                    if (splits.length > 3) {
                        String currentPrice = splits[3];
                        Float ztPrice = Float.parseFloat(splits[2]);
                        Float jtPrice = Float.parseFloat(currentPrice);
                        // Float zzf = (jtPrice - ztPrice)/ztPrice*100;
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        //String p=decimalFormat.format(zzf);//format 返回的是字符串


                        Float shipeishizhuan = (jtPrice - buyprice) / buyprice * 100;
                        decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        String p = decimalFormat.format(shipeishizhuan);//format 返回的是字符串
                        //报警
                        if (shipeishizhuan < low) {
                            String tip = null;
                            tip = realCode + "已经超跌了";
                            Notification notification =
                                    NotificationUtils.creatNotify(mContent, tip,true);
                            NotificationManager manager =
                                    (NotificationManager) mContent.getSystemService(
                                            mContent.NOTIFICATION_SERVICE);
                            manager.notify(444, notification);
                        }
                        if (shipeishizhuan > high) {
                            String tip = null;
                            tip = realCode + "已经超涨了";
                            Notification notification =
                                    NotificationUtils.creatNotify(mContent, tip,true);
                            NotificationManager manager =
                                    (NotificationManager) mContent.getSystemService(
                                            mContent.NOTIFICATION_SERVICE);
                            manager.notify(333, notification);
                        }
                    }
                }
    }


    private void pullFromNet(final String realCode,
                             final float buyprice,
                             final float low,
                             final float high,
                             final IResultListner ll){
        HttpRequestManager.getInstance(mContent.getApplicationContext()).request_stock(realCode, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response != null && response.body() != null){
                    final String res = new String(response.body().string());
                    if(ll != null){
                        ll.onResult(realCode,buyprice,low,high,res);
                    }
                }
            }
        });
    }
}


