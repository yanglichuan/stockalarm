package com.ad.block.osaadblock;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.service.BlockService;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.NotificationUtils;
import com.ad.block.osaadblock.utils.StockNewSettingUtils;
import com.ad.block.osaadblock.utils.StockUtils;
import com.andexert.library.RippleView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends BaseActivity implements RippleView.OnRippleCompleteListener {
    private View setting;

    private View anim_wholeview;
    private View top_color;
    private View ratateLine;
    private View opened_bg;

    private TextView tip_top;

    private final int STATE_CLOSED = 1;
    private final int STATE_OPENING = 2;
    private final int STATE_OPENED = 3;
    private final int STATE_CLOSEING = 4;

    //初始化状态值
    private int iBlockState = STATE_CLOSED;



    private View stockView1;
    private View stockView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tip_top = (TextView) findViewById(R.id.tip_top);
        tip_top.setText(getString(R.string.open));

        iBlockState = STATE_CLOSED;
        ratateLine = findViewById(R.id.ratateLine);
        top_color = findViewById(R.id.top_color);
        opened_bg = findViewById(R.id.opened_bg);
        opened_bg.setVisibility(View.GONE);

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(this);

        anim_wholeview = findViewById(R.id.anim_view);
        anim_wholeview.setOnClickListener(this);

        if(CommonUtils.isServiceRunning(mContext,BlockService.class.getCanonicalName())){
            noAnimOpen();
        }else{
            //不做任何事情
        }

        stockname1 = (TextView) findViewById(R.id.stockname1);
        stockname2 = (TextView) findViewById(R.id.stockname2);
        currentGP1Price = (TextView) findViewById(R.id.stockprice1);
        currentGP2Price = (TextView) findViewById(R.id.stockprice2);
        currentZhangfu1 = (TextView) findViewById(R.id.currentzhangfu1);
        currentZhagnfu2 = (TextView) findViewById(R.id.currentzhangfu2);
        nowshouyi1 = (TextView) findViewById(R.id.nowshouyi1);
        nowshouyi2 = (TextView) findViewById(R.id.nowshouyi2);


        sh_zhishu = (TextView) findViewById(R.id.sh_zhishu);
        sh_zhangfu = (TextView) findViewById(R.id.sh_zhangfu);
        sz_zhishu = (TextView) findViewById(R.id.sz_zhishu);
        sz_zhangfu = (TextView) findViewById(R.id.sz_zhangfu);


        stockView1 = findViewById(R.id.stockview1);
        stockView2 = findViewById(R.id.stockview2);
        stockView1.setOnClickListener(this);
        stockView2.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
        updateSum();
    }

    private TextView sh_zhishu;
    private TextView sh_zhangfu;
    private TextView sz_zhishu;
    private TextView sz_zhangfu;
    private TextView stockname1;
    private TextView stockname2;
    private TextView currentGP1Price;
    private TextView currentGP2Price;
    private TextView currentZhangfu1;
    private TextView currentZhagnfu2;
    private TextView nowshouyi1;
    private TextView nowshouyi2;


    private void initUI(){
        //
        String name1 = StockUtils.getStockName1(mContext);
        String name2 = StockUtils.getStockName2(mContext);

        String code1 = StockUtils.getStockCode1(mContext);
        String code2 = StockUtils.getStockCode2(mContext);

        stockname1.setText(name1+"\n"+code1);
        stockname2.setText(name2+"\n"+code2);
    }






    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(MainActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }


    public static void openActivity(Context context){
        Intent it  = new Intent();
        it.setClass(context, MainActivity.class);
        context.startActivity(it);
    }


    private void setStateOpened(){
        tip_top.setText(getString(R.string.close));
        iBlockState = STATE_OPENED;
    }

    private void setStateClosed(){
        tip_top.setText(getString(R.string.open));
        iBlockState = STATE_CLOSED;
    }

    private void setStateOpening(){
        tip_top.setText(getString(R.string.opening));
        iBlockState = STATE_OPENING;
    }

    private void setStateCloseing(){
        tip_top.setText(getString(R.string.closeing));
        iBlockState = STATE_CLOSEING;
    }

    @Deprecated
    private void noAnimClose(){
        setStateClosed();
        //拦截服务已经关闭
        stopService(new Intent(mContext, BlockService.class));
        opened_bg.setVisibility(View.GONE);
        ratateLine.setVisibility(View.VISIBLE);
        top_color.setBackgroundColor(getResources().getColor(R.color.background_from));
    }

    private void noAnimOpen(){
        setStateOpened();
        startService(new Intent(mContext, BlockService.class));
        opened_bg.setVisibility(View.VISIBLE);
        ratateLine.setVisibility(View.GONE);
        top_color.setBackgroundColor(getResources().getColor(R.color.background_to));
    }

    private void animClose(){
        animFadeScaleIn(opened_bg, true, new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                opened_bg.setVisibility(View.VISIBLE);
                setStateCloseing();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animRotate(ratateLine, true);
                animBackGroundColor(top_color, true, new AnimLL() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setStateClosed();

                        //拦截服务已经关闭
                        stopService(new Intent(mContext, BlockService.class));
                    }
                });
            }
        });
        animFadeOut(ratateLine, true, new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                ratateLine.setVisibility(View.VISIBLE);
            }
        });
    }


    private void animOpen(){
        animRotate(ratateLine, false);
        animBackGroundColor(top_color, false, new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                setStateOpening();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animFadeScaleIn(opened_bg, false, new AnimLL() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        opened_bg.setVisibility(View.VISIBLE);
                        setStateOpened();

                        startService(new Intent(mContext, BlockService.class));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animFadeOut(ratateLine, false, new AnimLL() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ratateLine.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ratateLine.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    /**
     * zong'dong'h
     * @param view
     * @param bReverse
     */
    private void animRotate(View view, boolean bReverse){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        objectAnimator.setDuration(800);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        if(bReverse){
            objectAnimator.reverse();
        }else{
            objectAnimator.start();
        }
    }


    private void animBackGroundColor(View view,boolean bReverse ,Animator.AnimatorListener listener){
        //
        ObjectAnimator bg = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.opening_color);
        bg.setEvaluator(new ArgbEvaluator());
        bg.setTarget(view);
        bg.addListener(listener);
        if(bReverse){
            bg.reverse();
        }else{
            bg.start();
        }


    }

    private void animFadeScaleIn(final View view,boolean bReverse, Animator.AnimatorListener listener){
        ObjectAnimator objectAnimator = null;
        ObjectAnimator objectAnimator2 = null;
        ObjectAnimator objectAnimator3 = null;
        if(bReverse){
            objectAnimator = ObjectAnimator.ofFloat(view,"alpha",1,0);
            objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",1,0);
            objectAnimator3 = ObjectAnimator.ofFloat(view,"scaleY",1,0);
        }else{
            objectAnimator = ObjectAnimator.ofFloat(view,"alpha",0,1);
            objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",0,1);
            objectAnimator3 = ObjectAnimator.ofFloat(view,"scaleY",0,1);
        }
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator2.setDuration(400);
        objectAnimator2.setRepeatCount(0);
        objectAnimator2.setInterpolator(new DecelerateInterpolator());
        objectAnimator3.setDuration(400);
        objectAnimator3.setRepeatCount(0);
        objectAnimator3.setInterpolator(new DecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.addListener(listener);
        set.playTogether(objectAnimator,objectAnimator2,objectAnimator3);
        set.start();
    }

    private void animFadeOut(final View view,boolean bReverse, Animator.AnimatorListener listener){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"alpha",1,0);
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(0);
        objectAnimator.addListener(listener);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        if(bReverse){
            objectAnimator.reverse();
        }else{
            objectAnimator.start();
        }
    }


    private Handler mHandle = new Handler();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anim_view:
                if(iBlockState == STATE_OPENED){
                    animClose();
                }
                if(iBlockState == STATE_CLOSED){
                    animOpen();
                }
                break;

            case R.id.setting:
                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SettingActivity.openActivity(mContext);
                    }
                }, 500);
                break;

            case  R.id.stockview1:
                StockAddActivity.openActivity(mContext);
                break;
            case  R.id.stockview2:
                StockAddActivity.openActivity(mContext);
                break;
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId()){
            case R.id.item1_ft:
                break;
            case R.id.item2_ft:
                break;
            case R.id.item3_ft:
                break;
            case R.id.item4_ft:
                break;
        }
    }


    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if(event instanceof BlockEvent){
            updateSum();
        }
    }

    private void updateSum(){
        String str = StockUtils.getStockCode1(mContext);
        String real = StockNewSettingUtils.getStockExchange(mContext,str) + str;
        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice1(mContext));
            float low =Float.parseFloat(StockUtils.getStockKuisun1(mContext));
            float hight = Float.parseFloat(StockUtils.getStockYingli1(mContext));
            updateCustom(real,currentGP1Price,currentZhangfu1,nowshouyi1,ff,low,hight);
        }catch (Exception e){
            e.printStackTrace();;
        }



        str = StockUtils.getStockCode2(mContext);
        real = StockNewSettingUtils.getStockExchange(mContext,str) + str;

        try {
            float ff = Float.parseFloat(StockUtils.getStockBuyPrice2(mContext));
            float low =Float.parseFloat(StockUtils.getStockKuisun2(mContext));
            float hight = Float.parseFloat(StockUtils.getStockYingli2(mContext));
            updateCustom(real, currentGP2Price, currentZhagnfu2, nowshouyi2, ff, low, hight);
        }catch (Exception e){
            e.printStackTrace();;
        }


        updateSh();
        updatesz();
    }


    private void updateSh(){
        updateCustom("sh000001",sh_zhishu,sh_zhangfu,null,0,0,0);

    }
    private void updatesz(){
        updateCustom("sz399001",sz_zhishu,sz_zhangfu,null,0,0,0);
    }

    private void updateCustom(final String realCode,
                              final TextView pricetv,
                              final TextView zhangfuTv,
                              final TextView yinglilema,
                              final float buyprice,
                                final float low,
                              final float high){
        HttpRequestManager.getInstance(getApplicationContext()).request_stock(realCode, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response response) throws IOException {
                if(response != null && response.body() != null){
                    final String res = new String(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!TextUtils.isEmpty(res)){
                                String[] splits = res.split(",");
                                if(splits.length>3){
                                    String currentPrice = splits[3];
                                    Float ztPrice = Float.parseFloat(splits[2]);
                                    Float jtPrice = Float.parseFloat(currentPrice);
                                    Float zzf = (jtPrice - ztPrice)/ztPrice*100;
                                    DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                                    String p=decimalFormat.format(zzf);//format 返回的是字符串
                                    pricetv.setText(currentPrice);
                                    zhangfuTv.setText(p);

                                    if((jtPrice-ztPrice)>0){
                                        zhangfuTv.setBackgroundResource(R.drawable.hao1);
                                    }else{
                                        zhangfuTv.setBackgroundResource(R.drawable.hao2);
                                    }

                                    if(yinglilema!= null){
                                        zzf = (jtPrice - buyprice)/buyprice*100;
                                        decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                                        p=decimalFormat.format(zzf);//format 返回的是字符串
                                        yinglilema.setText(p);


                                        if((jtPrice - buyprice)>0){
                                            yinglilema.setBackgroundResource(R.drawable.hao1);
                                        }else{
                                            yinglilema.setBackgroundResource(R.drawable.hao2);
                                        }

                                        //报警
                                        if(zzf < low){
                                            String tip = null;
                                            tip=realCode+"已经超跌了";
                                            Notification notification = NotificationUtils.creatNotify(mContext, tip);
                                            NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                                            manager.notify(444,notification);
                                        }
                                        if(zzf > high){
                                            String tip = null;
                                            tip=realCode+"已经超涨了";
                                            Notification notification = NotificationUtils.creatNotify(mContext,tip);
                                            NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                                            manager.notify(333, notification);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }



}
