<%-- 
    Document   : SearchCpKpiSoap
    Created on : Jan 3, 2019, 4:35:16 PM
    Author     : TRIEUNGUYEN
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
  
<div id="content">
    <div class="breadcrumb">
        <!--<a href="#"><fmt:message key="s.scks" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="s.scks" />
            </h1>
        </div>
        <div class="content">            
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1">
                <s:form id="SearchCpKpiSoapForm" name="SearchCpKpiSoapForm"  theme="simple" method="post">  
                    <div>
                        <table width="100%">
                            <tr> 
                                <td width="10%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="sl.MSISDN" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="text" id="SearchCpKpiSoapForm.msisdn" maxlength="20"
                                           name="SearchCpKpiSoapForm.msisdn"/>
                                </td>    
                                <td width="10px">&nbsp;</td>

                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.tn" /></strong></span>
                                </td>
                                <td width="200px">
                                    
                                    <sx:datetimepicker id="SearchCpKpiSoapForm.fromDate" 
                                                       type="date" name="SearchCpKpiSoapForm.fromDate" 
                                                       displayFormat="yyyy-MM-dd" />
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="ss.dn" /></strong></span>
                                </td>
                                <td width="200px">
                                
                                        <sx:datetimepicker id="SearchCpKpiSoapForm.toDate" value="%{'today'}"
                                                           type="date" name="SearchCpKpiSoapForm.toDate" 
                                                           displayFormat="yyyy-MM-dd" />
                                
                                </td>    
                                <td width="10px">&nbsp;</td>
                                <td width="20%">   
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />       
                                    <vb:sxbutton  value="#_submit" id="tim_kiem" executeScripts="true"
                                                  beforeNotifyTopics="SearchCpKpiSoapForm/searchCpKpiSoap"
                                                  afterNotifyTopics="SearchCpKpiSoapForm/after"
                                                  errorNotifyTopics="SearchCpKpiSoapForm/after"
                                                  targets="listSearchCpKpiSoapDiv" />
                                    &nbsp; 
                                </td>
                            </tr>
                        </table>  
                    </div>

                    <div id="files_list"></div>

                    <sx:div id="listSearchCpKpiSoapDiv" >
                        <jsp:include page="SearchCpKpiSoapList.jsp"/>
                    </sx:div>                     
                </s:form>                
            </s:if>
        </div>
    </div>
</div>



