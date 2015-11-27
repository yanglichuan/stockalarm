package com.ad.block.osaadblock.utils;

import android.content.Context;

public class StockNewSettingUtils {

    //暂时只能添加两个股票
    private static final String Pre60 = "60";
    private static final String Pre000= "000";
    private static final String Pre002= "002";
    private static final String Pre300= "300";


    //刷新的时间
    private static final String time_detween = "time_detween";
    private static final String alarm_count = "alarm_count";
    public static int getAlarmCount(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(alarm_count, 1);
    }
    public static void setAlarmCount(Context context, int b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(alarm_count,b);
    }


    //
    private static final String alarm_zhendong = "alarm_zhendong";
    private static final String alarm_tongzhi = "alarm_tongzhi";
    private static final String alarm_tusi = "alarm_tusi";
    private static final String alarm_sound = "alarm_sound";
    private static final String alarm_flash = "alarm_flash";

    //时间提醒
    private static final String time_0900 = "time_0900";
    private static final String time_1130 = "time_1130";
    private static final String time_1300 = "time_1300";
    private static final String time_1500 = "time_1500";
    private static final String time_1430 = "time_1430";


    /**
     * 获得交易场所
     * @param context
     * @param strPre
     * @return
     */
    public static String getStockExchange(Context context,String strPre){
        if(strPre.startsWith(Pre60)){
            return "sh";
        }else if(strPre.startsWith(Pre000) || strPre.startsWith(Pre000)
                ||strPre.startsWith(Pre002)||strPre.startsWith(Pre300)){
            return "sz";
        }else{
            return "";
        }
    }

    public static int getTimeBetween(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(time_detween, 1);
    }

    public static void setTimeBetween(Context context, int timebt){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(time_detween,timebt);
    }




    public static boolean getAlarmZhendong(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(alarm_zhendong, true);
    }
    public static void setAlarmZhendong(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(alarm_zhendong, b);
    }
    public static boolean getAlarmTongzhi(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(alarm_tongzhi, true);
    }
    public static void setAlarmTongzhi(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(alarm_tongzhi, b);
    }
    public static boolean getAlarmTusi(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(alarm_tusi, true);
    }
    public static void setAlarmTusi(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(alarm_tusi, b);
    }
    public static boolean getAlarmSound(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(alarm_sound,true);
    }
    public static void setAlarmSound(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(alarm_sound, b);
    }
    public static boolean getAlarmFlash(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(alarm_flash,true);
    }
    public static void setAlarmFlash(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(alarm_flash, b);
    }






    public static boolean getTime_0900(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(time_0900,true);
    }
    public static void setTime_0900(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(time_0900, b);
    }
    public static boolean getTime_1130(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(time_1130,true);
    }
    public static void setTime_1130(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(time_1130, b);
    }
    public static boolean getTime_1300(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(time_1300,true);
    }
    public static void setTime_1300(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(time_1300, b);
    }
    public static boolean getTime_1500(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(time_1500,true);
    }
    public static void setTime_1500(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(time_1500, b);
    }
    public static boolean getTime_1430(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getBooleanData(time_1430,true);
    }
    public static void setTime_1430(Context context, boolean b){
        RecordFileUtils.getInstance(context.getApplicationContext()).setBooleanData(time_1430, b);
    }


//
//
//
//    /**
//     * 这两个股票信息都归零
//     * @param context
//     */
//    public static void reset(Context context){
//        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName1,"");
//        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName2, "");
//
//        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockPrice1, "");
//        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockPrice2, "");
//
//    }
}