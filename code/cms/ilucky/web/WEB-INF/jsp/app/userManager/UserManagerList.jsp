<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updateUser ==1">
    <%--<vm:info value=" ${fn:escapeXml(Ret_updateUserMsg)}"/>--%>
    <fmt:message key="${Ret_updateUserMsg}" var="Ret_updateUserMsg_redefine"  >
        <fmt:param value="${Ret_updateUserMsg_param}" />
    </fmt:message>
<vm:info value=" ${fn:escapeXml(Ret_updateUserMsg_redefine)}"/>
</s:if>

<s:if test="#attr.userList !=null && #attr.userList.size() >0"> 
    <sx:div id="pagingUserManagerDiv"> 
        <s:token/> 

        <display:table targets="pagingUserManagerDiv" requestURI="pagingUserManager.do" id="userList" 
                       name="userList" cellpadding="1" cellspacing="1" pagesize="150" 
                       class="dataTable" style="width:100%">
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(userList_rowNum)}

            </display:column>   
            <spring:message code="animal" var="animalMsg"/>

<fmt:message key="qltk.tdn" var="_td" />
<fmt:message key="cp.mt" var="_mt" />
<fmt:message key="cmd.tth" var="_tt" />
    <display:column escapeXml="true" property="username" title="${_td}" />
    <display:column escapeXml="true" property="descriptions" title="${_mt}" />
    <display:column  title="${_tt}" >
            
            <%--<display:column escapeXml="true" property="username" title="#_td"></display:column>--%>
            <%--<display:column escapeXml="true" property="descriptions" title="Mô tả"></display:column>--%>
            <%--<display:column title="Trạng thái" >--%>
                <s:if test="#attr.userList.status==1">
                    <fmt:message key="cc.hd" var="_hd" />
                    <img src="<%=request.getContextPath()%>/share/img/tick.png"
                         valign="middle" title="${_hd}" alt="Hoạt động"/>
                </s:if>
                <s:if test="#attr.userList.status==0">
                    <fmt:message key="cc.khd" var="_khd" />
                    <img src="<%=request.getContextPath()%>/share/img/publish_x.png"
                         valign="middle" title="${_khd}" alt="Không hoạt động"/>
                </s:if>
            </display:column>
<fmt:message key="qltk.q" var="_q" />
            <display:column title="${_q}" >

                <s:if test="#attr.userList.userType==1">
                    <div>
                        Admin
                    </div>
                </s:if>
                <s:if test="#attr.userList.userType==2">
                    <div>
                        CC
                    </div>
                </s:if>
                <s:if test="#attr.userList.userType==3">
                    <div>
                        KD
                    </div>
                </s:if>
                <s:if test="#attr.userList.userType==5">
                    <div>
                        KD-CC
                    </div>
                </s:if>
                <s:if test="#attr.userList.userType==4">
                    <div>
                        CP
                    </div>
                </s:if>

            </display:column>
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:20%" title="${_tht}" headerClass="sortable" >
                    <div onclick="prepareChangePassUser('<s:property escapeJavaScript="true" value="%{#attr.userList.username}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="menu.changepass" var="_changepass" />
                        <img src="<%=request.getContextPath()%>/share/images/project/locked.gif" valign="middle" title="${_changepass}" alt="edit"/>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="prepareEditUser('<s:property escapeJavaScript="true" value="%{#attr.userList.username}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.s" var="_s" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-edit.gif" valign="middle" title="${_s}" alt="edit"/>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    <div onclick="prepareDeleteUser('<s:property escapeJavaScript="true" value="%{#attr.userList.username}"/>');"
                         class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer;">
                        <fmt:message key="sub.x" var="_x" />
                        <img src="<%=request.getContextPath()%>/share/images/project/action-delete.gif" valign="middle" title="${_x}" alt="delete"/>
                    </div>
                </display:column>
            </s:if>
            <display:footer>
                <tr>
                    <td colspan="3">
                        <b><fmt:message key="qltk.tstk" /> ${fn:escapeXml(userList_rowNum)}</b>
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

