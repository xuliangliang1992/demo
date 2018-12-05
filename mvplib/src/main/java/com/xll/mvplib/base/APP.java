package com.xll.mvplib.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.xll.mvplib.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * application基类 主要做一些共有的初始化动作
 *
 * @author xll
 * @date 2018/1/1
 */
public class APP extends Application {

    public static int APP_VERSION_CODE;
    public static String APP_VERSION_NAME;

    public static String APP_PACKAGE;//包名
    public static String APP_NAME;//应用名称

    public static String FINGERPRINT;//设备指纹

    public static String OS_VERSION;//系统版本号
    public static String BRAND;//设备名称 例如 huawei  meizu
    public static String MODEL;//设备型号 例如 BAH-W09  m3

    public static Locale defLocale;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initLogger();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Logger.i("width = " + displayMetrics.widthPixels + "\n" + "height = " + displayMetrics.heightPixels);
    }

    /**
     * 初始化日志
     */
    private void initLogger() {
        Logger.init(APP.class.getSimpleName()).methodCount(3)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)//打release版的时候 改为NONE
                .methodCount(1)
                .methodOffset(5);
    }

    /**
     * 初始化基础信息
     */
    protected void init() {
        final Configuration config = getApplicationContext().getResources().getConfiguration();
        defLocale = config.locale;
        Properties BUILD_PROPS = new Properties();
        try {
            BUILD_PROPS.load(new FileInputStream("/system/build.prop"));
        } catch (final Throwable th) {
        }

        final PackageManager pm = getPackageManager();
        try {
            final PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);

            APP_NAME = getString(pi.applicationInfo.labelRes);
            Logger.d("info", "APP_NAME == " + APP_NAME);
            APP_VERSION_CODE = pi.versionCode;
            APP_VERSION_NAME = pi.versionName;
            APP_PACKAGE = pi.packageName;
            File EXT_STORAGE = Environment.getExternalStorageDirectory();
            FINGERPRINT = Build.FINGERPRINT;
            OS_VERSION = Build.VERSION.RELEASE;
            BRAND = Build.BRAND;
            MODEL = Build.MODEL;

            Logger.i(APP_NAME + " (" + APP_PACKAGE + ")" + APP_VERSION_NAME + "(" + pi.versionCode + ")");
            Logger.i("Root             dir: " + Environment.getRootDirectory());
            Logger.i("Data             dir: " + Environment.getDataDirectory());
            Logger.i("External storage dir: " + EXT_STORAGE);
            Logger.i("Files            dir: " + FileUtils.getAbsolutePath(getFilesDir()));
            Logger.i("Cache            dir: " + FileUtils.getAbsolutePath(getCacheDir()));
            Logger.i("System locale       : " + defLocale);
            Logger.i("BOARD       : " + Build.BOARD);
            Logger.i("BRAND       : " + Build.BRAND);
            Logger.i("CPU_ABI     : " + BUILD_PROPS.getProperty("ro.product.cpu.abi"));
            Logger.i("CPU_ABI2    : " + BUILD_PROPS.getProperty("ro.product.cpu.abi2"));
            Logger.i("DEVICE      : " + Build.DEVICE);
            Logger.i("DISPLAY     : " + Build.DISPLAY);
            Logger.i("FINGERPRINT : " + Build.FINGERPRINT);
            Logger.i("ID          : " + Build.ID);
            Logger.i("MANUFACTURER: " + BUILD_PROPS.getProperty("ro.product.manufacturer"));
            Logger.i("MODEL       : " + Build.MODEL);
            Logger.i("PRODUCT     : " + Build.PRODUCT);
            Logger.i("RELEASE VERSION:" + Build.VERSION.RELEASE);
        } catch (final PackageManager.NameNotFoundException e) {
            Logger.w("init NameNotFoundException", e);
        }
    }

}
