package com.ad.block.osaadblock.utils;

import android.content.Context;

public class StockUtils {

    //暂时只能添加两个股票
    private static final String stockName1 = "stockName1";
    private static final String stockName2 = "stockName2";

    private static final String stockCode1 = "stockCode1";
    private static final String stockCode2 = "stockCode2";

    private static final String stockBuyPrice1 = "stockBuyPrice1";
    private static final String stockBuyPrice2 = "stockBuyPrice2";

    private static final String stockYingli1 = "stockYingli1";
    private static final String stockYingli2 = "stockYingli2";
    private static final String stockKuisun1 = "stockKuisun1";
    private static final String stockKuisun2 = "stockKuisun2";


    //final
    private static final String code1 = "600288";
    private static final String name1 = "大恒科技";
    private static final String buyprice1 = "0.1";//默认初始值
    private static final String yingli1 = "5";
    private static final String kuisun1 = "-4";

    private static final String code2 = "600287";
    private static final String name2 = "江苏舜天";
    private static final String buyprice2 = "0.1";//默认初始值
    private static final String yingli2 = "5";
    private static final String kuisun2 = "-4";


    public static String getStockName1(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockName1,name1);
    }
    public static String getStockName2(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockName2,name2);
    }

    public static String getStockBuyPrice1(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockBuyPrice1,buyprice1);
    }
    public static String getStockBuyPrice2(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockBuyPrice2,buyprice2);
    }

    public static String getStockCode1(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockCode1,code1);
    }
    public static String getStockCode2(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockCode2,code2);
    }
    public static String getStockYingli1(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockYingli1,yingli1);
    }
    public static String getStockYingli2(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockYingli2,yingli2);
    }
    public static String getStockKuisun1(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockKuisun1,kuisun1);
    }
    public static String getStockKuisun2(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getStringData(stockKuisun2,kuisun2);
    }




    public static void updateStock1BuyPrice(Context context,String price){
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice1, price);
    }

    public static void updateStock2BuyPrice(Context context,String price){
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice2, price);
    }


    public static void addStockItem1(Context context,String code, String name, String price,
                                     String yingli,String kunsui){
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockCode1, code);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName1,name);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice1, price);

        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockYingli1, yingli);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockKuisun1, kunsui);
    }

    public static void addStockItem2(Context context,String code, String name, String price,
                                     String yingli,String kunsui){
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockCode2, code);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName2,name);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice2, price);

        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockYingli2, yingli);
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockKuisun2, kunsui);
    }



    /**
     * 这两个股票信息都归零
     * @param context
     */
    public static void reset(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName1,"");
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockName2, "");

        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice1, "");
        RecordFileUtils.getInstance(context.getApplicationContext()).setStringData(stockBuyPrice2, "");

    }
}