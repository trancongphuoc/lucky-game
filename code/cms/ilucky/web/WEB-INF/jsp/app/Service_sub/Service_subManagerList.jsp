<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateService_sub ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateService_subMsg)}"/>--%>
     <fmt:message key="${Ret_updateService_subMsg}" var="Ret_updateService_subMsg_redefine"  >
                <fmt:param value="${Ret_updateService_subMsg_param}" />
            </fmt:message>
    <vm:info value=" ${fn:escapeXml(Ret_updateService_subMsg_redefine)}"/>
</s:if>
<s:if test="#attr.Service_subList !=null && #attr.Service_subList.size() >0"> 
    <sx:div id="pagingService_subManagerDiv">
        <s:token/>
        <display:table targets="pagingService_subManagerDiv" requestURI="pagingService_subManager.do" id="Service_subList" 
                       name="Service_subList" cellpadding="1" cellspacing="1" pagesize="150" 
                       class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(Service_subList_rowNum)}
            </display:column>  
            <fmt:message key="ss.stb" var="_stb" />
            <%--<fmt:message key="ng.mcp" var="_mcp" />--%>
            <fmt:message key="cmd.tth" var="_tth" />
            <display:column escapeXml="true" property="msisdn" title="${_stb}"></display:column> 
            <%--<display:column escapeXml="true" property="sub_service_code" title="${_mcp}"></display:column>--%> 
            <display:column title="${_tth}" >
                <s:if test="#attr.Service_subList.status==1">
                    <span><fmt:message key="sup.dk" /></span>
                </s:if>
                <s:if test="#attr.Service_subList.status==0">
                    <span><fmt:message key="cmd.h" /></span>
                </s:if>     
                <s:if test="#attr.Service_subList.status==2">
                    <span><fmt:message key="ng.td" /></span>
                </s:if>   
            </display:column>  
            <%--<display:column escapeXml="true" property="status" title="${_mcp}"></display:column>--%> 
            <%--<fmt:message key="ng.pr" var="_pr" />--%>
            <%--<fmt:message key="ng.dv" var="_dv" />--%>
            <fmt:message key="cp.gc" var="_gc" />
            <fmt:message key="ss.tgdk" var="_tgdk" />
            <fmt:message key="ss.tgh" var="_tgh" />
            <fmt:message key="cp.mt" var="_mt" />
            <fmt:message key="ng.tgtcsc" var="_tgtcsc" />
            <fmt:message key="ng.tgtctt" var="_tgtctt" />
            <fmt:message key="ng.tttc" var="_tttc" />
            <%--<display:column escapeXml="true" property="provider_name" title="${_pr}"></display:column>--%>
            <%--<display:column escapeXml="true" property="service_name" title="${_dv}"></display:column>--%> 
            <display:column escapeXml="true" property="sub_service_name" title="${_gc}"></display:column>
            <display:column escapeXml="true" property="register_time" title="${_tgdk}"></display:column>
            <display:column escapeXml="true" property="cancel_time" title="${_tgh}"></display:column>  
            <display:column escapeXml="true" property="descriptions" title="${_mt}"></display:column> 
            <display:column escapeXml="true" property="last_monfee_charge_time" title="${_tgtcsc}"></display:column> 
            <display:column escapeXml="true" property="next_monfee_charge_time" title="${_tgtctt}"></display:column>                          
            <display:column title="${_tttc}" >
                <s:if test="#attr.Service_subList.charge_status==1">
                    <fmt:message key="update.tc" var="_tc" />
                    <img src="<%=request.getContextPath()%>/share/img/tick.png"
                         valign="middle" title="${_tc}" alt="Thành công"/>
                </s:if>
                <s:if test="#attr.Service_subList.charge_status==0">
                    <fmt:message key="update.ktc" var="_ktc" />
                    <img src="<%=request.getContextPath()%>/share/img/publish_x.png"
                         valign="middle" title="${_ktc}" alt="Không thành công"/>
                </s:if>               
            </display:column>  
            <%--<display:column title="${_ckm}" >
                <fmt:message key="service.category.pricemanagerdlg.tt" var="_tt" />
                <s:if test="#attr.Service_mo_priceList.is_promotion==1">
                    <img src="<%=request.getContextPath()%>/share/img/tick.png"
                         valign="middle" title="${_tt}" alt="Khuyến mại"/>
                </s:if>
                <fmt:message key="service.category.pricemanagerdlg.ktt" var="_ktt" />    
                <s:if test="#attr.Service_mo_priceList.is_promotion==0">
                    <img src="<%=request.getContextPath()%>/share/img/publish_x.png"
                         valign="middle" title="${_ktt}" alt="Không khuyến mại"/>
                </s:if>               
            </display:column>  --%>
            
            <s:if test="#attr.userToken.status !=null && (#attr.userToken.status ==1 or #attr.userToken.userType == 2)">
                <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:100px" title="${_tht}" headerClass="sortable" >    
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="Service_sub_cancel('<s:property escapeJavaScript="true" value="%{#attr.Service_subList.msisdn}"/>', '<s:property escapeJavaScript="true" value="%{#attr.Service_subList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.cntb" var="_cntb" />
                        <img src="<%=request.getContextPath()%>/share/img/edit.gif" valign="middle" title="${_cntb}" alt="Cập nhập thuê bao"/>
                    </div>                    
                </display:column>
            </s:if>
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="ng.tss" /> ${fn:escapeXml(Service_subList_rowNum)}</b>
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
