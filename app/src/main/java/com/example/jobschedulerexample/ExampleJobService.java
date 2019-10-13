package com.example.jobschedulerexample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.SystemClock;
import android.util.Log;

public class ExampleJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean cancelJob = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job Started");
        doJobInBackground(jobParameters);
        return true; //when job is done in background thread, we return true, so that our app is awake until job finishes
    }
    private void doJobInBackground(final JobParameters jobParameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    if(cancelJob) {
                        return;
                    }

                    Log.d(TAG, "run: "+i);
                    SystemClock.sleep(1000);
                }
                Log.d(TAG, "Job finished");
                jobFinished(jobParameters,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        cancelJob = true;
        Log.d(TAG, "Job Stopped");
        return true;
    }
}