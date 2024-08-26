<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="vb" tagdir="/WEB-INF/tags/vcl/button/" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="common/language.jsp" %>'

<div align="center" style="height: 80%">
    <h1>        
        <p><fmt:message key="home.welcome1" /> <s:property escapeJavaScript="true" value="#attr.userToken.username"></s:property>.</p>
        <p><fmt:message key="home.welcome2" /></p>
            <p style="color: red"><s:property value="#attr.informationLogin" escapeJavaScript="true"></s:property></p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>        
    </h1>    
</div>