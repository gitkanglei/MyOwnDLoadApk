package com.pptv.dexloader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.pptv.plugin.ProxyActivity;
import com.pptv.plugin.utils.PluginConstants;

import java.io.File;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tx).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
                String apkPath = Environment.getExternalStorageDirectory()+ "/chajian_demo.apk";
                intent.putExtra(PluginConstants.EXTRA_PLUGIN_APK_PATH,apkPath);
                intent.putExtra(PluginConstants.EXTRA_PLUGIN_CLASS, "com.huanju.chajiandemo.TestActivity");
                startActivity(intent);
                //launchTargetActivity();
            }
        });

    }
    protected void launchTargetActivity() {
        try {
            // 得到 加载 ClassLoader
            String cachePath = MainActivity.this.getCacheDir().getAbsolutePath();
            String apkPath = Environment.getExternalStorageDirectory()+ "/chajian_demo.apk";
            File file = new File(apkPath);
            if(!file.exists())
            {
                return;
            }
            DexClassLoader classLoader = new DexClassLoader(apkPath, cachePath, cachePath,getClassLoader());
            Class<?>mClasssLoader = classLoader.loadClass("com.huanju.chajiandemo.AActivity");
            Constructor<?> localConstructor = mClasssLoader.getConstructor(new Class[] {});
            Object instance = localConstructor.newInstance(new Object[] {});
            //反射得到 插件里面的Activity
//            mPluginActivity = (DLPlugin) instance;
//            ((DLProxyActivity)mProxyActivity).attach(mPluginActivity);
//            Log.d(TAG, "instance = " + instance);
//            // attach the proxy activity and plugin package to the mPluginActivity
//            mPluginActivity.attach(mProxyActivity);
//
//            Bundle bundle = new Bundle();
//            bundle.putInt(DLConstants.FROM, DLConstants.FROM_EXTERNAL);
//            mPluginActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(newBase);

        //
        // String cachePath =
        // MainActivity.this.getCacheDir().getAbsolutePath();
        // String apkPath =
        // Environment.getExternalStorageDirectory().getAbsolutePath() +
        // "/chajian_demo.apk";
        // // 用于加载其他包的classLoader
        // DexClassLoader mClassLoader = new DexClassLoader(apkPath,
        // cachePath, cachePath, getClassLoader());
        // MyHookHelper.inject(mClassLoader);
        //
        // try {
        // Class clz =
        // mClassLoader.loadClass("com.huanju.chajiandemo.TestActivity");
        // Object pbj = clz.newInstance();
        // MyProxyHandler handler = new MyProxyHandler(pbj);
        // Object o = handler.newInstance(mClassLoader);
        // Method attachBaseContext =
        // Activity.class.getDeclaredMethod("attachBaseContext",Context.class);
        // attachBaseContext.invoke(pbj,this);
        // Method oncreate =
        // Activity.class.getDeclaredMethod("onCreate",Bundle.class);
        // oncreate.setAccessible(true);
        // oncreate.invoke(pbj, new Bundle());
        //
        //
        //
        // // 可以动态加载一个apk中的类和方法
        //// Class clz =
        // mClassLoader.loadClass("com.huanju.chajiandemo.TestActivity");
        //// Object pbj = clz.newInstance();
        //// Method walk = pbj.getClass().getMethod("walk");
        //// walk.invoke(pbj);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

    }
}
