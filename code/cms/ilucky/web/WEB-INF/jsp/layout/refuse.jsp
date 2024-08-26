<%-- 
    Document   : refuse
    Created on : Dec 8, 2009, 11:41:55 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"  %>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>

<div class="pageTitle">
    <div id="pageTitleLabel">Home page</div>
    <span id="pageTitleSpan">
        <a href="<%=request.getContextPath()%>/logout.do"><span id="logoutSpan">Logout</span></a>
        <a><span id="userSpan"><s:property escapeJavaScript="true" value="#attr.userToken.username"></s:property></span></a>
        <a href="<%=request.getContextPath()%>/Authentication.do"><span id="homeSpan">Home page</span></a>
    </span>
</div>
<br/>
<div align="center" style="color:red; font-size:14px">
    User khong co quyen su dung chuc nang nay.
</div>