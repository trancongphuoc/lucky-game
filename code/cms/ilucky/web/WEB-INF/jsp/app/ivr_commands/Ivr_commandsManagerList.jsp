<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateIvr_commands ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateIvr_commandsMsg)}"/>--%>
     <fmt:message key="${Ret_updateIvr_commandsMsg}" var="Ret_updateIvr_commandsMsg_redefine"  >
                <fmt:param value="${Ret_updateIvr_commandsMsg_param}" />
            </fmt:message>
    <vm:info value=" ${fn:escapeXml(Ret_updateIvr_commandsMsg_redefine)}"/>
</s:if>
<s:if test="#attr.Ivr_commandsList !=null && #attr.Ivr_commandsList.size() >0"> 
    <sx:div id="pagingIvr_commandsManagerDiv">
        <s:token/>
        <display:table targets="pagingIvr_commandsManagerDiv" requestURI="pagingIvr_commandsManager.do" id="Ivr_commandsList" 
                       name="Ivr_commandsList" cellpadding="1" cellspacing="1" pagesize="150" 
                       class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(Ivr_commandsList_rowNum)}
            </display:column>
            <fmt:message key="cp.gc" var="_gc" />
            <fmt:message key="cmd.nt" var="_nt" />
            <fmt:message key="cmd.k" var="_k" />
            <display:column escapeXml="true" property="questionCode" title="question Code"></display:column>
            <display:column escapeXml="true" property="content" title="content"></display:column>
            <display:column escapeXml="true" property="answer1" title="answer1"></display:column>
            <display:column escapeXml="true" property="answer2" title="answer2"></display:column>
            <display:column escapeXml="true" property="answer3" title="answer3"></display:column>
            <display:column escapeXml="true" property="answer4" title="answer4"></display:column>
            <%--<display:column escapeXml="true" property="answerCorrect" title="answer Correct"></display:column>--%>
            <%--<display:column escapeXml="true" property="difficult" title="difficult"></display:column>--%>
  
            <display:column title="difficult" >
                <s:if test="#attr.Ivr_commandsList.difficult==1">
                    <samp>easy</samp>
                </s:if>
                <s:if test="#attr.Ivr_commandsList.difficult==2">
                    <samp>normal</samp>
                </s:if>
                <s:if test="#attr.Ivr_commandsList.difficult==3">
                    <samp>hard</samp>
                </s:if>
            </display:column>
            
         <%--   <fmt:message key="cmd.tth" var="_tth" />      
            <display:column title="${_tth}" >
                <fmt:message key="cc.hd" var="_hd" />
                <s:if test="#attr.Ivr_commandsList.status==1">
                    <img src="<%=request.getContextPath()%>/share/img/tick.png"
                         valign="middle" title="${_hd}" alt="Hoạt động"/>
                </s:if>
                    <fmt:message key="cc.khd" var="_khd" />
                <s:if test="#attr.Ivr_commandsList.status==0">
                    <img src="<%=request.getContextPath()%>/share/img/publish_x.png"
                         valign="middle" title="${_khd}" alt="Không hoạt động"/>
                </s:if>
           </display:column>
                 <fmt:message key="cmd.ltc" var="_ltc" />
            <display:column escapeXml="true" property="charge_cmd" title="${_ltc}"></display:column>            
--%>
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:100px" title="${_tht}" headerClass="sortable" >                    
                    
                    <div onclick="prepareEditIvr_commands('<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.s" var="_s" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-edit.gif" valign="middle" title="${_s}" alt="edit"/>
                    </div>
                    
                    <div onclick="CopyIvr_commands('<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <img src="<%=request.getContextPath()%>/share/img/edit.gif" valign="middle" title="Copy_Ivr" alt="Copy_Ivr"/>
                    </div>
                    
                    <div onclick="deleteIvr_commands('<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.Ivr_commandsList.sub_service_name}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.x" var="_x" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-delete.gif" valign="middle" title="${_x}" alt="delete"/>
                    </div>
                </display:column>
            </s:if>
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b>number row: ${fn:escapeXml(Ivr_commandsList_rowNum)}</b>
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
