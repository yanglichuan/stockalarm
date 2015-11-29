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

    private final String tianqiWhere = "北京";
    @Override
    public void onReceive(Context context, Intent intent) {
        mContent = context;
        if(mLL == null){
            mLL = new IResultListner() {
                @Override
                public void onStockResult(final String realCode,
                                          final float buyprice,
                                          final float low,
                                          final float high,
                                          String result) {
                    stockYujing(realCode, buyprice, low, high, result);
                }

                @Override
                public void onWhetherResult(String res) {
                    if(!TextUtils.isEmpty(res)){
                        String t = tianqiWhere+" "+res.substring(res.indexOf("<status1>")+"<status1>".length(),res.indexOf("</status1>"))+
                                " "+res.substring(res.indexOf("<status2>")+"<status2>".length(),res.indexOf("</status2>"));
                        Notification notification =
                                NotificationUtils.creatNotify(mContent, t,false);
                        NotificationManager manager =
                                (NotificationManager) mContent.getSystemService(
                                        mContent.NOTIFICATION_SERVICE);
                        manager.notify(notify_whether_id, notification);
                    }


                }
            };
        }

        timeDis = StockNewSettingUtils.getTimeBetween(context);

        iTickCount ++;
        if(iTickCount == 3600){
            iTickCount = 0;
        }
        if(iTickCount % timeDis == 0){

            notifyActivity();

            notifyStock();
            //提醒时间

            notifyTime();

            pullWetherFromNet(tianqiWhere);

            //提醒点餐了
            notifyDingcan();

            //下班记得打卡
            notifyDaka();

        }
    }  //如果无网络连接activeInfo为null


    private void notifyDingcan(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return;
        }

        int xx =  calendar.get(Calendar.HOUR_OF_DAY);
        int xx2 = calendar.get(Calendar.MINUTE) + xx*60;

        if(xx2 > (11*60+30) && xx2 < (8*60+34)){
            String tip = null;
            tip = "点餐了";
            Notification notification =
                    NotificationUtils.creatNotify(mContent, tip,false);
            NotificationManager manager =
                    (NotificationManager) mContent.getSystemService(
                            mContent.NOTIFICATION_SERVICE);
            manager.notify(notify_dingcan_id, notification);
        }
    }



    private void notifyDaka(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return;
        }
        int xx =  calendar.get(Calendar.HOUR_OF_DAY);
        int xx2 = calendar.get(Calendar.MINUTE) + xx*60;

        if(xx2 > (19*60+00) && xx2 < (8*60+4)){
            String tip = null;
            tip = "下班打卡";
            Notification notification =
                    NotificationUtils.creatNotify(mContent, tip,false);
            NotificationManager manager =
                    (NotificationManager) mContent.getSystemService(
                            mContent.NOTIFICATION_SERVICE);
            manager.notify(notify_daka_id, notification);
        }
    }





    private void notifyActivity(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return;
        }
        //通知更新ui
        //通知各个activity
        EventBus.getDefault().post(new BlockEvent());
    }


    private void notifyTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return;
        }

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
                manager.notify(notify_time_id, notification);
            }
        }else
        if(xx2 > (11*60+25) && xx2 < (11*60+29)){
            if(StockNewSettingUtils.getTime_1130(mContent)){
                String tip = null;
                tip = "上午结束时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(notify_time_id, notification);
            }
        }else
        if(xx2 > (12*60+55) && xx2 < (12*60+59)){
            if(StockNewSettingUtils.getTime_1300(mContent)){
                String tip = null;
                tip = "下午开盘时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(notify_time_id, notification);
            }
        }else
        if(xx2 > (14*60+55) && xx2 < (14*60+59)){
            if(StockNewSettingUtils.getTime_1500(mContent)){
                String tip = null;
                tip = "下午结束时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(notify_time_id, notification);
            }
        }else
        if(xx2 > (14*60+25) && xx2 < (14*60+29)){
            if(StockNewSettingUtils.getTime_1430(mContent)){
                String tip = null;
                tip = "神奇时间快到了";
                Notification notification =
                        NotificationUtils.creatNotify(mContent, tip,false);
                NotificationManager manager =
                        (NotificationManager) mContent.getSystemService(
                                mContent.NOTIFICATION_SERVICE);
                manager.notify(notify_time_id, notification);
            }
        }
    }

    private void notifyStock(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return;
        }
        String str = StockUtils.getStockCode1(mContent);
        String real = StockNewSettingUtils.getStockExchange(mContent,str) + str;
        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice1(mContent));
            float low =Float.parseFloat(StockUtils.getStockKuisun1(mContent));
            float hight = Float.parseFloat(StockUtils.getStockYingli1(mContent));
            pullWetherFromNet(real, ff, low, hight);
        }catch (Exception e){
            e.printStackTrace();
        }

        str = StockUtils.getStockCode2(mContent);
        real = StockNewSettingUtils.getStockExchange(mContent,str) + str;

        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice2(mContent));
            float low =Float.parseFloat(StockUtils.getStockKuisun2(mContent));
            float hight = Float.parseFloat(StockUtils.getStockYingli2(mContent));
            pullWetherFromNet(real, ff, low, hight);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void stockYujing(final String realCode,
                             final float buyprice,
                             final float low,
                             final float high,
                             final String res){

                if (!TextUtils.isEmpty(res)) {
                    String[] splits = res.split(",");
                    if (splits.length > 3) {
                        String currentPrice = splits[3];
                        Float yestodayPrice = Float.parseFloat(splits[2]);
                        Float todayPrice = Float.parseFloat(currentPrice);
                        // Float zzf = (jtPrice - ztPrice)/ztPrice*100;
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                        //String p=decimalFormat.format(zzf);//format 返回的是字符串

                        Float shipeishizhuan = (todayPrice - buyprice) / buyprice * 100;
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
                            manager.notify(notify_chaodie_id, notification);
                        }
                        if (shipeishizhuan > high) {
                            String tip = null;
                            tip = realCode + "已经超涨了";
                            Notification notification =
                                    NotificationUtils.creatNotify(mContent, tip,true);
                            NotificationManager manager =
                                    (NotificationManager) mContent.getSystemService(
                                            mContent.NOTIFICATION_SERVICE);
                            manager.notify(notify_chaozhang_id, notification);
                        }
                    }
                }
    }


    private final int notify_chaozhang_id = 444;
    private final int notify_chaodie_id = 333;
    private final int notify_time_id = 999;
    private final int notify_whether_id = 1000;
    private final int notify_dingcan_id = 1001;
    private final int notify_daka_id = 1002;


    private void pullWetherFromNet(final String realCode,
                                   final float buyprice,
                                   final float low,
                                   final float high){
        HttpRequestManager.getInstance(mContent.getApplicationContext()).request_stock(realCode, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response != null && response.body() != null){
                    final String res = new String(response.body().string());
                    if(mLL != null){
                        mLL.onStockResult(realCode, buyprice, low, high, res);
                    }
                }
            }
        });
    }
    private void pullWetherFromNet(final String w){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int xx =  calendar.get(Calendar.HOUR_OF_DAY);
        int xx2 = calendar.get(Calendar.MINUTE) + xx*60;

        if(xx2 > (6*60+56) && xx2 < (7*60+59)){
            HttpRequestManager.getInstance(mContent.getApplicationContext()).request_whether(w, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response != null && response.body() != null) {
                        final String res = new String(response.body().string());
                        if (mLL != null) {
                            mLL.onWhetherResult(res);
                        }
                    }
                }
            });
        }
    }

    //
    private IResultListner mLL;
    interface IResultListner{
        void onStockResult(final String realCode,
                           final float buyprice,
                           final float low,
                           final float high,
                           String result);
        void onWhetherResult(String res);
    }
}


