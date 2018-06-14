package com.atoken.cn.android_progressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private HorizontalProgressbar mHProgress;
    private RoundProgressBar mRoundProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHProgress = (HorizontalProgressbar) findViewById(R.id.hor_progress);
        mRoundProgress = (RoundProgressBar) findViewById(R.id.round_pro_bar);

//        HProgressAsyncTask task = new HProgressAsyncTask(mHProgress);
//        task.execute();

        RProgressAsyncTask task1=new RProgressAsyncTask(mRoundProgress);
        task1.execute();





    }


}
