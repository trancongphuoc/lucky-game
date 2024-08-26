<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateCommands ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateCommandsMsg)}"/>--%>
     <fmt:message key="${Ret_updateCommandsMsg}" var="Ret_updateCommandsMsg_redefine"  >
                <vm:info value="${Ret_updateCommandsMsg_param}" />
            </fmt:message>
<vm:info value=" ${fn:escapeXml(Ret_updateCommandsMsg_redefine)}"/>
</s:if>
<s:if test="#attr.CommandsList !=null && #attr.CommandsList.size() >0"> 
    <sx:div id="pagingCommandsManagerDiv">
        <s:token/>

        <display:table targets="pagingCommandsManagerDiv" requestURI="pagingCommandsManager.do" id="CommandsList" 
                        name="CommandsList" cellpadding="1" cellspacing="1" pagesize="150" 
                        class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(CommandsList_rowNum)}
            </display:column>    
            <fmt:message key="cmd.ml" var="_ml" />
        <fmt:message key="cmd.gc" var="_gc" />
        <fmt:message key="cmd.nt" var="_nt" />
        <fmt:message key="cmd.ct" var="_ct" />
        <fmt:message key="cmd.tth" var="_tth" />
            <display:column escapeXml="true" property="cmd" title="${_ml}" />
            <display:column escapeXml="true" property="sub_service_name" title="${_gc}"></display:column>
            <display:column escapeXml="true" property="datetime" title="${_nt}"></display:column>
            <display:column escapeXml="true" property="details" title="${_ct}"></display:column>
             <display:column title="${_tth}" >
                 <fmt:message key="cc.hd" var="_hd" />
                <s:if test="#attr.CommandsList.status==1">
                    <img src="<%=request.getContextPath()%>/share/img/tick.png"
                         valign="middle" title="${_hd}" alt="Hoạt động"/>
                </s:if>
                    <fmt:message key="cc.khd" var="_khd" />
                <s:if test="#attr.CommandsList.status==0">
                    <img src="<%=request.getContextPath()%>/share/img/publish_x.png"
                         valign="middle" title="${_khd}" alt="Không hoạt động"/>
                </s:if>
            </display:column>                      
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:100px" title="${_tht}" headerClass="sortable" >                    
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="prepareEditCommands('<s:property escapeJavaScript="true" value="%{#attr.CommandsList.cmd}"/>','<s:property escapeJavaScript="true" value="%{#attr.CommandsList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.s" var="_s" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-edit.gif" valign="middle" title="${_s}" alt="edit"/>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="prepareDeleteCommands('<s:property escapeJavaScript="true" value="%{#attr.CommandsList.cmd}"/>','<s:property escapeJavaScript="true" value="%{#attr.CommandsList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.x" var="_x" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-delete.gif" valign="middle" title="${_x}" alt="delete"/>
                    </div>
                </display:column>
            </s:if>
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="mse.tsml" /> ${fn:escapeXml(CommandsList_rowNum)}</b>
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
