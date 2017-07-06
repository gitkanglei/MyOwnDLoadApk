package com.pptv.dexloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @anthor LeiKang
 */
public class MyProxyHandler implements InvocationHandler
{
    private Object mTarget;

    public MyProxyHandler(Object target)
    {
        mTarget = target;
    }

    public Object newInstance(ClassLoader classLoader)
    {
        return Proxy.newProxyInstance(classLoader, mTarget.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        Object result = method.invoke(mTarget, args);
        return result;
    }
}
