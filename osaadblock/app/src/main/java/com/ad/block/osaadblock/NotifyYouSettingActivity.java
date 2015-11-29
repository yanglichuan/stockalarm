package com.ad.block.osaadblock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.StockNewSettingUtils;

public class NotifyYouSettingActivity extends BaseActivity {

    private View tv_pp1;
    private View tv_pp2;

    private View rl_time;
    private View rl_tip;
    private View rl_grouptime;
    private View rl_grouptip;


    private View alarmcount_add;
    private View alarmcount_sub;
    private View time_add;
    private View time_sub;

    private TextView alarmcount_input;
    private TextView time_input;


    private int time_c = 1;
    private int alarm_c = 1;


    //
    private View rl_0900;
    private View rl_1130;
    private View rl_1300;
    private View rl_1500;
    private View rl_1430;

    private View rl_zhendong;
    private View rl_tongzhi;
    private View rl_tusi;
    private View rl_shengyin;
    private View rl_flash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //从缓存读取
        time_c = StockNewSettingUtils.getTimeBetween(mContext);
        alarm_c = StockNewSettingUtils.getAlarmCount(mContext);

        tv_pp1 = findViewById(R.id.tv_pp1);
        tv_pp2 = findViewById(R.id.tv_pp2);

        rl_time = findViewById(R.id.rl_time);
        rl_tip = findViewById(R.id.rl_tip);

        rl_grouptime = findViewById(R.id.rl_grouptime);
        rl_grouptip = findViewById(R.id.rl_grouptip);

        alarmcount_add = findViewById(R.id.alarmcount_add);
        alarmcount_sub = findViewById(R.id.alarmcount_sub);
        time_add = findViewById(R.id.time_add);
        time_sub = findViewById(R.id.time_sub);


        alarmcount_input = (TextView) findViewById(R.id.alarmcount_input);
        time_input = (TextView) findViewById(R.id.time_input);

        alarmcount_sub.setOnClickListener(this);
        alarmcount_add.setOnClickListener(this);
        time_sub.setOnClickListener(this);
        time_add.setOnClickListener(this);

        rl_tip.setOnClickListener(this);
        rl_time.setOnClickListener(this);


        //
        alarmcount_input.setText(alarm_c + "");
        time_input.setText(time_c + "");


        //
        rl_0900 = findViewById(R.id.rl_0900);
        rl_1130 = findViewById(R.id.rl_1130);
        rl_1300 = findViewById(R.id.rl_1300);
        rl_1500 = findViewById(R.id.rl_1500);
        rl_1430 = findViewById(R.id.rl_1430);
        rl_0900.setOnClickListener(this);
        rl_1130.setOnClickListener(this);
        rl_1300.setOnClickListener(this);
        rl_1500.setOnClickListener(this);
        rl_1430.setOnClickListener(this);

        rl_zhendong = findViewById(R.id.rl_zhendong);
        rl_tongzhi = findViewById(R.id.rl_tongzhi);
        rl_tusi = findViewById(R.id.rl_tusi);
        rl_shengyin = findViewById(R.id.rl_shengyin);
        rl_flash = findViewById(R.id.rl_flash);
        rl_zhendong.setOnClickListener(this);
        rl_tongzhi.setOnClickListener(this);
        rl_tusi.setOnClickListener(this);
        rl_shengyin.setOnClickListener(this);
        rl_flash.setOnClickListener(this);

        //
        boolean bb1 = StockNewSettingUtils.getTime_0900(mContext);
        boolean bb2 = StockNewSettingUtils.getTime_1130(mContext);
        boolean bb3 = StockNewSettingUtils.getTime_1300(mContext);
        boolean bb4 = StockNewSettingUtils.getTime_1500(mContext);
        boolean bb5 = StockNewSettingUtils.getTime_1430(mContext);
        rl_0900.setSelected(bb1);
        rl_1130.setSelected(bb2);
        rl_1300.setSelected(bb3);
        rl_1500.setSelected(bb4);
        rl_1430.setSelected(bb5);
         bb1 = StockNewSettingUtils.getAlarmZhendong(mContext);
         bb2 = StockNewSettingUtils.getAlarmTongzhi(mContext);
         bb3 = StockNewSettingUtils.getAlarmTusi(mContext);
         bb4 = StockNewSettingUtils.getAlarmSound(mContext);
         bb5 = StockNewSettingUtils.getAlarmFlash(mContext);
        rl_zhendong.setSelected(bb1);
        rl_tongzhi.setSelected(bb2);
        rl_tusi.setSelected(bb3);
        rl_shengyin.setSelected(bb4);
        rl_flash.setSelected(bb5);
    }








    private void addAlarmCount(){
        alarm_c ++;
        alarmcount_input.setText(alarm_c+"");

        //
        StockNewSettingUtils.setAlarmCount(mContext,alarm_c);
    }
    private void subAlarmCount(){
        alarm_c --;
        if(alarm_c<1){
            alarm_c = 1;
        }
        alarmcount_input.setText(alarm_c+"");

        //
        StockNewSettingUtils.setAlarmCount(mContext, alarm_c);
    }

    private void addTimeCount(){
        time_c ++;
        time_input.setText(time_c+"");

        //
        StockNewSettingUtils.setTimeBetween(mContext,time_c);
    }

    private void subTimeCount(){
        time_c --;
        if(time_c<1){
            time_c = 1;
        }
        time_input.setText(time_c+"");
        //
        StockNewSettingUtils.setTimeBetween(mContext, time_c);
    }




    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(NotifyYouSettingActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifyyousetting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_time:
                if(rl_grouptime.getVisibility() != View.VISIBLE){
                    rl_grouptime.setVisibility(View.VISIBLE);
                    //
                    tv_pp1.setBackgroundResource(R.drawable.down);

                }else{
                    rl_grouptime.setVisibility(View.GONE);
                    //
                    tv_pp1.setBackgroundResource(R.drawable.up);
                }

                break;
            case R.id.rl_tip:
                if(rl_grouptip.getVisibility() != View.VISIBLE){
                    rl_grouptip.setVisibility(View.VISIBLE);
                    //
                    tv_pp2.setBackgroundResource(R.drawable.down);
                }else{
                    rl_grouptip.setVisibility(View.GONE);
                    //
                    tv_pp2.setBackgroundResource(R.drawable.up);
                }

                break;
            case R.id.alarmcount_add:
                addAlarmCount();
                break;
            case R.id.alarmcount_sub:
                subAlarmCount();
                break;
            case R.id.time_add:
                addTimeCount();
                break;
            case R.id.time_sub:
                subTimeCount();
                break;
            //
            case R.id.rl_0900:
                rl_0900.setSelected(!rl_0900.isSelected());
                boolean b = rl_0900.isSelected();
                StockNewSettingUtils.setTime_0900(mContext,b);
                break;
            case R.id.rl_1130:
                rl_1130.setSelected(!rl_1130.isSelected());
                b = rl_1130.isSelected();
                StockNewSettingUtils.setTime_1130(mContext, b);
                break;
            case R.id.rl_1300:
                rl_1300.setSelected(!rl_1300.isSelected());
                b = rl_1300.isSelected();
                StockNewSettingUtils.setTime_1300(mContext, b);
                break;
            case R.id.rl_1500:
                rl_1500.setSelected(!rl_1500.isSelected());
                b = rl_1500.isSelected();
                StockNewSettingUtils.setTime_1500(mContext, b);
                break;
            case R.id.rl_1430:
                rl_1430.setSelected(!rl_1430.isSelected());
                b = rl_1430.isSelected();
                StockNewSettingUtils.setTime_1430(mContext, b);
                break;
            case R.id.rl_tongzhi:
                rl_tongzhi.setSelected(!rl_tongzhi.isSelected());
                b = rl_tongzhi.isSelected();
                StockNewSettingUtils.setAlarmTongzhi(mContext, b);
                break;
            case R.id.rl_zhendong:
                rl_zhendong.setSelected(!rl_zhendong.isSelected());
                b = rl_zhendong.isSelected();
                StockNewSettingUtils.setAlarmZhendong(mContext, b);
                break;
            case R.id.rl_tusi:
                rl_tusi.setSelected(!rl_tusi.isSelected());
                b = rl_tusi.isSelected();
                StockNewSettingUtils.setAlarmTusi(mContext, b);
                break;
            case R.id.rl_shengyin:
                rl_shengyin.setSelected(!rl_shengyin.isSelected());
                b = rl_shengyin.isSelected();
                StockNewSettingUtils.setAlarmSound(mContext, b);
                break;
            case R.id.rl_flash:
                rl_flash.setSelected(!rl_flash.isSelected());
                b = rl_flash.isSelected();
                StockNewSettingUtils.setAlarmFlash(mContext,b);
                break;
        }
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context, NotifyYouSettingActivity.class));
    }
}
