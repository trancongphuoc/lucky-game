<%-- 
    Document   : Stats_Error_Sub_Service
    Created on : Feb 14, 2014, 10:53:49 AM
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


<sx:div id="listStats_Error_Sub_ServiceDiv">
    <s:if test="#request.Stats_ReSub_ServiceList !=null">
<%--        <s:select list="#request.Stats_ReSub_ServiceList"
                  listKey="sub_service_name" listValue="sub_service_name"
                  headerKey="-1" headerValue="--Chọn gói cước--"
                  name="Stats_ErrorManagerForm.sub_service_name"
                  id="Stats_ErrorManagerForm.sub_service_name" cssStyle="width:150px;">
        </s:select>   --%>
        <select 
                  name="Stats_ErrorManagerForm.sub_service_name"
                  id="Stats_ErrorManagerForm.sub_service_name" cssStyle="width:150px;">
                        <option value="-1" > <fmt:message key="sub.choose" /></option>
            <c:forEach items="${Stats_ReSub_ServiceList}" var="data">
                <option value="${data.sub_service_name}" > ${data.sub_service_name}</option>
            </c:forEach>
        </select> 
    </s:if>
</sx:div>