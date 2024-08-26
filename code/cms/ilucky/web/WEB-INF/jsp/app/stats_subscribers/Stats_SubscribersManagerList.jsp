<%-- 
    Document   : Stats_SubscribersManagerList
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


<div>
    <s:if test="#attr.Ret_updateStats_SubscribersManager ==1">
        <vm:info value=" ${fn:escapeXml(Ret_updateStats_SubscribersManagerMsg)}"/>
    </s:if>
    <s:if test="#attr.Stats_SubscribersManagerList !=null && #attr.Stats_SubscribersManagerList.size() >0"> 
        <sx:div id="pagingStats_SubscribersManagerDiv">
            <s:token/>
            <display:table targets="pagingStats_SubscribersManagerDiv" requestURI="pagingStats_SubscribersManager.do" id="Stats_SubscribersManagerList" 
                           name="Stats_SubscribersManagerList" cellpadding="1" cellspacing="1" pagesize="150" 
                           class="dataTable" style="width:100%">    
                
                <fmt:message key="ng.dv" var="_dv" />
                <fmt:message key="cp.gc" var="_gc" />
                <fmt:message key="stats.ttb" var="_ttb" />
                <fmt:message key="stats.ttbh" var="_ttbh" />
                <fmt:message key="stats.ttbdk" var="_ttbdk" />
                <fmt:message key="stats.ttbtctc" var="_ttbtctc" />
                <%--<fmt:message key="stats.ttbtc" var="_ttbtc" />--%>
                <fmt:message key="stats.ttbtdkt" var="_ttbtdkt" />
                <fmt:message key="c.tg" var="_tg" />
                
                <display:column  title="#" style="width:30px;text-align:center">
                    ${fn:escapeXml(Stats_SubscribersManagerList_rowNum)}
                </display:column>    
                <display:column escapeXml="true" property="provider_name" title="CP"></display:column>            
                <display:column escapeXml="true" property="service_name" title="${_dv}"></display:column>  
                <display:column escapeXml="true" property="sub_service_name" title="${_gc}"></display:column>            
                <display:column escapeXml="true" property="total_subs" title="${_ttb}"></display:column>  
                <display:column escapeXml="true" property="total_cancel" title="${_ttbh}"></display:column>  
                <display:column escapeXml="true" property="total_register" title="${_ttbdk}"></display:column>            
                <display:column escapeXml="true" property="total_monfee" title="${_ttbtctc}"></display:column>  
                <%--<display:column escapeXml="true" property="total_monfee_trans" title="${_ttbtc}"></display:column>--%>  
                <display:column escapeXml="true" property="total_register_real" title="${_ttbtdkt}"></display:column>  
                <display:column escapeXml="true" property="stats_date" title="${_tg}"></display:column>  
                <display:footer>
                    <tr>
                        <td colspan="3">
                            <b><fmt:message key="c.ts" /> ${fn:escapeXml(Stats_SubscribersManagerList_rowNum)}</b>
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

</div>