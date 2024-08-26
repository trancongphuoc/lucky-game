<%--
    Document   : Ivr_commands
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
    <s:form id="Ivr_commandsManagerEditForm"  name="Ivr_commandsManagerEditForm" theme="simple" method="post">  
        <s:token/>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">                        
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>question Code</strong></span>
                            </td>
                            <td style="width:250px">
                                <s:textfield id="Ivr_commandsManagerEditForm.questionCode"
                                             name="Ivr_commandsManagerEditForm.questionCode" style="width:300px;">
                                </s:textfield>
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr>
                           
                        <s:hidden id="Ivr_commandsManagerEditForm.id" name="Ivr_commandsManagerEditForm.id"/>
                                               
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>Question</strong></span>
                            </td>
                            <td style="width:250px">
                                <s:textarea id="Ivr_commandsManagerEditForm.content" 
                                            name="Ivr_commandsManagerEditForm.content" maxLength="500"
                                            cssStyle="border: 1px solid #b5b8c8;
                                            background-color:#fff;
                                            font-family: Arial,Helvetica,sans-serif;
                                            font-size:12px;
                                            width:300px;
                                            height:60px;
                                            white-space:normal;">
                                </s:textarea><br/><fmt:message key="cmd.kq500kt" />                            
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr>     
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>answer 1</strong></span>
                            </td>
                            <td style="width:250px">
                                <s:textfield id="Ivr_commandsManagerEditForm.answer1" 
                                            name="Ivr_commandsManagerEditForm.answer1" maxLength="500"
                                            cssStyle="border: 1px solid #b5b8c8;
                                            background-color:#fff;
                                            font-family: Arial,Helvetica,sans-serif;
                                            font-size:12px;
                                            width:300px;
                                            white-space:normal;">
                                </s:textfield><br/>                                  
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>answer2</strong></span>
                            </td>
                            <td style="width:250px">
                                <s:textfield id="Ivr_commandsManagerEditForm.answer2" 
                                            name="Ivr_commandsManagerEditForm.answer2" maxLength="500"
                                            cssStyle="border: 1px solid #b5b8c8;
                                            background-color:#fff;
                                            font-family: Arial,Helvetica,sans-serif;
                                            font-size:12px;
                                            width:300px;
                                            white-space:normal;">
                                </s:textfield><br/>                                 
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr>  
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>answer3</strong></span>
                            </td>
                            <td style="width:250px">
                               <s:textfield id="Ivr_commandsManagerEditForm.answer3" 
                                            name="Ivr_commandsManagerEditForm.answer3" maxLength="500"
                                            cssStyle="border: 1px solid #b5b8c8;
                                            background-color:#fff;
                                            font-family: Arial,Helvetica,sans-serif;
                                            font-size:12px;
                                            width:300px;
                                            white-space:normal;">
                                </s:textfield><br/>  
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr> 
<!--                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>answer4</strong></span>
                            </td>
                            <td style="width:250px">
                               <s:textfield id="Ivr_commandsManagerEditForm.answer4" 
                                            name="Ivr_commandsManagerEditForm.answer4" maxLength="500"
                                            cssStyle="border: 1px solid #b5b8c8;
                                            background-color:#fff;
                                            font-family: Arial,Helvetica,sans-serif;
                                            font-size:12px;
                                            width:300px;
                                            white-space:normal;">
                                </s:textfield>
                                
                                <br/>   
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr> -->
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>Correct Answer</strong></span>
                            </td>
                            <td style="width:250px">
                               
                                 <select
                                    name="Ivr_commandsManagerEditForm.answerCorrect"
                                    id="Ivr_commandsManagerEditForm.answerCorrect" cssStyle="width:150px;">
                                    <option value="-1" > -- choice --</option>
                                    <option value="1" >1</option>
                                    <option value="2" >2</option>
                                    <option value="3" >3</option>
                                </select>
                            </td>
                            <td style="width:10px">&nbsp;</td>
                        </tr>
                        
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong>difficult</strong></span>
                            </td>
                            <td style="width:250px">
                               
                                <select
                                    name="Ivr_commandsManagerEditForm.difficult"
                                    id="Ivr_commandsManagerEditForm.difficult" cssStyle="width:150px;">
                                    <option value="-1" > -- choice --</option>
                                    <option value="1" >easy</option>
                                    <option value="2" >normal</option>
                                    <option value="3" >hard</option>
                                </select>
                            </td>
                            <td style="width:10px">&nbsp;</td>
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
                    <fmt:message key="bt.cn" var="_submit" scope="request" />
                    <vb:sxbutton  value="#_submit" id="Cập nhật" executeScripts="true"
                                  beforeNotifyTopics="Ivr_commandsManagerEditForm/updateInsertIvr_commands"
                                  afterNotifyTopics="Ivr_commandsManagerEditForm/after"
                                  errorNotifyTopics="Ivr_commandsManagerEditForm/after"
                                  targets="listIvr_commandsManagerDiv" />&nbsp;
                    <a class="myButton" onclick="closeIvr_commandsManagerDlg();"><fmt:message key="button.h" /></a>
                </td>
            </tr>
        </table>                    
    </s:form>
</s:if>
 <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />                   
    <s:else>
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>


