
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        <!--<a href="#"><fmt:message key="tm.tctnmo" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" />Winner
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 ">
                <s:form id="mt_allManagerForm" name="mt_allManagerForm"  theme="simple" method="post">                    
                    <div>
                        <table width="100%">
                            <tr>  
                                <td width="10%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.stb" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="text" id="mt_allManagerForm.msisdn"  
                                           name="mt_allManagerForm.msisdn"/>
                                </td>
                                <td width="10px">&nbsp;</td> 
                                <s:if test="#request.ServicesList !=null">
                                    <td width="6%" class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong><fmt:message key="cmd.tdv" /></strong></span>
                                    </td>
                                    <td width="150px" class="table_list_itemtd">   
                                      <!--  name="mt_allManagerForm.service_name"
                                            id="mt_allManagerForm.service_name" -->
                                        <select 
                                             name="mt_allManagerForm.short_code"
                                            id="mt_allManagerForm.short_code" 
                                            cssStyle="width:150px;">
                                                <option value="-1" > <fmt:message key="cc.cc" /></option>
                                                <c:forEach items="${ServicesList}" var="data">
                                                <option value="${data.short_code}" > ${data.short_code} - ${data.service_name}</option>
                                            </c:forEach>
                                        </select>
                                    </td> 
                               
                                <td width="8%" class="table_list_itemtd" style="text-align:left">
                                    <!--<span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>-->
                                </td>
<!--                                <td width="150px" class="table_list_itemtd">  
                                    <%--<sx:div id="mt_allDiv">--%>
                                        <table width="150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="150px">
                                                    <%--<jsp:include page="mt_all.jsp"/>--%>
                                                </td>
                                            </tr>
                                        </table>
                                    <%--</sx:div>--%>                                                            
                                </td>  -->
                                 </s:if>    
                                <td width="10px">&nbsp;</td> 
                                
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.tn" /></strong></span>
                                </td>
                                <td width="200px">
                                    <s:set var="_dt" value="#date" />
                                    
                                    <sx:datetimepicker id="mt_allManagerForm.datetime"
                                                       type="date" name="mt_allManagerForm.datetime" 
                                                       displayFormat="yyyy-MM-dd"   />
                                    
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /></strong></span>
                                </td>
                                <td width="200px">
                                    
                                    <sx:datetimepicker id="mt_allManagerForm.from_datetime" value="%{'today'}"
                                                       type="date" name="mt_allManagerForm.from_datetime" 
                                                       displayFormat="yyyy-MM-dd"  />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="20%">  
                                    <fmt:message key="bt.tk" var="_submit" scope="request"/>
                                    <!--<a class="myButton" onclick="javascript:searchmt_all();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="search" executeScripts="true"
                                                  beforeNotifyTopics="mt_allManagerForm/searchmt_all"
                                                  afterNotifyTopics="mt_allManagerForm/after"
                                                  errorNotifyTopics="mt_allManagerForm/after"
                                                  targets="listmt_allManagerDiv"/>
                                    
                               
                                 <td width="10px">&nbsp;
                                    <!--<a class="myButton" href="${contextPath}/download.do?filename=111">Download Excel</a> &nbsp;-->
                                    <a class="myButton" onclick="javascript:downloadExcel('${contextPath}');">Download Excel</a> &nbsp;
                                 </td>
								<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                                <td width="10px">&nbsp;
                                    <input type="hidden" value="0" id="allChecked" />
                                        <a class="myButton" onclick="javascript:checkAllWinner();">ALL</a>
                                </td>
                                 <td width="10px">
                                    <a  onclick="approvedWinnerAll(3);">
                                       <img src="<%=request.getContextPath()%>/share/img/approved.png" valign="middle" title="approve ALL" alt="approve ALL"/>
                                    </a>
                                </td>
                                <td width="10px">
                                    <a  onclick="approvedWinnerAll(0);">
                                       <img src="<%=request.getContextPath()%>/share/img/unapproved.png" valign="unapproved" title="reject ALL" alt="reject ALL"/>
                                    </a>
                                </td>
								</s:if>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listmt_allManagerDiv">
                        <jsp:include page="mt_allManagerList.jsp"/>
                    </sx:div>                    
                </s:form>                
            </s:if>
        </div>
    </div>
</div>
