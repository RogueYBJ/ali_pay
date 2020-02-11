package com.example.ali_pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.lang.ref.WeakReference;
import java.util.Map;



public class AliPayService {

    private PayRunnable payRunnable;

    private PayHandler mHandler;

    private PayTask alipay ;

    private static AliPayService instance;

    //    0 未启动（可以调用） 1 已经启动，不能调用
    private int status;

    private AliPayService(){

    }

    static AliPayService getInstance(){
        if (instance==null)
            instance=new AliPayService();
        return instance;
    }

    public void initAliPayServer(Activity activity){
        alipay = new PayTask(activity);
        payRunnable =new PayRunnable();
        mHandler=new PayHandler(this);
//        thread=new Thread(payRunnable);
    }

    public void startPay(String orderInfo){
        if (status==1){
            Log.e("---AliPayService----","AliPayServer is running");
            return;
        }
        status=1;
        payRunnable.setOrderInfo(orderInfo);
        ThreadUtil.getInstance().getPool().submit(payRunnable);
    }

    private class PayRunnable implements Runnable{

        private  String orderInfo="";

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public void run() {
            if (TextUtils.isEmpty(orderInfo)){
                return;
            }
            Log.e("-------",orderInfo);
            Map<String,String> result = alipay.payV2(orderInfo,true);

            Message msg = new Message();
            msg.what = 0;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    }

    private static class PayHandler extends Handler{

        private final WeakReference<AliPayService> weak;

        private PayHandler(AliPayService aliPayService) {
            weak=new WeakReference<AliPayService>(aliPayService);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weak.get()!=null) {
                switch (msg.what) {
                    case 0:

                        weak.get().status=0;
                        Log.d("=====msg.obj=====",msg.obj.toString());
                        break;
                }
            }
        }
    }
}