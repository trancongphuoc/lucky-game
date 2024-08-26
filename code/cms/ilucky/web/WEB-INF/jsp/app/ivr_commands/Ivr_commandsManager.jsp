
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <!--<a href="#"><fmt:message key="qltk.qlmo" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" />Question
            </h1>
        </div>
        <div class="content">
            <%--<s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">--%>
                <s:form id="Ivr_commandsManagerForm" name="Ivr_commandsManagerForm"  theme="simple" method="post">                    
                    <div>
                        <table width="100%">
                            <tr>
                               
                                   <td width="10%" class="table_list_itemtd" style="text-align:left">
                                        <span style="font-size:12px;"><strong>search(question code or content): </strong></span>
                                    </td>
                                    <td style="width:150px" class="table_list_itemtd">                                     
                                        
                                         <s:textfield id="Ivr_commandsManagerForm.search"
                                             name="Ivr_commandsManagerForm.search"  style="width:300px;">
                                        </s:textfield>
                                    </td>
                              
<!--                                                         -->
                                <%--<td width="5%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.mlmo" /></strong></span>
                                </td>
                                <td style="width:200px">
                                    <input type="text" id="Ivr_commandsManagerForm.cmd"  
                                           name="Ivr_commandsManagerForm.cmd"/>
                                </td>
                                <td style="width:10px">&nbsp;</td>--%>
                                <td width="20%">      
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchIvr_commands_a();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="btn_search" executeScripts="true"
                                                  beforeNotifyTopics="Ivr_commandsManagerForm_searchIvr_commands"
                                                  afterNotifyTopics="Ivr_commandsManagerForm/after"
                                                  errorNotifyTopics="Ivr_commandsManagerForm/after"
                                                  targets="listIvr_commandsManagerDiv"/>
                                    &nbsp;
                                    <a class="myButton" onclick="javascript:prepareAddIvr_commands();"><fmt:message key="cp.tm" /></a>
                                </td>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listIvr_commandsManagerDiv">
                        <jsp:include page="Ivr_commandsManagerList.jsp"/>
                    </sx:div>                    
                </s:form>
                    <fmt:message key="c.ttmocm" var="_title" scope="request" />
                <vd:openDlg id="Ivr_commandsEditDlg"  title="#_title" width="500px" />
                <sx:div id="Ivr_commandsEditDlgDiv">
                    <jsp:include page="Ivr_commandsManagerDlg.jsp" />
                </sx:div>
                <vd:closeDlg/>                
            <%--</s:if>--%>
        </div>
    </div>
</div>
