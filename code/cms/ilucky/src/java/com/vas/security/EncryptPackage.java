/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vas.security;

/**
 *
 * @author hanhnv
 */
public interface EncryptPackage
{

    public abstract byte[] decode(byte abyte0[])
        throws Exception;

    public abstract byte[] encode(byte abyte0[])
        throws Exception;
}

