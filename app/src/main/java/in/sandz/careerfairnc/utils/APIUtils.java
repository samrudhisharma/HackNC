package in.sandz.careerfairnc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import in.sandz.careerfairnc.DashboardActivity;
import in.sandz.careerfairnc.HomeScreen;

/**
 * Created by sandz on 10/10/15.
 */
public class APIUtils {
    Activity activity;

    public APIUtils(Activity activity) {
        this.activity = activity;
    }

    public String getAuthorizationToken() {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());

        String auth_token = settings.getString("auth_token", null);
//        Log.i("Auth Token", auth_token);
        return auth_token;
    }

    public String getHeaderAuthorizationToken() {
        return " Token " + getAuthorizationToken();
    }
}
