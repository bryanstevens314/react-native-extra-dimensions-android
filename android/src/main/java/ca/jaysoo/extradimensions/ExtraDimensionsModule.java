package ca.jaysoo.extradimensions;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.provider.Settings;
import android.content.res.Resources;
import android.view.WindowManager;
import android.view.ViewConfiguration;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.Field;

public class ExtraDimensionsModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private ReactContext mReactContext;

    public ExtraDimensionsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mReactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "ExtraDimensions";
    }

    @Override
    public void onHostDestroy() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostResume() {

    }

    private boolean hasPermanentMenuKey() {
        final Context ctx = getReactApplicationContext();
        int id = ctx.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return !(id > 0 && ctx.getResources().getBoolean(id));
    }

    @ReactMethod
    public void isSoftMenuBarEnabled(final Callback callback) {
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        final int heightResId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        float height = heightResId > 0
            ? ctx.getResources().getDimensionPixelSize(heightResId) / metrics.density
            : 0;
        callback.invoke(height);
    }

    @ReactMethod
    public void getStatusBarHeight(final Callback callback) {
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        final int heightResId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        float height = heightResId > 0
            ? ctx.getResources().getDimensionPixelSize(heightResId) / metrics.density
            : 0;
        callback.invoke(height);
    }

    @ReactMethod
    public void getSoftMenuBarHeight(final Callback callback) {
        if(hasPermanentMenuKey()) {
            callback.invoke(0);
            return;
        }
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        final int heightResId = ctx.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        float height = heightResId > 0
            ? ctx.getResources().getDimensionPixelSize(heightResId) / metrics.density
            : 0;
        callback.invoke(height);
    }

    @ReactMethod
    public void getRealHeight(final Callback callback) {
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        float height = metrics.heightPixels / metrics.density;
        callback.invoke(height);
    }

    @ReactMethod
    public void getRealWidth(final Callback callback) {
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        float height = metrics.widthPixels / metrics.density;
        callback.invoke(height);
    }

    @ReactMethod
    public void getSmartBarHeight(final Callback callback) {
        final Context ctx = getReactApplicationContext();
        final DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        final boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");
 
        final boolean autoHideSmartBar = Settings.System.getInt(ctx.getContentResolver(),
            "mz_smartbar_auto_hide", 0) == 1;
 
        if (!isMeiZu || autoHideSmartBar) {
            callback.invoke(0);
        }
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int h = Integer.parseInt(field.get(obj).toString());
            float height = ctx.getResources().getDimensionPixelSize(h) / metrics.density;
            callback.invoke(height);
        } catch (Throwable e) { // 不自动隐藏smartbar同时又没有smartbar高度字段供访问，取系统navigationbar的高度
            float height = getNormalNavigationBarHeight(ctx) / metrics.density;
            callback.invoke(height);
        }
        //return getNormalNavigationBarHeight(ctx) / metrics.density;
    }
 
    protected static float getNormalNavigationBarHeight(final Context ctx) {
        try {
            final Resources res = ctx.getResources();
            int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid > 0) {
                boolean flag = res.getBoolean(rid);
                if (flag) {
                    int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        return res.getDimensionPixelSize(resourceId);
                    }
                }
            }
        } catch (Throwable e) {
            return 0;
        }
        return 0;
    }
}