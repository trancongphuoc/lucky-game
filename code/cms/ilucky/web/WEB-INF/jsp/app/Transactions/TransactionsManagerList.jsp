<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateTransactions ==1">
    <vm:info value=" ${fn:escapeXml(Ret_updateTransactionsMsg)}"/>
</s:if>
<s:if test="#attr.TransactionsList !=null && #attr.TransactionsList.size() >0"> 
    <sx:div id="pagingTransactionsManagerDiv">
        <s:token/>
        <display:table targets="pagingTransactionsManagerDiv" requestURI="pagingTransactionsManager.do" id="TransactionsList" 
                       name="TransactionsList" cellpadding="1" cellspacing="1" pagesize="150" 
                       class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
               ${fn:escapeXml(TransactionsList_rowNum)}
            </display:column>    
            <fmt:message key="ss.stb" var="_stb" />
            <fmt:message key="cmd.ml" var="_ml" />
            <fmt:message key="cmd.tcp" var="_tcp" />
            <fmt:message key="menu.qldv.dv" var="_dv" />
            <fmt:message key="bd.gdv" var="_gdv" />
            <fmt:message key="ng.tgg" var="_tgg" />
            <fmt:message key="ng.tgtv" var="_tgtv" />
            <fmt:message key="ng.mtv" var="_mtv" />
            <fmt:message key="ng.tl" var="_tl" />
            <fmt:message key="ng.nd" var="_nd" />
            <fmt:message key="ng.g" var="_g" />
            <fmt:message key="cmd.chargeAc" var="_ac" />
            <fmt:message key="cmd.chargeAm" var="_am" />
            <fmt:message key="cmd.duration" var="_dura" />
            <fmt:message key="cmd.ktc" var="_ktc" />
            
            <display:column escapeXml="true" property="msisdn" title="${_stb}"></display:column>            
            <display:column escapeXml="true" property="cmd" title="${_ml}"></display:column>
            <%--<display:column escapeXml="true" property="provider_name" title="${_tcp}"></display:column>--%> 
            <%--<display:column escapeXml="true" property="service_name" title="${_dv}"></display:column>--%>
            <%--<display:column escapeXml="true" property="sub_service_name" title="${_gdv}"></display:column>--%>            
            <display:column escapeXml="true" property="request_time" title="${_tgg}"></display:column>
            <display:column escapeXml="true" property="response_time" title="${_tgtv}"></display:column>
            <display:column escapeXml="true" property="response_code" title="${_mtv}"></display:column>
            <display:column escapeXml="true" property="category_name" title="${_tl}"></display:column>
            <display:column escapeXml="true" property="item_name" title="Item"></display:column>
            <display:column escapeXml="true" property="contents" title="${_nd}"></display:column>
            <display:column escapeXml="true" property="price" title="${_g}"></display:column>             
            <%--<display:column escapeXml="true" property="charge_account" title="${_ac}"></display:column>--%>             
            <%--<display:column escapeXml="true" property="charge_amount" title="${_am}"></display:column>--%>             
            <%--<display:column escapeXml="true" property="duration" title="${_dura}"></display:column>--%>             
            <display:column title="${_ktc}" >
                <s:if test="#attr.TransactionsList.charge_type==0 or #attr.TransactionsList.charge_type==null">
                    <samp><fmt:message key="ng.kxd" /></samp>
                </s:if>
                <s:if test="#attr.TransactionsList.charge_type==1">
                    <samp>Mobile</samp>
                </s:if>
                <s:if test="#attr.TransactionsList.charge_type==2">
                    <samp>Card</samp>
                </s:if>
                <s:if test="#attr.TransactionsList.charge_type==3">
                    <samp>Banks</samp>
                </s:if>
                <s:if test="#attr.TransactionsList.charge_type==4">
                    <samp>VAS</samp>
                </s:if>                
            </display:column>  
                     <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1 ">
            <%--<s:if test="#attr.TransactionsList !=null && #attr.TransactionsList.size() >0">--%> 
            <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:100px" title="${_tht}" headerClass="sortable" >    
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="ViewMO_MT_Dlg('<s:property escapeJavaScript="true" value="%{#attr.TransactionsList.trans_id}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <img src="<%=request.getContextPath()%>/share/images/top_menu_logout.gif" valign="middle" title="<fmt:message key="wj.ndtn" />" alt="Nội dung tin nhắn"/>
                    </div>                    
                </display:column>
            <%--</s:if>--%>
                     </s:if>
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="ng.tsgd" />${fn:escapeXml(TransactionsList_rowNum)}</b>
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
