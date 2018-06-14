package com.atoken.cn.android_progressbar;

import android.os.AsyncTask;
import android.util.Log;


public class HProgressAsyncTask extends AsyncTask<Void,Integer,Integer> {

    private static final  String TAG="HProgressAsyncTask";

    private HorizontalProgressbar mHProgress;

    public HProgressAsyncTask(HorizontalProgressbar mHProgress) {
        this.mHProgress = mHProgress;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG,"start...");
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(100);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    //更新进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int vlaue = values[0];
        mHProgress.setProgress(vlaue);
    }
}
