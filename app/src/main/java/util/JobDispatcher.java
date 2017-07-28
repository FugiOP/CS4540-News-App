package util;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Fugi on 7/27/2017.
 */

public class JobDispatcher {
    static boolean initialized;
    synchronized public static void refresh(Context context){
        //Checking if the refresh settings have been set already if yes, then do nothing
        if(initialized){
            return;
        }else{
            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            //Setting the refresh timer to 1 minute
            Job constraint = dispatcher.newJobBuilder()
                    .setService(NewsJob.class)
                    .setTag("news_job_tag")
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(1,1+60))
                    .setReplaceCurrent(true)
                    .build();
            dispatcher.schedule(constraint);
            initialized = true;
        }
    }
}
