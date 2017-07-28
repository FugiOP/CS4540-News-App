package util;

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Fugi on 7/27/2017.
 */

public class NewsJob extends JobService {
    AsyncTask backgroundTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                //call to set the constraints on refresh
                JobDispatcher.refresh(NewsJob.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jobFinished(job,false);
            }
        };

        backgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        if(backgroundTask != null){
            backgroundTask.cancel(false);
        }
        return true;
    }
}
