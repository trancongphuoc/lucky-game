/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vas.util;

/**
 *
 * @author Tr
 */
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

// Referenced classes of package com.viettel.util:
//            AppServerConn

public class showImageVT extends HttpServlet
{

    public showImageVT()
    {
        try
        {
            jbInit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void init()
        throws ServletException
    {
    }




    public Connection getDBConnection(String user, String password, String SID)
        throws Exception
    {
        Connection conn = null;
        String uRL = "";
        try
        {
            uRL = "jdbc:oracle:thin:@" + SID;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(uRL, user, password);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        return conn;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        doGet(req, res);
    }

    public void destroy()
    {
    }

    public static byte[] readByteBlob(Blob myBlob)
        throws IOException, SQLException
    {
        if(myBlob == null)
            return null;
        InputStream is = myBlob.getBinaryStream();
        byte b1[] = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte line[] = new byte[1024];
        int i = 0;
        do
        {
            i = is.read(line, 0, line.length);
            if(i != -1)
                bos.write(line, 0, i);
        } while(i != -1);
        byte return_value[] = null;
        return_value = bos.toByteArray();
        return return_value;
    }

    private void jbInit()
        throws Exception
    {
    }
}
