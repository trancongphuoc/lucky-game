<%-- 
    Document   : Commands
    Created on : Dec 19, 2013, 9:19:36 AM
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

<sx:div id="listCommandsDiv">
    <s:if test="#request.cSub_ServiceList !=null">   
     <%--   <s:select required="true" list="#request.cSub_ServiceList"
                  listKey="sub_service_name" listValue="sub_service_name"
                  headerKey="-1" headerValue="--Chọn gói cước--"
                  name="CommandsManagerForm.sub_service_name"
                  id="CommandsManagerForm.sub_service_name" cssStyle="width:150px;">
        </s:select>  --%>    
        <select
            name="CommandsManagerForm.sub_service_name"
            id="CommandsManagerForm.sub_service_name" cssStyle="width:150px;">
                <option value="-1" > <fmt:message key="sub.choose" /></option>
            <c:forEach items="${cSub_ServiceList}" var="data">
                <option value="${data.sub_service_name}" > ${data.sub_service_name}</option>
            </c:forEach>
        </select>
    </s:if>
</sx:div>
