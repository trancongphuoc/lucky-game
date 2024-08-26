<%-- 
    Document   : Stats_RevenueManager
    Created on : Sep 9, 2013, 2:10:38 PM
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

<div id="content">
    <div class="breadcrumb">
        <!--<a href="#"><fmt:message key="menu.tk.tkdt" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="menu.tk.tkdt" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && (#attr.userToken.userType ==1 or #attr.userToken.userType ==3 or #attr.userToken.userType ==4 or #attr.userToken.userType ==5)">
                <s:form id="Stats_RevenueManagerForm" name="Stats_RevenueManagerForm"  theme="simple" method="post">
                    <div>
                        <table width="100%">
                            <tr>             
                                <s:if test="#request.ServicesList !=null">
                                    <td width="6%"class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong><fmt:message key="cmd.tdv" /></strong></span>
                                    </td>
                                    <td width="150px" class="table_list_itemtd">                                     
                                        <select onchange="searchSub_Service_Stats_Re()"
                                            name="Stats_RevenueManagerForm.service_name"
                                            id="Stats_RevenueManagerForm.service_name" cssStyle="width:150px;">
                                            <option value="-1" > <fmt:message key="cc.cc" /></option>
                                            <c:forEach items="${ServicesList}" var="data">
                                                <option value="${data.service_name}" > ${data.service_name}</option>
                                            </c:forEach>
                                        </select>
                                    </td> 
                                </s:if>           
                                <td width="10px">&nbsp;</td>                             
                                <td width="8%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>
                                </td>
                                <td width="150px" class="table_list_itemtd">  
                                    <sx:div id="Stats_Revenue_Sub_Service_Div">
                                        <table width="150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="150px">
                                                    <jsp:include page="Stats_Revenue_Sub_Service.jsp"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </sx:div>                                                            
                                </td>  
                                <td width="10px">&nbsp;</td> 
                                <td width="8%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.ml" /></strong></span>
                                </td>
                                <td width="150px" class="table_list_itemtd">  
                                    <sx:div id="Stats_Revenue_CMD_Div">
                                        <table width="150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="150px">
                                                    <jsp:include page="Stats_Revenue_CMD.jsp"/>
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
                                    <sx:datetimepicker id="Stats_RevenueManagerForm.from_date" 
                                                       type="date" name="Stats_RevenueManagerForm.from_date" 
                                                       displayFormat="yyyy-MM-dd" />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /></strong></span>
                                </td>
                                <td width="200px">
                                    <sx:datetimepicker id="Stats_RevenueManagerForm.to_date" 
                                                       type="date" name="Stats_RevenueManagerForm.to_date" value="%{'today'}"
                                                       displayFormat="yyyy-MM-dd" />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="20%">    
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchStats_RevenueManager();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="Stats_RevenueManagerForm/searchStats_Revenue"
                                                  afterNotifyTopics="Stats_RevenueManagerForm/after"
                                                  errorNotifyTopics="Stats_RevenueManagerForm/after"
                                                  targets="listStats_RevenueManagerDiv"/>

                                </td>
                            </tr>
                        </table>
                    </div>  
                    <sx:div id="listStats_RevenueManagerDiv">
                        <jsp:include page="Stats_RevenueManagerList.jsp"/>
                    </sx:div> 
                </s:form>
            </s:if>
        </div>
    </div>
</div>
