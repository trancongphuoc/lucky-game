<%-- 
    Document   : Stats_RevenueManagerList
    Created on : Sep 10, 2013, 9:28:08 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>


<div>
    <s:if test="#attr.Ret_updateStats_RevenueManager ==1">
        <vm:info value=" ${fn:escapeXml(Ret_updateStats_RevenueManagerMsg)}"/>
    </s:if>
    
       <%--<s:property value="#attr.Stats_RevenueManagerList.size()  "/>--%> 
    <s:if test="#attr.Stats_RevenueManagerList !=null && #attr.Stats_RevenueManagerList.size() >0"> 
        <sx:div id="pagingStats_RevenueManagerDiv">
            <s:token/>
            <display:table targets="pagingStats_RevenueManagerDiv" requestURI="pagingStats_RevenueManager.do" id="Stats_RevenueManagerList" 
                           name="Stats_RevenueManagerList" cellpadding="1" cellspacing="1" pagesize="150" 
                           class="dataTable" style="width:100%"> 
                
            <fmt:message key="ng.dv" var="_dv" />
            <fmt:message key="cp.gc" var="_gc" />
            <fmt:message key="cmd.ml" var="_ml" />
            <fmt:message key="stats.Doanhthu" var="_Doanhthu" />
            <fmt:message key="c.tg" var="_tg" />
                
                <display:column  title="#" style="width:30px;text-align:center">
                    ${fn:escapeXml(Stats_RevenueManagerList_rowNum)}
                </display:column>    
                <display:column escapeXml="true" property="provider_name" title="CP"></display:column>            
                <display:column escapeXml="true" property="service_name" title="${_dv}"></display:column>  
                <display:column escapeXml="true" property="sub_service_name" title="${_gc}"></display:column>            
                <display:column escapeXml="true" property="cmd" title="${_ml}"></display:column>  
                <display:column escapeXml="true" style="text-align:right" property="revenue" title="${_Doanhthu}"></display:column>            
                <display:column escapeXml="true" property="stats_date" title="${_tg}"></display:column>  
                <display:footer>                    
                    <tr>
                        <td colspan="3">
                            <b><fmt:message key="c.ts" /> ${fn:escapeXml(Stats_RevenueManagerList_rowNum)}</b>
                        </td>
                    <tr>
                    </display:footer>
                </display:table>
                    <div id="fda" style="font: 20px;color: red; position: absolute;font-weight: bolder"><fmt:message key="stats.tdt" /></div>
            </sx:div>      
            <div  id="fda" style="margin-left:100px; color: red;font-weight: bolder">${fn:escapeXml(totalRevenue)}</div>
        </s:if>
       <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />                   
    <s:else>
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>

    </div>