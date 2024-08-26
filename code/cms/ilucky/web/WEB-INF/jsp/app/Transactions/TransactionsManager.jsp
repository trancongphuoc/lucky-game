
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

<%
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("exportPath", request.getAttribute("exportPath"));
%>
<div id="content">
    <div class="breadcrumb">
        <!--<a href="#"><fmt:message key="gd.tcgd" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="gd.tcgd" />
            </h1>
        </div>
        <div class="content">            
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1">
                <s:form id="TransactionsManagerForm" name="TransactionsManagerForm"  theme="simple" method="post">                                        
                    <table width="100%">
                        <tr> 
                            <td width="10%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="ss.stb" /></strong></span>
                            </td>
                            <td width="200px">
                                <input type="text" id="TransactionsManagerForm.msisdn" 
                                       name="TransactionsManagerForm.msisdn"/>
                            </td>    
                            <td width="10px">&nbsp;</td>
                            
                                      
                            
                            <td width="150px" class="table_list_itemtd">  
                                <sx:div id="TransactionsDiv">
                                    <table width="150px" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="150px">
                                                <jsp:include page="Transactions.jsp"/>
                                            </td>
                                        </tr>
                                    </table>
                                </sx:div>                                                            
                            </td>                              
                            <td width="10px">&nbsp;</td> 
                                 
                            <td width="5%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="ss.tn" /></strong></span>
                            </td>
                            <td width="200px">
                                <sx:datetimepicker id="TransactionsManagerForm.request_time" 
                                                   type="date" name="TransactionsManagerForm.request_time" 
                                                   displayFormat="yyyy-MM-dd" />
                            </td>    
                            <td width="10px">&nbsp;</td>
                            <td width="5%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /></strong></span>
                            </td>
                            <td width="200px">
                                <sx:datetimepicker id="TransactionsManagerForm.response_time" value="%{'today'}"
                                                   type="date" name="TransactionsManagerForm.response_time" 
                                                   displayFormat="yyyy-MM-dd" />
                            </td>    
                            <td width="10px">&nbsp;</td>
                            <td width="20%">   
                                <fmt:message key="bt.tk" var="_submit" scope="request" />
                                <!--<a class="myButton" onclick="javascript:searchTransactions();">Tìm kiếm</a>-->
                                <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                              beforeNotifyTopics="TransactionsManagerForm/searchTransactions"
                                              afterNotifyTopics="TransactionsManagerForm/after"
                                              errorNotifyTopics="TransactionsManagerForm/after"
                                              targets="listTransactionsManagerDiv"/>

                            </td>
                            <td width="10px">&nbsp;
                                    <a class="myButton" onclick="javascript:downloadTransactionLog('${contextPath}');">Download Excel</a> &nbsp;
                                 </td>
                        </tr>
                    </table>                    
                    <div id="files_list"></div>
                    <sx:div id="listTransactionsManagerDiv">
                        <jsp:include page="TransactionsManagerList.jsp"/>
                    </sx:div>   
                    <fmt:message key="tm.tnmvm" var="_tnmvm" />
                    <vd:openDlg id="MO_MT_Dlg"  title="${_tnmvm}" width="800px" />
                    <sx:div id="MO_MT_DlgDiv">
                        <jsp:include page="MO_MT_Dlg.jsp" />
                    </sx:div>
                    <vd:closeDlg/>                      
                </s:form>                
            </s:if>
        </div>
    </div>
</div>
