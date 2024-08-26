
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
        <a href="#"><fmt:message key="menu.changepass" /></a>
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="cp.tt" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1">
                <s:form id="changePassForm" name="changePassForm" theme="simple" method="post">
                    <s:token/>
                    <sx:div id="changePassMsgDiv">
                         <label id="warning_id" style="color:red; width: 300px; margin-top: 300px;" >
                             <b>
                        <%--<jsp:include page="ChangePassMsg.jsp"/>--%>
                        <a><fmt:message key="login.first" /></a>
                        </label>
                    </sx:div>
                    <div>
                        <table width="100%" cellpadding="3" cellspacing="3">
                            <tr>
                                <td width="20%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="cg.nd" /></strong></span>
                                </td>
                                <td width="200px">
                                    <label id="warning_id" style="color:red; width: 250px; margin-top: 300px;" >
                                        <b>${fn:escapeXml(attr.userToken.username)}</b>
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td width="20%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="login.mkht" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="password" id="changePassForm.oldPass"  name="changePassForm.oldPass"
                                           style="border: 1px solid #b5b8c8; 
                                           background-color:#fff;
                                           font-family: Arial,Helvetica,sans-serif;
                                           font-size:12px;
                                           width:90%;
                                           height:16px;
                                           white-space:normal;" />

                                </td>
                            </tr>
                            <tr>
                                <td width="20%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="qltk.mkm" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="password" id="changePassForm.newPass"  name="changePassForm.newPass"
                                           style="border: 1px solid #b5b8c8;
                                           background-color:#fff;
                                           font-family: Arial,Helvetica,sans-serif;
                                           font-size:12px;
                                           width:90%;
                                           height:16px;
                                           white-space:normal;" />

                                </td>
                            </tr>
                            <tr>
                                <td width="20%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="qltk.nlmkm" /></strong></span>
                                </td>
                                <td width="200px">
                                    <input type="password" id="changePassForm.newPass2"  name="changePassForm.newPass2"
                                           style="border: 1px solid #b5b8c8;
                                           background-color:#fff;
                                           font-family: Arial,Helvetica,sans-serif;
                                           font-size:12px;
                                           width:90%;
                                           height:16px;
                                           white-space:normal;" />

                                </td>
                                <td>
                                    <fmt:message key="cmd.cn" var="_submit" scope="request" />
                                    <vb:sxbutton  value="#_submit" id="Cập nhật" executeScripts="true"
                                                  beforeNotifyTopics="changePassForm/changePassword"
                                                  afterNotifyTopics="changePassForm/after"
                                                  errorNotifyTopics="changePassForm/after"
                                                  targets="changePassMsgDiv"
                                                  />
                                </td>
                        </table>
                    </div>                                  
                </s:form>
            </s:if>
        </div>
    </div>
</div>