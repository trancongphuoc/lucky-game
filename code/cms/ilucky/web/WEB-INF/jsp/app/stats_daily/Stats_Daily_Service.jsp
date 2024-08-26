<%-- 
    Document   : Stats_Daily_Service
    Created on : Feb 19, 2014, 10:54:10 AM
    Author     : hanhnv62
--%>

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
<%@include file="../../common/language.jsp" %>


<sx:div id="listStats_Daily_ServiceDiv">    
    <s:if test="#request.ServicesList !=null">

        <select onchange="searchSub_Service_Stats_Da()"
            name="Stats_DailyManagerForm.service_name"
            id="Stats_DailyManagerForm.service_name" cssStyle="width:150px;">
            <option value="-1" > <fmt:message key="cc.cc" /></option>
            <c:forEach items="${ServicesList}" var="data">
                <option value="${data.service_name}" > ${data.service_name}</option>
            </c:forEach>
        </select>
    </s:if>
</sx:div>
