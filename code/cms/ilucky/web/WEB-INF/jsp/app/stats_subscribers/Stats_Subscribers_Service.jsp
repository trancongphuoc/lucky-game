
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


<sx:div id="listStats_Subscribers_ServiceDiv">    
    <s:if test="#request.Services_SubList !=null">
        <%--<s:select onchange="searchSub_Service_Stats_sub();" list="#request.Services_SubList"
                  listKey="service_name" listValue="service_name"
                  headerKey="-1" headerValue="--Chọn dịch vụ--"
                  name="Stats_SubscribersManagerForm.service_name"
                  id="Stats_SubscribersManagerForm.service_name" cssStyle="width:150px;">
        </s:select>  --%> 
        <select   onchange="searchSub_Service_Stats_sub()"
                  name="Stats_SubscribersManagerForm.service_name"
                  id="Stats_SubscribersManagerForm.service_name" cssStyle="width:150px;">
                 <option value="-1" > <fmt:message key="cc.cc" /></option>
            <c:forEach items="${Services_SubList}" var="data">
                <option value="${data.service_name}" > ${data.service_name}</option>
            </c:forEach>
        </select> 
    </s:if>
</sx:div>