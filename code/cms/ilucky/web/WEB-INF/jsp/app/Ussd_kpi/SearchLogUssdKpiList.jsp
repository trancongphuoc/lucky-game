<%-- 
    Document   : SearchLogUssdKpiList
    Created on : Dec 27, 2018, 5:08:11 PM
    Author     : TRIEUNGUYEN
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>


<s:if test="#attr.SearchLog ==1">
   
    <fmt:message key="${SearchLogMsg}" var="SearchLogMsg_redefine"  >
        <fmt:param value="${SearchLogMsg_param}" />
    </fmt:message>
<vm:info value=" ${fn:escapeXml(SearchLogMsg_redefine)}"/>
</s:if>
<s:if test="#attr.LogUssdKpiList !=null && #attr.LogUssdKpiList.size() >0"> 
    <sx:div id="pagingSearchLogUssdKpiListDiv">
        <s:token/>
        <display:table targets="pagingSearchLogUssdKpiListDiv" requestURI="pagingSearchLogUssdKpi.do" id="LogUssdKpiList" 
                       name="LogUssdKpiList" cellpadding="1" cellspacing="1" pagesize="10" 
                       class="dataTable" style="width:100%">    
            
            <display:column  title="No" style="width:30px;text-align:center">
                ${fn:escapeXml(LogUssdKpiList_rowNum)}
            </display:column>    
            <fmt:message key="sl.NAME" var="_NAME" />
            <fmt:message key="sl.CHANNEL" var="_CHANNEL" />
            <fmt:message key="sl.TRANS_ID" var="_TRANS_ID" />
            <fmt:message key="sl.MSISDN" var="_MSISDN" />
            <fmt:message key="sl.TYPE" var="_TYPE" />
            <fmt:message key="sl.DURATION" var="_DURATION" />
            <fmt:message key="sl.URL" var="_URL" />
            <fmt:message key="sl.BODY" var="_BODY" />
            <fmt:message key="sl.RESPONSE" var="_RESPONSE" />
            <fmt:message key="sl.REQUEST_TIME" var="_REQUEST_TIME" />
            <fmt:message key="sl.DESCRIPTION" var="_DESCRIPTION" />
            <fmt:message key="sl.ERROR_CODE" var="_ERROR_CODE" />

            <display:column escapeXml="true" property="transId" title="call-id"></display:column>    
            <display:column escapeXml="true" property="calling" title="calling"></display:column>             
            <display:column escapeXml="true" property="called" title="called"></display:column> 
            <%--<display:column escapeXml="true" property="requestTime" title="requestTime"></display:column>--%>
            <display:column escapeXml="true" property="created" title="Time"></display:column>

            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="ng.tsgd" /> ${fn:escapeXml(LogUssdKpiList_rowNum)}</b>
                    </td>
                <tr>
                </display:footer>
            </display:table>
        </sx:div>
    </s:if>
    <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />                   
    <s:else>
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>

