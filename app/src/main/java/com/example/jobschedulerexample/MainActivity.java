package com.example.jobschedulerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int JOB_ID = 123;
    JobInfo jobInfo;
    JobScheduler scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComponentName componentName = new ComponentName(this,ExampleJobService.class);
        JobInfo.Builder infoBuilder = new JobInfo.Builder(JOB_ID,componentName);
        infoBuilder.setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)       // job will persists even after reboot
                .setPeriodic(15*60*1000); //job will be scheduled every 15 minutes

        jobInfo = infoBuilder.build();
        scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

    }

    public void onSchedule(View v){
        int isScheduled = scheduler.schedule(jobInfo);
        if(isScheduled == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job Scheduled");
        }else {
            Log.d(TAG, "Job Scheduling Failed");
        }
    }

    public void onCancel(View v){
        scheduler.cancel(JOB_ID);
    }
}