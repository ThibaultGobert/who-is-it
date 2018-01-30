package com.example.thibault.who_is_it_thibaultgobert;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Thibault on 30/01/2018.
 */

public class MemoryLeakApplication extends Application {
    private RefWatcher refWatcher;
    public static RefWatcher getRefWatcher(Context context){
        MemoryLeakApplication application = (MemoryLeakApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

}

