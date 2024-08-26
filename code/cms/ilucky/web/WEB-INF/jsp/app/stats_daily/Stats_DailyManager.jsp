<%-- 
    Document   : Stats_dailyManager
    Created on : Feb 14, 2014, 10:39:17 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        <!--<a href="#"><fmt:message key="tk.tkdttn" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="tk.tkdttn" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && (#attr.userToken.userType ==1 or #attr.userToken.userType ==3 or #attr.userToken.userType ==4 or #attr.userToken.userType ==5)">
                <s:form id="Stats_DailyManagerForm" name="Stats_DailyManagerForm"  theme="simple" method="post">
                    <div>
                        <table width="100%">
                            <tr>     
                                <td width="10px">&nbsp;</td>                             
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="promotion.from_date" /></strong></span>
                                </td>
                                <td width="200px">
                                    <sx:datetimepicker id="Stats_DailyManagerForm.date"  
                                                       type="date" name="Stats_DailyManagerForm.date" 
                                                        displayFormat="yyyy-MM-dd"  />
                                </td>    
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="promotion.to_date" /></strong></span>
                                </td>
                                <td width="200px">
                                    <sx:datetimepicker id="Stats_DailyManagerForm.toDate"  value="%{'today'}"
                                                       type="date" name="Stats_DailyManagerForm.toDate" 
                                                        displayFormat="yyyy-MM-dd"  />
                                </td>    
                                <td width="10px">&nbsp;</td>   
                             
                                <td width="10%">    
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchStats_DailyManager();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="Stats_DailyManagerForm/searchStats_Daily"
                                                  afterNotifyTopics="Stats_DailyManagerForm/after"
                                                  errorNotifyTopics="Stats_DailyManagerForm/after"
                                                  targets="listStats_DailyManagerDiv"/>

                                </td>
                                <td width="10px">&nbsp;
                                    <a class="myButton" onclick="javascript:downloadExcelRevuenue('${contextPath}');">Download Excel</a> &nbsp;
                                 </td>
                            </tr>
                        </table>
                    </div>  
                    <sx:div id="listStats_DailyManagerDiv">
                        <jsp:include page="Stats_DailyManagerList.jsp"/>
                    </sx:div> 
                </s:form>
            </s:if>
        </div>
    </div>
</div>
