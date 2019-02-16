package com.swapcard.tmdb;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;


public class AppController extends MultiDexApplication {

    private Context context;
    public static FragmentActivity currentActivity;
    public static Fragment currentFragment;
    private static InputMethodManager inputMethodManager;
    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        context = getBaseContext();
        inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    public static int getWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (currentActivity() != null) {
            currentActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (displayMetrics.widthPixels > 0)
                return displayMetrics.widthPixels;
            else
                return 480;
        } else
            return 480;
    }

    public static int getHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (currentActivity() != null) {
            currentActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            if (displayMetrics.heightPixels > 0)
                return displayMetrics.heightPixels;
            else
                return 800;
        } else
            return 800;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public static void setCurrentActivity(FragmentActivity currentActivity) {
        AppController.currentActivity = currentActivity;
    }

    public static FragmentActivity currentActivity() {
        return currentActivity;
    }


}
