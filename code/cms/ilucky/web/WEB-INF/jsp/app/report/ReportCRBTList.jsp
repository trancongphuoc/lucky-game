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
<s:if test="#attr.reportCRBTList !=null && #attr.reportCRBTList.size() >0">
    <sx:div id="pagingReportCRBTDiv">
        <s:token/>
        <display:table targets="pagingReportCRBTDiv" requestURI="pagingReportCRBT.do" id="reportCRBTList" 
                       name="reportCRBTList" cellpadding="1" cellspacing="1" pagesize="15" 
                       class="dataTable" style="width:100%">
            <display:caption style="text-align: left;">
                <span style="font-size:12px;"><strong> Xuất thống kê ra: &nbsp;&nbsp;&nbsp;</strong></span>
                <input type="button" onclick="exportExcelReportCRBT();"
                       style="text-decoration: underline; border: 1px solid #b5b8c8;
                  cursor:pointer;font-weight:
                  bold;color: #E7933D;font-style: inherit" value="Ms Excel"/>
                <s:div id="DivFormReportCRBT"></s:div>
            </display:caption>
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(reportCRBTList_rowNum)}
            </display:column>

            <display:column escapeXml="true" property="datetime" title="Date"></display:column>
            <display:column escapeXml="true" property="campaign_name" title="Campaign Name"></display:column>
            <display:column escapeXml="true" property="total_push_1" title="Total Push 1"></display:column>
            <display:column escapeXml="true" property="total_download_success" title="Total Download Success"></display:column>
            <display:column escapeXml="true" property="total_download_fail_by_charge" title="Total Download Fail By Charge"></display:column>
            <display:column escapeXml="true" property="total_not_registed_crbt" title="Total Not Registed CRBT"></display:column>
            <display:column escapeXml="true" property="total_exist_in_bst" title="Total Exist In BST"></display:column>
            <display:column escapeXml="true" property="total_push_2" title="Total Push 2"></display:column>
            <display:column escapeXml="true" property="total_complete" title="Total Complete"></display:column>
            <%--<display:column escapeXml="true" property="cp_name" title="Nhà cung cấp"></display:column>--%>
            
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b>Tổng số user: ${fn:escapeXml(reportCRBTList_rowNum)}</b>
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

