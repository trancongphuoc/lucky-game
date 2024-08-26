<%-- 
    Document   : Stats_SubscribersManager
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
        <!--<a href="#"><fmt:message key="menu.tk.tktb" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="menu.tk.tktb" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && (#attr.userToken.userType ==1 or #attr.userToken.userType ==3 or #attr.userToken.userType ==4 or #attr.userToken.userType ==5)">
                <s:form id="Stats_SubscribersManagerForm" name="Stats_SubscribersManagerForm"  theme="simple" method="post">
                    <div>
                        <table width="100%">
                            <tr>  
                                <s:if test="#request.Cp_subList !=null">
                                    <td width="6%"class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong><fmt:message key="cmd.tcp" /></strong></span>
                                    </td>
                                    <td width="150px" class="table_list_itemtd">                                    
                                        <%--<s:select  required="true" onchange="searchServices_Stats_Sub();"  list="#request.Cp_subList"
                                                   listKey="provider_name" listValue="provider_name"
                                                   headerKey="-1" headerValue="--Chọn CP--"
                                                   name="Stats_SubscribersManagerForm.provider_name"
                                                   id="Stats_SubscribersManagerForm.provider_name" cssStyle="width:150px;">
                                        </s:select>  --%>
                                        <select    onchange="searchServices_Stats_Sub()"  list="#request.Cp_subList"
                                                   name="Stats_SubscribersManagerForm.provider_name"
                                                   id="Stats_SubscribersManagerForm.provider_name" cssStyle="width:150px;">
                                            <option value="-1" > <fmt:message key="qltk.ccp" /></option>
                                            <c:forEach items="${Cp_subList}" var="data">
                                                <option value="${data.provider_name}" > ${data.provider_name}</option>
                                             </c:forEach>  
                                        </select>
                                    </td> 
                                </s:if>           
                                <td width="10px">&nbsp;</td> 
                                <td width="6%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tdv" /></strong></span>
                                </td>
                                <td width="150px" class="table_list_itemtd">  
                                    <sx:div id="Stats_Subscribers_ServiceDiv">                                    
                                        <table width="150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="150px">
                                                    <jsp:include page="Stats_Subscribers_Service.jsp"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </sx:div>  
                                </td>
                                <td width="10px">&nbsp;</td> 
                                <td width="6%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>
                                </td>
                                <td width="150px" class="table_list_itemtd">  
                                    <sx:div id="Stats_Subscribers_Sub_ServiceDiv">
                                        <table width="150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="150px">
                                                    <jsp:include page="Stats_Subscribers_Sub_Service.jsp"/>
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
                                    <sx:datetimepicker id="Stats_SubscribersManagerForm.from_date" 
                                                       type="date" name="Stats_SubscribersManagerForm.from_date" 
                                                       displayFormat="yyyy-MM-dd" />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /></strong></span>
                                </td>
                                <td width="200px">
                                    <sx:datetimepicker id="Stats_SubscribersManagerForm.to_date" 
                                                       type="date" name="Stats_SubscribersManagerForm.to_date"  value="%{'today'}"
                                                       displayFormat="yyyy-MM-dd" />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="20%">   
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchStats_SubscribersManager();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="Stats_SubscribersManagerForm/searchStats_Subscribers"
                                                  afterNotifyTopics="Stats_SubscribersManagerForm/after"
                                                  errorNotifyTopics="Stats_SubscribersManagerForm/after"
                                                  targets="listStats_SubscribersManagerDiv"/>

                                </td>
                            </tr>
                        </table>
                    </div>  
                    <sx:div id="listStats_SubscribersManagerDiv">
                        <jsp:include page="Stats_SubscribersManagerList.jsp"/>
                    </sx:div> 
                </s:form>
            </s:if>
        </div>
    </div>
</div>
