<%-- 
    Document   : Stats_ErrorManager
    Created on : Feb 25, 2014, 11:08:40 AM
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
<%@taglib  uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@include file="../../common/language.jsp" %>

<div id="content">
    <div class="breadcrumb">
        <!--<a href="#"><fmt:message key="tk.tkmlgd" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="tk.tkmlgd" />
            </h1>
        </div>
        <div class="content">
            <%--<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && (#attr.userToken.userType ==1 or #attr.userToken.userType ==3 or #attr.userToken.userType ==4 or #attr.userToken.userType ==5)">
                <s:form id="Stats_ErrorManagerForm" name="Stats_ErrorManagerForm"  theme="simple" method="post">
                    <div>
                        <table width="100%">
                            <tr>     
                                <td width="6%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tcp" /></strong></span>
                                </td>
                                <td style="width:150px" class="table_list_itemtd">                                    
                                   <%-- <s:select  required="true" onchange="searchServices_Error_Sub();"  list="#request.Cp_subList"
                                               listKey="provider_name" listValue="provider_name"
                                               headerKey="-1" headerValue="--Chọn CP--"
                                               name="Stats_ErrorManagerForm.provider_name"
                                               id="Stats_ErrorManagerForm.provider_name" cssStyle="width:150px;">
                                    </s:select> --%>
                                    <%--<select  onchange="searchServices_Error_Sub()"
                                               name="Stats_ErrorManagerForm.provider_name"
                                               id="Stats_ErrorManagerForm.provider_name" Style="width:150px;">
                                                    <option value="-1" > <fmt:message key="qltk.ccp" /></option>
                                                 <c:forEach items="${Cp_subList}" var="data">
                                                    <option value="${data.provider_name}" > ${data.provider_name}</option>
                                                </c:forEach>
                                    </select>        
                                </td>                                          
                                <td style="width:10px">&nbsp;</td>
                                <s:if test="#request.ServicesList !=null">
                                    <td width="6%" class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong><fmt:message key="cmd.tdv" /></strong></span>
                                    </td>
                                    <td style="width:150px" class="table_list_itemtd">  
                                        <sx:div id="Stats_Error_ServiceDiv">                                    
                                            <table style="width:150px" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td style="width:150px">
                                                        <jsp:include page="Stats_Error_Service.jsp"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </sx:div>  
                                    </td>
                                </s:if>           
                                <td style="width: 10px">&nbsp;</td>                             
                                <td width="8%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>
                                </td>
                                <td style="width:150px" class="table_list_itemtd">  
                                    <sx:div id="Stats_Error_Sub_Service_Div">
                                        <table style="width: 150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td style="width:150px">
                                                    <jsp:include page="Stats_Error_Sub_Service.jsp"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </sx:div>                                                            
                                </td> 
                                <td style="width:10px">&nbsp;</td> 
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="tk.cng" /></strong></span>
                                </td>
                                <td style="width:200px">
                                    <sx:datetimepicker id="Stats_ErrorManagerForm.date"  value="%{'today'}"
                                                       type="date" name="Stats_ErrorManagerForm.date" 
                                                       displayFormat="dd/MM/yyyy" />
                                </td>    
                                <td style="width:10px">&nbsp;</td>   

                                <!--<td width="10px"><s:label value="Hoặc tháng năm: "/></td>-->
                                <td style="width:10px"><fmt:message key="tk.htn" /></td>
                                <td style="width:130px">
                                    <select
                                        name="Stats_ErrorManagerForm.month"
                                        id="Stats_ErrorManagerForm.month">
                                        <option value="-1"><fmt:message key="tk.ct" /></option>
                                        <option value="1"><fmt:message key="tk.t1" /></option>
                                        <option value="2"><fmt:message key="tk.t2" /></option>
                                        <option value="3"><fmt:message key="tk.t3" /></option>
                                        <option value="4"><fmt:message key="tk.t4" /></option>
                                        <option value="5"><fmt:message key="tk.t5" /></option>
                                        <option value="6"><fmt:message key="tk.t6" /></option>
                                        <option value="7"><fmt:message key="tk.t7" /></option>
                                        <option value="8"><fmt:message key="tk.t8" /></option>
                                        <option value="9"><fmt:message key="tk.t9" /></option>
                                        <option value="10"><fmt:message key="tk.t10" /></option>
                                        <option value="11"><fmt:message key="tk.t11" /></option>
                                        <option value="12"><fmt:message key="tk.t12" /></option>
                                    </select>
                              
                                    <select
                                        name="Stats_ErrorManagerForm.years"
                                        id="Stats_ErrorManagerForm.years">
                                        <option value="-1"><fmt:message key="tk.cn" /></option>
                                        <option value="1"><fmt:message key="tk.cn1" /></option>
                                        <option value="2"><fmt:message key="tk.cn2" /></option>
                                        <option value="3"><fmt:message key="tk.cn3" /></option>
                                        <option value="4"><fmt:message key="tk.cn4" /></option>
                                        <option value="5"><fmt:message key="tk.cn5" /></option>
                                        <option value="6"><fmt:message key="tk.cn6" /></option>
                                        <option value="7"><fmt:message key="tk.cn7" /></option>
                                        <option value="8"><fmt:message key="tk.cn8" /></option>
     
     
                                    </select>
                                </td>
                                <td width="10%">   
                                    <fmt:message key="stats.Statistic" var="_submit" scope="request" />
                                    <!--<input type="submit" value="Thống kê" id="searchBtn" tabindex="-1" onclick="searchStatistics();  return false;" />-->
                                    <!--<a class="myButton" onclick="javascript:searchStats_ErrorManager();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Thống kê" executeScripts="true"
                                                  beforeNotifyTopics="Stats_ErrorManagerForm/searchStats_Error"
                                                  afterNotifyTopics="Stats_ErrorManagerForm/after"
                                                  errorNotifyTopics="Stats_ErrorManagerForm/after"
                                                  targets="listStats_ErrorManagerDiv"/>

                                </td>
                            </tr>
                        </table>
                    </div>  
                    <sx:div id="listStats_ErrorManagerDiv">
                        <s:if test="#attr.Stats_ErrorList !=null && #attr.Stats_ErrorList.size() >0">
                            <jsp:include page="Stats_ErrorManagerList.jsp"/>
                        </s:if>
                    </sx:div> 
                </s:form>
            </s:if>--%>
            <table>                        
             <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="0: Giao dịch thanh công"/>--%> 
                <fmt:message key="List.error0" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="1: Không nhận diện được thuê bao"/>--%> 
                <fmt:message key="List.error1" />
            </div>
            </tr>           
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="4: ip không nằm trong dải ip pool"/>--%> 
                <fmt:message key="List.error2" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="11: Thiếu tham số thanh toán "/>--%> 
                <fmt:message key="List.error3" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="25: CP request ID không hợp lệ"/>--%>
                <fmt:message key="List.error4" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="100: Giao dịch hợp lệ"/>--%>
                <fmt:message key="List.error5" />
            </div>
            </tr>

            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="102: Lỗi khi đăng ký dịch vụ"/>--%>
                <fmt:message key="List.error6" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="406: Lỗi khi lấy số điện thoại"/>--%>
                <fmt:message key="List.error7" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="407: CP không được phép thực hiện giao dịch"/>--%>
                <fmt:message key="List.error8" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="408: Thuê bao không sử dụng dịch vụ"/>--%>
                <fmt:message key="List.error9" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="411: Thuê bao đã hủy dịch vụ"/>--%>
                <fmt:message key="List.error10" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="412: Thuê bao không sử dụng dịch vụ"/>--%>
                <fmt:message key="List.error11" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="413: Lấy giá tiền thanh toán bị lỗi"/>--%>
                <fmt:message key="List.error12" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="414: Hủy dịch vụ trong chu kỳ trừ cước"/>--%>
                <fmt:message key="List.error13" />
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <%--<vf:label value="503: Lỗi xử lý"/>--%>
                <fmt:message key="List.error14" />
            </div>
            </tr>
        </table>                       
                                    
        </div>           
    </div>
</div>
