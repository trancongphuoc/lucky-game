<%-- 
    Document   : respondMessage
    Created on : May 15, 2009, 10:25:40 AM
    Author     : ThanhDat
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<!--div id="resError"-->
<%
    String str = request.getAttribute("strAlert").toString();
    if(str.equals("update.success")){

%>

<%--<vm:alert value="Cập nhật thành công">--%>
<dl id="system-message"> <dt class="message">Success</dt>
    <dd class="message message fade">
        <ul> <li>${strAlert}.</li>
        </ul>
    </dd>
</dl>
<%
}else{

        %>
<dl id="system-message"> <dt class="error">Error</dt>
    <dd class="error message fade">
        <ul> <li>${strAlert}.</li>
        </ul>
    </dd>
</dl>
<%
}
%>



