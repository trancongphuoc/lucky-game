<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateUser ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateUserMsg)}"/>--%>
    <fmt:message key="${Ret_updateUserMsg}" var="Ret_updateUserMsg_redefine"  >
        <fmt:param value="${Ret_updateUserMsg_param}" />
    </fmt:message>
    <vm:info value=" ${fn:escapeXml(Ret_updateUserMsg_redefine)}"/>
</s:if>
<s:if test="#attr.reportVoiceBroadCastList !=null && #attr.reportVoiceBroadCastList.size() >0">
    <sx:div id="pagingReportVoiceBroadCastDiv">
        <s:token/>
        <display:table targets="pagingReportVoiceBroadCastDiv" requestURI="pagingReportVoiceBroadCast.do" id="reportVoiceBroadCastList" 
                       name="reportVoiceBroadCastList" cellpadding="1" cellspacing="1" pagesize="15" 
                       class="dataTable" style="width:100%">
            <display:caption style="text-align: left;">
                <span style="font-size:12px;"><strong> Xuất thống kê ra: &nbsp;&nbsp;&nbsp;</strong></span>
                <input type="button" onclick="exportExcelReportVoiceBroadCast();"
                       style="text-decoration: underline; border: 1px solid #b5b8c8;
                  cursor:pointer;font-weight:
                  bold;color: #E7933D;font-style: inherit" value="Ms Excel"/>
                <s:div id="DivFormReportVoiceBroadCast"></s:div>
            </display:caption>
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(reportVoiceBroadCastList_rowNum)}
            </display:column>

            <display:column escapeXml="true" property="datetime" title="Date"></display:column>
            <display:column escapeXml="true" property="campaign_name" title="Campaign Name"></display:column>
            <display:column escapeXml="true" property="total_call" title="Total Call"></display:column>
            <display:column escapeXml="true" property="total_listen" title="Total Listen"></display:column>
            <display:column escapeXml="true" property="total_cancel" title="Total Cancel"></display:column>
            <display:column escapeXml="true" property="total_busy" title="Total Busy"></display:column>
            <display:column escapeXml="true" property="total_notfound" title="Total Not found"></display:column>
            <display:column escapeXml="true" property="total_error" title="Total Error"></display:column>
            <display:column escapeXml="true" property="total_complete" title="Total Complete"></display:column>
            <%--<display:column escapeXml="true" property="cp_name" title="Nhà cung cấp"></display:column>--%>
            
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b>Tổng số user: ${fn:escapeXml(reportVoiceBroadCastList_rowNum)}</b>
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

