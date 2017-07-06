# MyOwnDLoadApk
在学习插件化过程中自己写的动态加载apk 的demo

学习动态加载apk的过程：

动态加载的3个问题：
1.资源访问的问题 resources （在attachBaseContext去加载插件apk中的资源）；
2.activity生命周期的管理 （用接口去管理）；
3.插件classLoader的管理（在插件的基类activity中 ，每加载一个插件的activity就会有一个classLoader）；

大致思路：
1.新建一个library  ： Ipluign 接口去管理插件中的activity的生命周期，IpluignActivity 插件中activity的基类
在宿主程序中有一个代理proxyActivity,在oncreate 去加载插件activity 把插件activity强转成接口Iplugin 在proxyActivity的生命周期中依次去调用相应的方法；

ps:
在宿主代理ProxyActivity 中的attachBaseContext 去替换资源 (在插件activity中去替换资源会报resourcesNofouned);
在编译插件apk的时候 library jar 只参与插件apk的编译 不参与打包否则会报错；
dependencies
{
provided fileTree('libs/mysdk.jar')
}

