package com.ad.block.osaadblock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.StockUtils;
import com.ad.block.osaadblock.utils.ToastUtils;

public class StockAddActivity extends BaseActivity{


    private EditText gp_code_1;
    private EditText gp_name_1;
    private EditText gp_buyprice_1;
    private EditText gp_yingli_1;
    private EditText gp_kuisun_1;

    private EditText gp_code_2;
    private EditText gp_name_2;
    private EditText gp_buyprice_2;
    private EditText gp_yingli_2;
    private EditText gp_kuisun_2;



    private View tv_update_1;
    private View tv_update_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        gp_code_1 = (EditText) findViewById(R.id.gp_code_1);
        gp_name_1 = (EditText) findViewById(R.id.gp_name_1);
        gp_buyprice_1 = (EditText) findViewById(R.id.gp_buyprice_1);
        gp_yingli_1 = (EditText) findViewById(R.id.gp_yingli_1);
        gp_yingli_1.setTextColor(Color.RED);
        gp_kuisun_1 = (EditText) findViewById(R.id.gp_kuisun_1);
        gp_kuisun_1.setTextColor(Color.GREEN);


        gp_code_2 = (EditText) findViewById(R.id.gp_code_2);
        gp_name_2 = (EditText) findViewById(R.id.gp_name_2);
        gp_buyprice_2 = (EditText) findViewById(R.id.gp_buyprice_2);
        gp_yingli_2 = (EditText) findViewById(R.id.gp_yingli_2);
        gp_yingli_2.setTextColor(Color.RED);
        gp_kuisun_2 = (EditText) findViewById(R.id.gp_kuisun_2);
        gp_kuisun_2.setTextColor(Color.GREEN);

        //约束输入
        gp_code_1.addTextChangedListener(new MaxLengthWatcher(6,gp_code_1));
        gp_code_2.addTextChangedListener(new MaxLengthWatcher(6,gp_code_2));

        tv_update_1 = findViewById(R.id.tv_update_1);
        tv_update_2 = findViewById(R.id.tv_update_2);
        tv_update_1.setOnClickListener(this);
        tv_update_2.setOnClickListener(this);


        init();
    }


    private void init(){
        gp_code_1 = (EditText) findViewById(R.id.gp_code_1);
        gp_name_1 = (EditText) findViewById(R.id.gp_name_1);
        gp_buyprice_1 = (EditText) findViewById(R.id.gp_buyprice_1);
        gp_yingli_1 = (EditText) findViewById(R.id.gp_yingli_1);
        gp_kuisun_1 = (EditText) findViewById(R.id.gp_kuisun_1);


        gp_code_2 = (EditText) findViewById(R.id.gp_code_2);
        gp_name_2 = (EditText) findViewById(R.id.gp_name_2);
        gp_buyprice_2 = (EditText) findViewById(R.id.gp_buyprice_2);
        gp_yingli_2 = (EditText) findViewById(R.id.gp_yingli_2);
        gp_kuisun_2 = (EditText) findViewById(R.id.gp_kuisun_2);

        String code1= StockUtils.getStockCode1(mContext);
        String name1 = StockUtils.getStockName1(mContext);
        String buyPrice1 = StockUtils.getStockBuyPrice1(mContext);
        String yingli1 = StockUtils.getStockYingli1(mContext);
        String kuisun1 = StockUtils.getStockKuisun1(mContext);
        gp_code_1.setText(code1);
        gp_name_1.setText(name1);
        gp_buyprice_1.setText(buyPrice1);
        gp_yingli_1.setText(yingli1);
        gp_kuisun_1.setText(kuisun1);

        String code2= StockUtils.getStockCode2(mContext);
        String name2 = StockUtils.getStockName2(mContext);
        String buyPrice2 = StockUtils.getStockBuyPrice2(mContext);
        String yingli2 = StockUtils.getStockYingli2(mContext);
        String kuisun2 = StockUtils.getStockKuisun2(mContext);
        gp_code_2.setText(code2);
        gp_name_2.setText(name2);
        gp_buyprice_2.setText(buyPrice2);
        gp_yingli_2.setText(yingli2);
        gp_kuisun_2.setText(kuisun2);

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
            params.height = CommonUtils.getStatusHeight(StockAddActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stockadd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_1:
                if(TextUtils.isEmpty(gp_code_1.getText().toString())){
                    ToastUtils.toastL(mContext,"股票代码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_name_1.getText().toString())){
                    ToastUtils.toastL(mContext,"股票名字不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_buyprice_1.getText().toString())){
                    ToastUtils.toastL(mContext,"买入价格不能为空");
                    return;
                }
                if(Float.parseFloat(gp_buyprice_1.getText().toString()) < 1){
                    gp_buyprice_1.setText("1.00");
                }
                if(TextUtils.isEmpty(gp_yingli_1.getText().toString())){
                    ToastUtils.toastL(mContext,"盈利上限不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_kuisun_1.getText().toString())){
                    ToastUtils.toastL(mContext,"亏损下限不能为空");
                    return;
                }
                String code = gp_code_1.getText().toString();
                String name = gp_name_1.getText().toString();
                String buyprice = gp_buyprice_1.getText().toString();
                String yingli = gp_yingli_1.getText().toString();
                String kuisun = gp_kuisun_1.getText().toString();
                StockUtils.addStockItem1(mContext, code,name,buyprice,yingli,kuisun);

                ToastUtils.toastL(mContext, "更新完成");

                break;
            case R.id.tv_update_2:


                if(TextUtils.isEmpty(gp_code_2.getText().toString())){
                    ToastUtils.toastL(mContext,"股票代码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_name_2.getText().toString())){
                    ToastUtils.toastL(mContext,"股票名字不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_buyprice_2.getText().toString())){
                    ToastUtils.toastL(mContext,"买入价格不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_yingli_2.getText().toString())){
                    ToastUtils.toastL(mContext,"盈利上限不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gp_kuisun_2.getText().toString())){
                    ToastUtils.toastL(mContext, "亏损下限不能为空");
                    return;
                }
                if(Float.parseFloat(gp_buyprice_2.getText().toString()) < 1){
                    gp_buyprice_2.setText("1.00");
                }



                code = gp_code_2.getText().toString();
                name = gp_name_2.getText().toString();
                buyprice = gp_buyprice_2.getText().toString();
                yingli = gp_yingli_2.getText().toString();
                kuisun = gp_kuisun_2.getText().toString();
                StockUtils.addStockItem2(mContext, code, name, buyprice, yingli, kuisun);

                ToastUtils.toastL(mContext, "更新完成");

                break;
        }
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context, StockAddActivity.class));
    }




    /*
    * 监听输入内容是否超出最大长度，并设置光标位置
    * */
    class MaxLengthWatcher implements TextWatcher {
        private int maxLen = 0;
        private EditText editText = null;

        public MaxLengthWatcher(int maxLen, EditText editText) {
            this.maxLen = maxLen;
            this.editText = editText;
        }
        public void afterTextChanged(Editable arg0) {
        }
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
        }
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            Editable editable = editText.getText();
            int len = editable.length();
            if(!TextUtils.isEmpty(editable.toString())){
                String str = editable.toString();
                String pre0 = str.substring(0,1);
                String pre1 = null;
                String pre2 = null;
                if(str.length()>1){
                    pre1 = str.substring(1,2);
                    if(!pre0.equals("0") && !pre0.equals("6")){
                        editText.setText("");
                        Selection.setSelection(editText.getText(), 0);
                    }
                    if(!pre1.equals("0")){
                        editText.setText(pre0+"");
                        Selection.setSelection(editText.getText(), 1);
                    }
                }
                if(str.length()>2){
                    pre2 = str.substring(2,3);
                    if(!pre0.equals("0") && !pre0.equals("6")){
                        editText.setText("");
                        Selection.setSelection(editText.getText(), 0);
                    }
                    if(!pre1.equals("0")){
                        editText.setText(pre0+"");
                        Selection.setSelection(editText.getText(), 1);
                    }
                    if(!pre2.equals("0")&& !pre2.equals("1")&& !pre2.equals("2")){
                        editText.setText(pre0+pre1+"");
                        Selection.setSelection(editText.getText(), 2);
                    }
                }
                if(!pre0.equals("0") && !pre0.equals("6")){
                    editText.setText("");
                    Selection.setSelection(editText.getText(), 0);
                }
            }
            if(len > maxLen)
            {
                int selEndIndex = Selection.getSelectionEnd(editable);
                String str = editable.toString();
                //截取新字符串
                String newStr = str.substring(0,maxLen);
                editText.setText(newStr);
                editable = editText.getText();

                //新字符串的长度
                int newLen = editable.length();
                //旧光标位置超过字符串长度
                if(selEndIndex > newLen)
                {
                    selEndIndex = editable.length();
                }
                //设置新光标所在的位置
                Selection.setSelection(editable, selEndIndex);
            }
        }
    }

}
