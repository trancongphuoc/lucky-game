/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vas.util;

/**
 *
 * @author Administrator
 */
import com.vas.security.MAXEncrypt;

import java.io.PrintStream;
import java.sql.Connection;

// Referenced classes of package com.viettel.util:
//            AppServerConn

public class ParamsVT
{

    public ParamsVT()
    {
    }

    public static final MAXEncrypt enc;

    static
    {
        enc = new MAXEncrypt();
        enc.encrypterGeneration();
        //throw exception;
    }
}