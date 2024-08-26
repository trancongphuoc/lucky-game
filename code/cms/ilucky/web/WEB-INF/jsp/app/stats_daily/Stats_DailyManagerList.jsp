<%-- 
    Document   : Stats_DailyList
    Created on : Feb 17, 2014, 11:00:48 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@include file="../../common/language.jsp" %>


<s:if test="#attr.Stats_DailyList !=null && #attr.Stats_DailyList.size() >0">
    <sx:div id="pagingStats_DailyDiv">
        <display:table targets="pagingStats_DailyDiv"
                       requestURI="pagingStats_Daily.do" id="Stats_DailyList"
                       name="Stats_DailyList" cellpadding="1" cellspacing="1"
                       pagesize="300" class="dataTable" style="width:100%">
            <display:column title="STT" style="width:5%;text-align:center">
                ${Stats_DailyList_rowNum}
            </display:column>            
            <%--<display:column property="provider_name" style="text-align:left" title="CP" escapeXml="true"></display:column>--%>
            <fmt:message key="menu.qldv.dv" var="_dv" />
            <fmt:message key="menu.qldv.gc" var="_gc" />
            <fmt:message key="std.ch" var="_ch" />
            <fmt:message key="std.op" var="_op" />
            <fmt:message key="std.sc" var="_sc" />
            <fmt:message key="std.nf" var="_nf" />
            <fmt:message key="std.rt" var="_rt" />
            <%--<display:column property="report_time" style="text-align:left" title="report day" escapeXml="true"></display:column>--%>
           
            <%--<fmt:message key="cmd.ml" var="_ml" />--%>
            <display:column property="reportDay" style="text-align:left" title="report Day" escapeXml="true"></display:column>
            <display:column property="register" style="text-align:left" title="register" escapeXml="true"></display:column>
            <display:column property="cancel" style="text-align:left" title="cancel" escapeXml="true"></display:column>
            <display:column property="revenue" style="text-align:left" title="revenue" escapeXml="true"></display:column>
            <display:column property="totalSub" style="text-align:left" title="totalSub" escapeXml="true"></display:column>
            <display:column property="activeSub" style="text-align:left" title="activeSub" escapeXml="true"></display:column>
            <display:column property="pendingSub" style="text-align:left" title="pendingSub" escapeXml="true"></display:column>
            <display:column property="cancelSub" style="text-align:left" title="cancelSub" escapeXml="true"></display:column>
            <display:column property="cost" style="text-align:left" title="cost" escapeXml="true"></display:column>
            <display:column property="profit" style="text-align:left" title="profit" escapeXml="true"></display:column>
            <display:column property="moneyRegister" style="text-align:left" title="moneyRegister" escapeXml="true"></display:column>
            <display:column property="moneyBuy" style="text-align:left" title="moneyBuy" escapeXml="true"></display:column>
            <display:column property="moneyRenew" style="text-align:left" title="moneyRenew" escapeXml="true"></display:column>
            <display:column property="numPlay" style="text-align:left" title="numPlay" escapeXml="true"></display:column>
            <display:column property="numPlayer" style="text-align:left" title="numPlayer" escapeXml="true"></display:column>
        </display:table>

    </sx:div>

</s:if>
<s:else>
    <vm:alert value="Data not found"></vm:alert>
</s:else>