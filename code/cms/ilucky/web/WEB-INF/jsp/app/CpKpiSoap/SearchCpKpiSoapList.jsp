<%-- 
    Document   : SearchCpKpiSoapMsgList
    Created on : Jan 3, 2019, 4:44:15 PM
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


<s:if test="#attr.SearchCp ==1">
    <fmt:message key="${SearchCpKpiSoapMsg}" var="SearchCpKpiSoapMsg_redefine"  >
        <fmt:param value="${SearchCpKpiSoapMsg_param}" />
    </fmt:message>
    <vm:info value=" ${fn:escapeXml(SearchCpKpiSoapMsg_redefine)}"/>
</s:if>
<s:property value="#attr.CpKpiSoapList.size() "/>
<s:if test="#attr.CpKpiSoapList !=null && #attr.CpKpiSoapList.size() >0"> 
    <sx:div id="pagingSearchCpKpiSoapListDiv">
        <s:token/>
        <display:table targets="pagingSearchCpKpiSoapListDiv" requestURI="pagingSearchCpKpiSoap.do" id="CpKpiSoapList" 
                       name="CpKpiSoapList" cellpadding="1" cellspacing="1" pagesize="10" 
                       class="dataTable" style="width:100%">    

            <display:column  title="No" style="width:30px;text-align:center">
                ${fn:escapeXml(CpKpiSoapList_rowNum)}
            </display:column>    
            <fmt:message key="sl.PROVIDERNAME" var="_PNAME" />
            <fmt:message key="sl.SUBSERVICENAME" var="_SSNAME" />
            <fmt:message key="sl.CHANNEL" var="_CHANNEL" />
            <fmt:message key="sl.TRANS_ID" var="_TRANS_ID" />
            <fmt:message key="sl.MSISDN" var="_MSISDN" />
            <fmt:message key="sl.ACTION" var="_ACTION" />
            <fmt:message key="sl.DURATION" var="_DURATION" />
            <fmt:message key="sl.URL" var="_URL" />
            <fmt:message key="sl.BODY" var="_BODY" />
            <fmt:message key="sl.RESPONSE" var="_RESPONSE" />
            <fmt:message key="sl.REQUEST_TIME" var="_REQUEST_TIME" />
            <fmt:message key="sl.DESCRIPTION" var="_DESCRIPTION" />
            <fmt:message key="sl.ERROR_CODE" var="_ERROR_CODE" />

            <display:column escapeXml="true" property="providerName" title="${_PNAME}"></display:column>    
            <display:column escapeXml="true" property="subServiceName" title="${_SSNAME}"></display:column>  
            <display:column escapeXml="true" property="channel" title="${_CHANNEL}"></display:column>         
            <display:column escapeXml="true" property="transId" title="${_TRANS_ID}"></display:column> 
            <display:column escapeXml="true" property="msisdn" title="${_MSISDN}"></display:column> 
            <display:column escapeXml="true" property="action" title="${_ACTION}"></display:column>  
            <display:column escapeXml="true" property="duration" title="${_DURATION}"></display:column>            
            <display:column escapeXml="true" property="url" title="${_URL}"></display:column>
            <display:column escapeXml="true" property="body" title="${_BODY}"></display:column>
            <display:column escapeXml="true" property="response" title="${_RESPONSE}"></display:column>
            <display:column escapeXml="true" property="requestTime" title="${_REQUEST_TIME}"></display:column>
            <display:column escapeXml="true" property="description" title="${_DESCRIPTION}"></display:column>
            <display:column escapeXml="true" property="errorCode" title="${_ERROR_CODE}"></display:column>             

            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="ng.tsgd" /> ${fn:escapeXml(CpKpiSoapList_rowNum)}</b>
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

