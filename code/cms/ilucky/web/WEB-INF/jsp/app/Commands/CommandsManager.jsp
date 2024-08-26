
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
        <!--<a href="#"><fmt:message key="cmd.qlcpml" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="cmd.qlcpml" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <s:form id="CommandsManagerForm" name="CommandsManagerForm"  theme="simple" method="post">                    
                    <div>
                        <table width="100%">
                            <tr>    
                                <s:if test="#request.ServicesList !=null">
                                    <td width="10%" class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong><fmt:message key="cmd.tdv" /></strong></span>
                                    </td>
                                    <td style="width:150px" class="table_list_itemtd">                                  
                                       
                                        <select onchange="searchSub_Services_command()"
                                                name="CommandsManagerForm.service_name"
                                                id="CommandsManagerForm.service_name" Style="width:150px;">
                                            <option value="-1"><fmt:message key="cc.cc" /></option>
                                            <c:forEach items="${ServicesList}" var="data">
                                                <option value="${data.service_name}" > ${data.service_name}</option>
                                            </c:forEach>
                                        </select>
                                    </td> 
                                </s:if>    
                                <td width="12%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tgc" /></strong></span>
                                </td>
                                <td style="width:150px" class="table_list_itemtd">  
                                    <sx:div id="CommandsDiv">
                                        <table style="width:150px" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td style="width:150px">
                                                    <jsp:include page="Commands.jsp"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </sx:div>                                                            
                                </td>  
                                <td width="6%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tth" /></strong></span>
                                </td>
                                <td style="width:150px" class="table_list_itemtd">
                                    <select
                                        name="CommandsManagerForm.status"
                                        id="CommandsManagerForm.status">
                                        <option value="-1"><fmt:message key="cc.tc" /></option>
                                        <option value="1"><fmt:message key="cc.hd" /></option>
                                        <option value="0"><fmt:message key="cc.khd" /></option>
                                    </select>
                                </td>       
                                <td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.ml" /></strong></span>
                                </td>
                                <td style="width:200px">
                                    <input type="text" id="CommandsManagerForm.cmd"  
                                           name="CommandsManagerForm.cmd"/>
                                </td>                                
                                <td style="width:10px">&nbsp;</td>
                                <td width="20%">   
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchCommands();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="CommandsManagerForm/searchCommands"
                                                  afterNotifyTopics="CommandsManagerForm/after"
                                                  errorNotifyTopics="CommandsManagerForm/after"
                                                  targets="listCommandsManagerDiv"/>
                                    &nbsp;
                                    <a class="myButton" onclick="javascript:prepareAddCommands();"><fmt:message key="cmd.tm" /></a>
                                </td>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listCommandsManagerDiv">
                        <jsp:include page="CommandsManagerList.jsp"/>
                    </sx:div>                    
                </s:form>
                    <fmt:message key="cmd.ttmlww" var="_title" scope="request" />
                <vd:openDlg id="CommandsEditDlg"  title="#_title" width="500px" />
                <sx:div id="CommandsEditDlgDiv">
                    <jsp:include page="CommandsManagerDlg.jsp" />
                </sx:div>
                <vd:closeDlg/>                
            </s:if>
        </div>
    </div>
</div>
