<%-- 
    Document   : sessionTimeout
    Created on : Nov 19, 2010, 5:19:14 PM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
    </head>
    <body >        
        <script language="javascript">
            setTimeout(location.href= "<%=request.getContextPath()%>/Login!logout.do" ,1);
        </script>
    </body>
</html>
