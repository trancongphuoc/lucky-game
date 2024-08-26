<%--
    Document   : user
    Created on : Oct 21, 2009, 1:54:38 PM
    Author     : QuyetTC
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
    <s:form id="userManagerEditForm"  name="userManagerEditForm" theme="simple" method="post">  
        <s:token/>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">
                        <tr >
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="qltk.tdn" /></strong></span>
                            </td>
                            <td style="width: 250px" >
                                <s:hidden id="userManagerEditForm.oldusername" name="userManagerEditForm.oldusername"></s:hidden>                                
                                <s:if test="userManagerEditForm.username !=null">
                                    <s:textfield id="userManagerEditForm.username"  readonly="true"
                                                 name="userManagerEditForm.username" style="width:344px;">
                                    </s:textfield>
                                </s:if>
                                <s:if test="userManagerEditForm.username == null">
                                    <s:textfield id="userManagerEditForm.username"
                                                 name="userManagerEditForm.username" style="width:344px;">
                                    </s:textfield>

                                </s:if>
                            </td>
                        </tr>
                        <s:if test="userManagerEditForm.username == null">
                            <tr>
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="login.pass" /></strong></span>
                                </td>
                                <td style="width: 250px">
                                    <input type="password" id="userManagerEditForm.password"  
                                           name="userManagerEditForm.password" style="width:344px;"/>
                                </td>
                                <td style="width: 10px">&nbsp;</td>
                            </tr>
                            <tr>
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="login.nlmk" /></strong></span>
                                </td>
                                <td style="width: 250px">
                                    <input type="password" id="repassword"  name="repassword"
                                           style="width:344px;"/>
                                </td>
                                <td style="width: 10px">&nbsp;</td>
                            </tr>
                        </s:if>
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="cp.mt" /></strong></span>
                            </td>
                            <td style="width: 250px">

                                <s:textfield id="userManagerEditForm.descriptions"
                                             name="userManagerEditForm.descriptions" style="width:344px;">
                                </s:textfield>

                            </td>
                            <td style="width: 10px">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="20%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="qltk.q" /></strong></span>
                            </td>
                            <td style="width: 250px">
                                <s:select required="true" list="#{
                                          '1':'Admin',
                                              '2':'CC',
                                              '3':'KD'
                                          }"
                                          name="userManagerEditForm.user_type"
                                          id="userManagerEditForm.user_type"
                                          cssClass="txtInputFull"
                                          cssStyle="width:350px"/>
                                
                            </td>
                        </tr>   
                        <tr>
                            <td width="20%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="cmd.tth" /></strong></span>
                            </td>
                            <td style="width: 250px">
                                <select
                                    name="userManagerEditForm.status"
                                    id="userManagerEditForm.status">
                                    <option value="1"><fmt:message key="cc.hd" /></option>
                                    <option value="0"><fmt:message key="cc.khd" /></option>
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
                                  beforeNotifyTopics="userManagerEditForm/updateInsertUser"
                                  afterNotifyTopics="userManagerEditForm/after"
                                  errorNotifyTopics="userManagerEditForm/after"
                                  targets="listUserManagerDiv" />
                    <a class="myButton" onclick="closeUserManagerDlg();"><fmt:message key="button.h" /></a>
                </td>
            </tr>
        </table>                     
    </s:form>
</s:if>

