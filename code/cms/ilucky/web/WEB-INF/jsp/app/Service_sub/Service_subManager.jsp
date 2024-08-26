
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

<div id="content">
    <div class="breadcrumb">
        <!--<a href="#"><fmt:message key="ss.qlnddv" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="ss.qlnddv" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && (#attr.userToken.status ==1 or #attr.userToken.status ==2)">
                <s:form id="Service_subManagerForm" name="Service_subManagerForm"  theme="simple" method="post">                    
                    <div>
                        <table width="100%">
                            <tr>                                  
                                
                                <td style="width:10px">&nbsp;</td>
                                <td width="10%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.stb" /></strong></span>
                                </td>
                                <td style="width:200px">
                                    <input type="text" id="Service_subManagerForm.msisdn"  
                                           name="Service_subManagerForm.msisdn"/>
                                </td>
                                <td style="width:10px">&nbsp;</td>                                  
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                  <!--    <span style="font-size:12px;"><strong><fmt:message key="ss.tn" /></strong></span> -->
                                </td>
                                <td style="width:200px">
                                 <!--    <sx:datetimepicker id="Service_subManagerForm.from_time" 
                                                       type="date" name="Service_subManagerForm.from_time" 
                                                       displayFormat="yyyy-MM-dd" /> -->
                                </td>    
                                <td style="width:10px">&nbsp;</td>
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                     <!-- <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /> 
                                     </strong> </span>-->
                                </td>
                                <td style="width:200px">
                                    <!--
                                    <sx:datetimepicker id="Service_subManagerForm.to_time" value="%{'today'}"
                                                       type="date" name="Service_subManagerForm.to_time" 
                                                       displayFormat="yyyy-MM-dd" /> -->
                                </td>    
                                <td style="width:10px">&nbsp;</td>
                                <td width="20%">      
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchSub_Service_Sub_a();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="Service_subManagerForm/searchService_sub"
                                                  afterNotifyTopics="Service_subManagerForm/after"
                                                  errorNotifyTopics="Service_subManagerForm/after"
                                                  targets="listService_subManagerDiv"/>

                                </td>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listService_subManagerDiv">
                        <jsp:include page="Service_subManagerList.jsp"/>
                    </sx:div>  
                    <fmt:message key="scp.tttbcdkdv" var="_tttbcdkdv" />
                    <vd:openDlg id="Service_sub_cancel_Dlg"  title="${_tttbcdkdv}" width="500px" />
                    <sx:div id="Service_sub_cancel_DlgDiv">
                        <jsp:include page="Service_sub_cancel.jsp" />
                    </sx:div>
                    <vd:closeDlg/> 
                       
                </s:form>                
            </s:if>
        </div>
    </div>
</div>
