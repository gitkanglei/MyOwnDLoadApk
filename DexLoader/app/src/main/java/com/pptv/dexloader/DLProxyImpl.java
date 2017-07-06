///*
// * Copyright (C) 2014 singwhatiwanna(任玉刚) <singwhatiwanna@gmail.com>
// *
// * collaborator:田啸,宋思宇,Mr.Simple
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.pptv.dexloader;
//
//import java.io.File;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.content.res.Resources.Theme;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//
//
//import com.pptv.plugin.DLPlugin;
//
//import dalvik.system.DexClassLoader;
//
///**
// *
// */
//public class DLProxyImpl
//{
//
//    private static final String TAG = "DLProxyImpl";
//
//    private String mClass;
//
//    private String mPackageName;
//
//    private AssetManager mAssetManager;
//
//    private Resources mResources;
//
//    private Theme mTheme;
//
//    private ActivityInfo mActivityInfo;
//
//    private Activity mProxyActivity;
//
//    protected DLPlugin mPluginActivity;
//
//    public DLProxyImpl(Activity activity)
//    {
//        mProxyActivity = activity;
//    }
//
//    public void onCreate(Intent intent)
//    {
//
//        // set the extra's class loader
//        Log.d(TAG, "mClass=" + mClass + " mPackageName=" + mPackageName);
//        mClass = intent.getStringExtra("Class");
//        try
//        {
//            String apkPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chajian_demo.apk";
//            // String mPath = getPackageResourcePath();
//            // 运用每个类中的class属性可进行反射
//            AssetManager assetManager = AssetManager.class.newInstance();
//            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
//            addAssetPathMethod.setAccessible(true);
//
//            // addAssetPathMethod.invoke(assetManager, mPath);
//            addAssetPathMethod.invoke(assetManager, apkPath);
//
//            // Method ensureStringBlocks =
//            // AssetManager.class.getDeclaredMethod("ensureStringBlocks");
//            // ensureStringBlocks.setAccessible(true);
//            // ensureStringBlocks.invoke(assetManager);
//
//            Resources supResource = mProxyActivity.getResources();
//            Log.e("Main", "supResource = " + supResource);
//            Resources mBundleResources = new Resources(assetManager, supResource.getDisplayMetrics(),
//                    supResource.getConfiguration());
//            // 在这里 去加apk 得到resource
//            mAssetManager = assetManager;
//            mResources = mBundleResources;
//            Field field = ((Context)mProxyActivity).getClass().getDeclaredField("mResources");
//            field.setAccessible(true);
//            field.set(((Context)mProxyActivity), mBundleResources);
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        launchTargetActivity();
//    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    protected void launchTargetActivity() {
//        try {
//            // 得到 加载 ClassLoader
//            String cachePath = mProxyActivity.getCacheDir().getAbsolutePath();
//            String apkPath = Environment.getExternalStorageDirectory()+ "/chajian_demo.apk";
//            File file = new File(apkPath);
//            if(!file.exists())
//            {
//                return;
//            }
//            DexClassLoader classLoader = new DexClassLoader(apkPath, cachePath, cachePath,((Context)mProxyActivity).getClassLoader());
//            Class<?> localClass = classLoader.loadClass(mClass);
//            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
//            Object instance = localConstructor.newInstance(new Object[] {});
//            //反射得到 插件里面的Activity
//            mPluginActivity = (DLPlugin) instance;
//            ((DLProxyActivity)mProxyActivity).attach(mPluginActivity);
//            Log.d(TAG, "instance = " + instance);
//            // attach the proxy activity and plugin package to the mPluginActivity
//            mPluginActivity.attach(mProxyActivity);
//
//            Bundle bundle = new Bundle();
//            bundle.putInt("extra.from", 1);
//            mPluginActivity.onCreate(bundle);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public AssetManager getAssets()
//    {
//        return mAssetManager;
//    }
//
//    public Resources getResources()
//    {
//        return mResources;
//    }
//
//    public Theme getTheme()
//    {
//        return mTheme;
//    }
//
//}
