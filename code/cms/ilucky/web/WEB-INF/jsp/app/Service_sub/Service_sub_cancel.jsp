
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

<s:if test="#attr.userToken.status !=null && #attr.userToken.status !=4 && #attr.userToken.status !=3 && #attr.userToken.userType !=4 && #attr.userToken.userType !=5 ">
    <s:form id="Service_subManagerForm"  name="Service_subManagerForm" theme="simple" method="post"> 
        <s:token/>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr style="width:100%">
                <td >
                    <table cellspacing="3" cellpadding="0">                       
                        <tr>
                            <td width="50%" class="table_list_itemtd" style="text-align:left">
                                <span style="font-size:12px;"><strong><fmt:message key="ss.stb" /></strong></span>
                            </td>
                            <td width="250px">
                                <s:label  id="Service_subManagerForm.msisdn" name="Service_subManagerForm.msisdn"></s:label>
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>                              
                            <tr>
                                <td width="50%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.gc" /></strong></span>
                                </td>
                                <td width="250px">                                    
                                <s:label id="Service_subManagerForm.sub_service_name" name="Service_subManagerForm.sub_service_name"></s:label>
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>  
                            <tr>
                                <td width="50%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.tgdk" /></strong></span>
                                </td>
                                <td width="250px">                                    
                                <s:label id="Service_subManagerForm.register_time" name="Service_subManagerForm.register_time"></s:label>
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>  
                            <tr>
                                <td width="50%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.tgh" /></strong></span>
                                </td>
                                <td width="250px">                                    
                                <s:label id="Service_subManagerForm.cancel_time" name="Service_subManagerForm.cancel_time"></s:label>
                                </td>
                                <td width="10px">&nbsp;</td>
                            </tr>  
                            <tr >
                            <s:if test="#attr.Ser_sub_status==1 || #attr.Ser_sub_status==2">
                                <td width="50%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.ndh" /></strong></span>
                                </td>
                                <td width="250px">
                                    <s:textarea cols="50" rows="5" id="Service_subManagerForm.descriptions" name="Service_subManagerForm.descriptions"></s:textarea> 
                                    </td>
                            </s:if>                            
                        </tr>  
                        <tr >                            
                            <s:if test="#attr.Ser_sub_status==0">
                                <td width="50%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.cntgdk" /></strong></span>
                                </td>
                                <td width="250px">
                                    <s:checkbox id="Service_subManagerForm.sub_service_code" name="Service_subManagerForm.sub_service_code" value="Update time active"></s:checkbox>
                                    <td width="250px">
                                </s:if>
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
                    <s:if test="#attr.Ser_sub_status==1">
                        <fmt:message key="cmd.h" var="_submit" scope="request" />
                        <vb:sxbutton  value="Unregister" id="Hủy" executeScripts="true"
                                      beforeNotifyTopics="Service_subManagerForm/Cancel_service_sub"
                                      afterNotifyTopics="Service_subManagerForm/after"
                                      errorNotifyTopics="Service_subManagerForm/after"
                                      targets="listService_subManagerDiv" />&nbsp;
                    </s:if>
                    <s:elseif test="#attr.Ser_sub_status==0 ">
                        <fmt:message key="sup.dk" var="_submit" scope="request" />
                        <vb:sxbutton  value="Register" id="Đăng ký" executeScripts="true"
                                      beforeNotifyTopics="Service_subManagerForm/Active_service_sub"
                                      afterNotifyTopics="Service_subManagerForm/after"
                                      errorNotifyTopics="Service_subManagerForm/after"
                                      targets="listService_subManagerDiv" />&nbsp;
                    </s:elseif>
                                      <a class="myButton" onclick="closeService_sub_cancel_Dlg();">Cancel</a>
                </td>
            </tr>
        </table>        
    </s:form>
</s:if>
<fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />
<s:else>
    <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
</s:else>
