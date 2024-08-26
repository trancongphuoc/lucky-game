// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 3/1/2010 8:04:21 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   VtExceptionInterceptor.java

package com.viettel.framework.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

public class VtExceptionInterceptor extends ExceptionMappingInterceptor
{

    public VtExceptionInterceptor()
    {
    }

    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder)
    {
        super.publishException(invocation, exceptionHolder);
    }
}