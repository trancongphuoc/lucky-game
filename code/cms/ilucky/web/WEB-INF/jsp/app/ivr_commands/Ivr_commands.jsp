<%-- 
    Document   : Ivr_commands
    Created on : Dec 19, 2013, 4:06:47 PM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="vb" tagdir="/WEB-INF/tags/vcl/button/" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>
<!DOCTYPE html>

<sx:div id="listIvr_commandsDiv">
    <s:if test="#request.mSub_ServiceList !=null">
        <select
            name="Ivr_commandsManagerForm.sub_service_name"
            id="Ivr_commandsManagerForm.sub_service_name" Style="width:170px;">
            <option value="-1" > <fmt:message key="sub.choose" /></option>
            <c:forEach items="${mSub_ServiceList}" var="data">
                <option value="${data.sub_service_name}" > ${data.sub_service_name}</option>
            </c:forEach>
        </select>
    </s:if>
</sx:div>
