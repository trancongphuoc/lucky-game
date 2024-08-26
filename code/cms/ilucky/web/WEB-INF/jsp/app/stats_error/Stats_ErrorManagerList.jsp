<%-- 
    Document   : Stats_ErrorManagerList
    Created on : Feb 25, 2014, 11:15:04 AM
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

<sx:head/>  
<sx:div id="listStats_ErrorDiv">
    <div id="dashboard"> 
        <% request.setAttribute("contextPath", request.getContextPath());%>
        <%
                String xmlData = (String) request.getAttribute("chartData");
        %>
        <table align="left" id="lst"  width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
            </tr>
            <tr>
                <td width="100%">
                    <center>                        
                        <jsp:include page="FusionChartsHTMLRenderer.jsp" flush="true">
                            <jsp:param name="chartSWF" value="/cms/share/FsCharts/MSLine.swf" />
                            <jsp:param name="strXML" value="<%=xmlData%>"/>
                            <jsp:param name="strURL" value="" /> 
                            <jsp:param name="TPS" value="TPS"/>
                            <jsp:param name="chartId" value="chartId" />
                            <jsp:param name="chartWidth" value="1000" />
                            <jsp:param name="chartHeight" value="500" />
                            <jsp:param name="debugMode" value="false" />
                        </jsp:include>
                    </center>
                </td>
            </tr>
           
            <tr>
                <td>
                    <display:table id="Stats_ErrorList" name="Stats_ErrorList" targets="listStats_ErrorDiv"
                                   requestURI="getStats_Error.do"
                                   cellpadding="0" cellspacing="0" pagesize="150" class="dataTable">
                        <display:column title="STT" style="width:30px;text-align:center">
                            ${Stats_ErrorList_rowNum}
                        </display:column>

                        <display:column  style="text-align:left" title="Date/Hour">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Date_Hour}"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Date_Hour}"/>
                            </s:else>
                        </display:column>

                        <display:column  style="text-align:right" title="Req success">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Success}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Success}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>

                        <display:column  style="text-align:right" title="Request Failure">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Total Request">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Total}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Total}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 1">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure1}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure1}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 4">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure4}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure4}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>

                        <display:column  style="text-align:right" title="Req Fail 11">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <s:if test="#attr.Stats_ErrorList.Req_Failure11 == 0">
                                    <div style="font-weight: bolder;color: blue">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure11}" escapeJavaScript="true"/>
                                    </div>
                                </s:if>
                                <s:else>
                                    <div style="font-weight: bolder;color: red">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure11}" escapeJavaScript="true"/>
                                    </div>
                                </s:else>
                            </s:if>
                            <s:else>
                                <s:if test="#attr.Stats_ErrorList.Req_Failure11 == 0"> 
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure11}" escapeJavaScript="true"/>
                                </s:if>
                                <s:else>
                                    <div style="font-weight: bolder;color: red">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure11}" escapeJavaScript="true"/>
                                    </div>
                                </s:else>
                            </s:else>
                        </display:column>

                        <display:column  style="text-align:right" title="Req Fail 25">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <s:if test="#attr.Stats_ErrorList.Req_Failure25 == 0">
                                    <div style="font-weight: bolder;color: blue">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure25}" escapeJavaScript="true"/>
                                    </div>
                                </s:if>
                                <s:else>
                                    <div style="font-weight: bolder;color: red">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure25}" escapeJavaScript="true"/>
                                    </div>
                                </s:else>
                            </s:if>
                            <s:else>
                                <s:if test="#attr.Stats_ErrorList.Req_Failure25 == 0">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure25}" escapeJavaScript="true"/>
                                </s:if>
                                <s:else>
                                    <div style="font-weight: bolder;color: red">
                                        <s:property value="%{#attr.Stats_ErrorList.Req_Failure25}" escapeJavaScript="true"/>
                                    </div>
                                </s:else>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 100">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure100}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure100}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 102">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure102}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure102}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 406">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure406}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure406}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 407">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure407}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure407}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 408">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure408}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure408}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>                        
                        <display:column  style="text-align:right" title="Req Fail 411">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure411}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure411}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 412">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure412}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure412}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>


                        <display:column  style="text-align:right" title="Req Fail 413">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure413}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure413}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 414">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure414}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure414}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>
                        <display:column  style="text-align:right" title="Req Fail 503">
                            <s:if test="#attr.Stats_ErrorList.Date_Hour == 'Total'">
                                <div style="font-weight: bolder;color: blue">
                                    <s:property value="%{#attr.Stats_ErrorList.Req_Failure503}" escapeJavaScript="true"/>
                                </div>
                            </s:if>
                            <s:else>
                                <s:property value="%{#attr.Stats_ErrorList.Req_Failure503}" escapeJavaScript="true"/>
                            </s:else>
                        </display:column>                        
                    </display:table>
                </td>
            </tr>

            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="0: Giao dịch thanh công"/> 
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="1: Không nhận diện được thuê bao"/> 
            </div>
            </tr>           
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="4: ip không nằm trong dải ip pool"/> 
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="11: Thiếu tham số thanh toán "/> 
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="25: CP request ID không hợp lệ"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="100: Giao dịch hợp lệ"/>
            </div>
            </tr>

            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="102: Lỗi khi đăng ký dịch vụ"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="406: Lỗi khi lấy số điện thoại"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="407: CP không được phép thực hiện giao dịch"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="408: Thuê bao không sử dụng dịch vụ"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="411: Thuê bao đã hủy dịch vụ"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="412: Thuê bao không sử dụng dịch vụ"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="413: Lấy giá tiền thanh toán bị lỗi"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="414: Hủy dịch vụ trong chu kỳ trừ cước"/>
            </div>
            </tr>
            <tr>
            <div style="font-weight: bolder;color: darkblue">
                <vf:label value="503: Lỗi xử lý"/>
            </div>
            </tr>
        </table>
        <vf:closeLst/>                
    </div>
</sx:div>



