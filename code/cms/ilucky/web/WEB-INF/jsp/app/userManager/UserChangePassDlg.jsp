<%-- 
    Document   : UserChangePassDlg
    Created on : Mar 21, 2012, 4:47:43 PM
    Author     : quyettc
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
    <s:form id="userChangePassForm"  name="userChangePassForm" theme="simple" method="post">   
        <s:token/>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">
                        <tr >
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="qltk.tdn" /></strong></span>
                            </td>
                            <td width="250px">
                                <s:if test="userChangePassForm.username !=null">
                                    <s:textfield id="userChangePassForm.username"  readonly="true"
                                                 name="userChangePassForm.username" style="width:344px;">
                                    </s:textfield>
                                </s:if>
                            </td>
                        </tr>
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="qltk.mkm" /></strong></span>
                            </td>
                            <td width="250px">
                                <input type="password" id="userChangePassForm.password"  
                                           name="userChangePassForm.password" style="width:344px;"/>
                            </td>
                            <td width="10px">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="qltk.nlmkm" /></strong></span>
                            </td>
                            <td width="250px">
                                <input type="password" id="newrepassword"  
                                           name="newrepassword" style="width:344px;"/>
                            </td>
                            <td width="10px">&nbsp;</td>
                        </tr>

                    </table>
                </td>
                <td >
                    <br/>
                </td>
            </tr>
        </table>
        <table width="100%">
            <tr>
                <td >
                    <br/>
                </td>
            </tr>
            <tr>
                <td align="center">                    
                </td>
                <td align="right">
                    <fmt:message key="cmd.cn" var="_submit" scope="request" />
                    <vb:sxbutton  value="#_submit" id="Cập nhật" executeScripts="true"
                                  beforeNotifyTopics="userChangePassForm/updateChangePassUser"
                                  afterNotifyTopics="userChangePassForm/after"
                                  errorNotifyTopics="userChangePassForm/after"
                                  targets="listUserManagerDiv" 
                                  />&nbsp;
                    <a class="myButton" onclick="closeUserChangePassDlg();"><fmt:message key="button.h" /></a>
                </td>
            </tr>
        </table>                    
    </s:form>
</s:if>
