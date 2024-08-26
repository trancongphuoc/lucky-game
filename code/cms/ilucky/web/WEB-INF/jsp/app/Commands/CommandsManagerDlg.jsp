<%--
    Document   : Commands
    Created on : Oct 21, 2009, 1:54:38 PM
    Author     : QuyetTC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="vb" tagdir="/WEB-INF/tags/vcl/button/" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1 ">
    <s:form id="CommandsManagerEditForm"  name="CommandsManagerEditForm" theme="simple" method="post">  
        <s:token/>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">
                        <tr >
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="cmd.mlww" /></strong></span>
                            </td>
                            <td width="250px">
                                <s:hidden id="CommandsManagerEditForm.oldcmd" name="CommandsManagerEditForm.oldcmd"></s:hidden>                                
                                <s:if test="CommandsManagerEditForm.cmd !=null">
                                    <s:textfield id="CommandsManagerEditForm.cmd"  readonly="true"
                                                 name="CommandsManagerEditForm.cmd" style="width:300px;">
                                    </s:textfield>
                                </s:if>
                                <s:if test="CommandsManagerEditForm.cmd == null">
                                    <s:textfield id="CommandsManagerEditForm.cmd"
                                                 name="CommandsManagerEditForm.cmd" style="width:300px;">
                                    </s:textfield>
                                </s:if>
                            </td>
                        </tr> 
                        <s:if test="#request.cSub_ServiceList !=null">
                            <tr>
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>
                                </td>
                                <td width="250px">
                                    <%--<s:select required="true"  list="#request.cSub_ServiceList"
                                              headerKey="-1" headerValue="--Chọn gói cước--"
                                              listKey="sub_service_name" listValue="sub_service_name"
                                              name="CommandsManagerEditForm.sub_service_name"
                                              id="CommandsManagerEditForm.sub_service_name" cssStyle="width:306px;">
                                    </s:select>--%>
                                    <select
                                        name="CommandsManagerEditForm.sub_service_name"
                                        id="CommandsManagerEditForm.sub_service_name" cssStyle="width:306px;">
                                        <option value="-1" > <fmt:message key="sub.choose" /></option>
                                        <c:forEach items="${cSub_ServiceList}" var="data">
                                            <option value="${data.sub_service_name}" ${data.sub_service_name == CommandsManagerEditForm.sub_service_name ? 'selected' : ''}> 
                                                ${data.sub_service_name}</option>
                                        </c:forEach>    
                                    </select>
                                   
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>  
                        </s:if>
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="cmd.ct" /></strong></span>
                                <!--<span style="font-size:12px;"><strong>check1</strong></span>-->
                            </td>
                            <td width="250px">
                                <s:textfield id="CommandsManagerEditForm.details"
                                             name="CommandsManagerEditForm.details" style="width:300px;">
                                </s:textfield>
                            </td>
                            <td width="10px">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="20%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="cmd.tth" /></strong></span>
                            </td>
                            <td width="250px">
                                <%--<s:select required="true" list="#{'1':'Hoạt động',
                                                                  '0':'Không hoạt động'}"
                                          name="CommandsManagerEditForm.status"
                                          id="CommandsManagerEditForm.status"
                                          cssClass="txtInputFull"   
                                          cssStyle="width:306px"/>--%>
                                <select name="CommandsManagerEditForm.status"
                                          id="CommandsManagerEditForm.status"
                                          class="txtInputFull"   
                                          style="width:100px"/>
                                <option value="1"
                                        <c:out value="${CommandsManagerEditForm.status == 1 ? 'selected': ''}"/>
                                        ><fmt:message key="cc.hd" /></option>
                                <option value="0"
                                        <c:out value="${CommandsManagerEditForm.status == 0 ? 'selected': ''}"/>
                                        ><fmt:message key="cc.khd" /></option>
                                </select>
                            </td>
                        </tr>   
                    </table>
                </td>
                <td >
                    <br/>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td >
                    <br/>
                </td>
            </tr>
            <tr>
                <td align="center">                    
                </td>
                <td align="right">
                    <fmt:message key="bt.cn" var="_submit" scope="request" />
                    <vb:sxbutton  value="#_submit" id="Cập nhật" executeScripts="true"
                                  beforeNotifyTopics="CommandsManagerEditForm/updateInsertCommands"
                                  afterNotifyTopics="CommandsManagerEditForm/after"
                                  errorNotifyTopics="CommandsManagerEditForm/after"
                                  targets="listCommandsManagerDiv" />
                    <a class="myButton" onclick="closeCommandsManagerDlg();"><fmt:message key="button.h" /></a>
                </td>
            </tr>
        </table>                    
    </s:form>
</s:if>
<fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />
<s:else>
    <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
</s:else>


