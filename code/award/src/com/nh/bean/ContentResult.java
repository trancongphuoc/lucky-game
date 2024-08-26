/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.bean;

/**
 *
 * @author hoand
 */
public class ContentResult {
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILED = 1;
    public static final int RESULT_ERROR = -1;
    
    private int mResult;
    private String mDesc;
    private int mMOPrice, mCmdPrice;

    public ContentResult(int mResult, String mDesc) {
        this.mResult = mResult;
        this.mDesc = mDesc;
        mMOPrice = 0;
        mCmdPrice = 0;
    }

    public ContentResult(int mResult, String mDesc, int mMOPrice, int mCmdPrice) {
        this.mResult = mResult;
        this.mDesc = mDesc;
        this.mMOPrice = mMOPrice;
        this.mCmdPrice = mCmdPrice;
    }

    public int getResult() {
        return mResult;
    }

    public String getDesc() {
        return mDesc;
    }

    public int getMOPrice() {
        return mMOPrice;
    }

    public int getCmdPrice() {
        return mCmdPrice;
    }

    @Override
    public String toString() {
        return getResult() +"|" + getDesc();
    }
}
