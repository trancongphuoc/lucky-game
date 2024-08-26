<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateUsers_services ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateUsers_servicesMsg)}"/>--%>
    <fmt:message key="${Ret_updateUsers_servicesMsg}" var="Ret_updateUsers_servicesMsg_redefine"  >
        <fmt:param value="${Ret_updateUsers_servicesMsg_param}" />
    </fmt:message>
    <vm:info value=" ${fn:escapeXml(Ret_updateUsers_servicesMsg_redefine)}"/>
</s:if>
<s:if test="#attr.sUsers_servicesList !=null && #attr.sUsers_servicesList.size() >0"> 
    <sx:div id="pagingUsers_servicesManagerDiv">
        <s:token/>
        <display:table targets="pagingUsers_servicesManagerDiv" requestURI="pagingUsers_servicesManager.do" id="sUsers_servicesList" 
                       name="sUsers_servicesList" cellpadding="1" cellspacing="1" pagesize="150" 
                       class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(sUsers_servicesList_rowNum)}
            </display:column>
            <%--<display:column title='<input type="checkbox" name="allbox" onclick="checkAll();"/>' media="html" style="width:30px;text-align:center">--%>    
            <display:column title='#' media="html" style="width:30px;text-align:center">
                <s:if test="#attr.sUsers_servicesList.status ==1">
                    <input id="chkId" type="checkbox" checked="true" name="chkId" value="<s:property value="%{#attr.sUsers_servicesList.service_name}"/>" />                
                </s:if>
                <s:else>
                    <input id="chkId" type="checkbox" name="chkId" value="<s:property value="%{#attr.sUsers_servicesList.service_name}"/>" />                
                </s:else>
            </display:column>
                <fmt:message key="cmd.tcp" var="_tcp" /> 
                <fmt:message key="cp.tdv" var="_tdv" /> 
                <fmt:message key="cmd.nt" var="_nt" /> 
                <fmt:message key="cmd.mt" var="_mt" /> 
            <display:column escapeXml="true" property="provider_name" title="${_tcp}"></display:column>
            <display:column escapeXml="true" property="service_name" title="${_tdv}"></display:column>
            <display:column escapeXml="true" property="time_create" title="${_nt}"></display:column>
            <display:column escapeXml="true" property="descriptions" title="${_mt}"></display:column>          
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="sup.tsdv" /> ${fn:escapeXml(sUsers_servicesList_rowNum)}</b>
                    </td>
                <tr>
                </display:footer>
            </display:table>

        </sx:div>
    </s:if>
    <s:else>
  <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />       
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>
