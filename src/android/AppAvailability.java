package com.ohh2ahh.appavailability;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppAvailability extends CordovaPlugin {
    public static final String TAG = AppAvailability.class.getSimpleName();

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("checkAvailability")) {
            String uri = args.getString(0);
            this.checkAvailability(uri, callbackContext);
            return true;
        } else if (action.equals("getInfo")) {
            String packageName = args.getString(0);
            getInfo(packageName, callbackContext);
            return true;
        }
        return false;
    }
    
    // Thanks to http://floresosvaldo.com/android-cordova-plugin-checking-if-an-app-exists
    public boolean appInstalled(String uri) {
        Context ctx = this.cordova.getActivity().getApplicationContext();
        final PackageManager pm = ctx.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch(PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    
    private void checkAvailability(String uri, CallbackContext callbackContext) {
        if(appInstalled(uri)) {
            callbackContext.success();
        }
        else {
            callbackContext.error("");
        }
    }

    public void getInfo(String packageName, CallbackContext callbackContext) {
        Log.i(TAG, "getInfo");
        Context ctx = this.cordova.getActivity().getApplicationContext();
        final PackageManager pm = ctx.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Log.i(TAG, "got package info");
            callbackContext.success(this.buildJson(info));
        } catch(PackageManager.NameNotFoundException e) {
            callbackContext.success(new JSONObject());
        } catch(Exception e) {
            callbackContext.error(e.getMessage());
        }
    }

    private JSONObject buildJson(PackageInfo info) throws JSONException {
        Log.i(TAG, "buildJson");
        JSONObject object = new JSONObject();
        object.put("versionName", info.versionName);
        object.put("versionCode", info.versionCode);
        return object;
    }
}
