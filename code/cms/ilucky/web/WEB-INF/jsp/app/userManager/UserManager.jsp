
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
        <!--<a href="#"><fmt:message key="menu.qlnd" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="menu.qlnd" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <s:form id="userManagerForm" name="userManagerForm"  theme="simple" method="post">
                    
                    <div>
                        <table width="100%">
                            <tr>
                                <td width="7%"class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cmd.tth" /></strong></span>
                                </td>
                                <td width="150px" class="table_list_itemtd">
                                    <select
                                        name="userManagerForm.status"
                                        id="userManagerForm.status">
                                        <option value="-1"><fmt:message key="cc.tc" /></option>
                                        <option value="1"><fmt:message key="cc.hd" /></option>
                                        <option value="0"><fmt:message key="cc.khd" /></option>
                                    </select>
                                </td>                                
                                <td width="10%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="qltk.tdn" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="text" id="userManagerForm.userName"  
                                           name="userManagerForm.userName"/>
                                </td>
                                <td width="10px">&nbsp;</td>
                                <td width="20%">    
                                    <fmt:message key="bt.tk" var="_submit" scope="request" />
                                    <!--<a class="myButton" onclick="javascript:searchUser();">Tìm kiếm</a>-->
                                    <vb:sxbutton  value="#_submit" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="userManagerForm/searchUser"
                                                  afterNotifyTopics="userManagerForm/after"
                                                  errorNotifyTopics="userManagerForm/after"
                                                  targets="listUserManagerDiv"/>
                                    &nbsp;
                                    <a class="myButton" onclick="javascript:prepareAddUser();"><fmt:message key="cp.tm" /></a>
                                </td>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listUserManagerDiv">
                        <jsp:include page="UserManagerList.jsp"/>
                    </sx:div>                    
                </s:form>
                    <fmt:message key="qltk.ttnd" var="_title" scope="request"/>
                    <!--${_title}-->
                <vd:openDlg id="userEditDlg"  title="#_title" width="500px" />
                <sx:div id="userEditDlgDiv">
                    <jsp:include page="UserManagerDlg.jsp" />
                </sx:div>
                <vd:closeDlg/>
                <fmt:message key="qltk.dmkcnd" var="_title" scope="request"/>
                <vd:openDlg id="userChangePassDlg"  title="${_title}" width="500px" />
                <sx:div id="userChangePassDlgDiv">
                    <jsp:include page="UserChangePassDlg.jsp" />
                </sx:div>
                <vd:closeDlg/>
            </s:if>
        </div>
    </div>
</div>
