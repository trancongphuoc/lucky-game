<%-- 
    Document   : MO_MT_Dlg
    Created on : Aug 9, 2013, 8:37:59 AM
    Author     : hanhnv62
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

<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType !=4 && #attr.TransactionsManagerForm.msisdn !=null ">
    <s:form id="TransactionsManagerForm"  name="TransactionsManagerForm" theme="simple" method="post">          
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">                       
                        <tr>
                            <td width="30%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="tm.stb" /></strong></span>
                            </td>
                            <td width="250px">
                                <s:label id="TransactionsManagerForm.msisdn" name="TransactionsManagerForm.msisdn"></s:label> 
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>  
                            <tr>
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.gc" /></strong></span>
                                </td>
                                <td width="250px">                                    
                                <s:label id="TransactionsManagerForm.Sub_service_name" name="TransactionsManagerForm.Sub_service_name"></s:label>
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>   
                            <tr >
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ng.nd" /></strong></span>
                                </td>
                                <td width="250px">
                                <s:label id="TransactionsManagerForm.cmd" name="TransactionsManagerForm.cmd"></s:label> 
                                </td>
                            </tr>      
                            <tr >
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="tm.tnmo" /></strong></span>
                                </td>
                                <td width="250px">
                                <s:label id="TransactionsManagerForm.Provider_name" name="TransactionsManagerForm.Provider_name"></s:label> 
                                </td>
                            </tr>      
                            <tr>
                                <td width="30%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="tm.tnmt" /></strong></span>
                                </td>
                                <td width="250px">
                                <s:label id="TransactionsManagerForm.Item_name" name="TransactionsManagerForm.Item_name"></s:label> 
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
                        <a class="myButton" onclick="closeMO_MT_Dlg();"><fmt:message key="scp.d" /></a>
                    </td>
                </tr>
            </table>        
    </s:form>
</s:if>
 <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />                   
    <s:else>
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>

