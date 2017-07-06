package com.pptv.dexloader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @anthor LeiKang
 *
 * 通过代理activity 来启动一个插件的activity
 */
public class Proxy extends Activity
{
    private Resources mBundleResources;

    private AssetManager assetManager;

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(context);
       // replaceContextResources(context);
    }


    public void replaceContextResources(Context context)
    {

        try
        {
            String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chajian_demo.apk";
            // String mPath = getPackageResourcePath();
            // 运用每个类中的class属性可进行反射
            assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);

            // addAssetPathMethod.invoke(assetManager, mPath);
            addAssetPathMethod.invoke(assetManager, apkPath);

            // Method ensureStringBlocks =
            // AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            // ensureStringBlocks.setAccessible(true);
            // ensureStringBlocks.invoke(assetManager);

            Resources supResource = getResources();
            Log.e("Main", "supResource = " + supResource);
            mBundleResources = new Resources(assetManager, supResource.getDisplayMetrics(),
                    supResource.getConfiguration());
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            field.set(context, mBundleResources);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // 重写getResources 让宿主activity 可以获取到插件的的资源resources
    @Override
    public Resources getResources()
    {
        return mBundleResources == null ? super.getResources() : mBundleResources;
    }

    @Override
    public AssetManager getAssets()
    {
        return assetManager == null ? super.getAssets() : assetManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("Class");
        try
        {
            String cachePath = this.getCacheDir().getAbsolutePath();
            String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chajian_demo.apk";
            // 用于加载其他包的classLoader 先得到类加载器
            DexClassLoader mClassLoader = new DexClassLoader(apkPath, cachePath, cachePath, getClassLoader());
            // 用类加载器来加载apk中的activity class
            Class<?> localClass = mClassLoader.loadClass(className);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
            Object instance = localClass.newInstance();
//            Method setProxy = localClass.getMethod("setProxy", new Class[] {Activity.class});
//            setProxy.setAccessible(true);
//            // 我这个根本不算是启动了插件中的activity 只是相当于用了插件里的resource
//            setProxy.invoke(instance, new Object[] {this});
              Activity activity = (Activity) instance;
            // 调用插件的onCreate()
            Method onCreate = localClass.getDeclaredMethod("onCreate",new Class[]{Bundle.class});
            onCreate.setAccessible(true);
            onCreate.invoke(instance,new Object[]{new Bundle()});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
