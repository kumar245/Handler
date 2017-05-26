package com.kumar.user.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
SeekBar skbar;
    TextView tView;
     boolean isRunning=false;
    int CounterUp=0;
    int MaxCounter=100;
    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skbar= (SeekBar) findViewById(R.id.seekBar);
        handler=new MyHandler();
        skbar.setMax(MaxCounter);
        tView= (TextView) findViewById(R.id.textView);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            while (isRunning){
                if (CounterUp<=MaxCounter){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                        }
//                    }); Instead we use handler

                    Message msg=handler.obtainMessage();
                    Bundle b= new Bundle();
                    b.putInt("counter",CounterUp);
                    msg.setData(b);
                    handler.sendMessage(msg);
                    CounterUp=CounterUp + 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    class MyHandler extends Handler{
        public void handleMessage(Message msg) {
            int count=msg.getData().getInt("counter");
            skbar.setProgress(count);
            tView.setText("Counter = " + count);
        }

    }

    public void buStart(View view) {
        isRunning=true;
    MyThread myThread= new MyThread();
        myThread.start();
    }

    public void buStop(View view) {
        isRunning=false;
    }


}
