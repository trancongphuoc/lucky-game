

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
        <!--<a href="#"><fmt:message key="qltk.qltkqtdv" /></a>-->
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="" alt="" /><fmt:message key="qltk.qltkqtdv" />
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1">
                <s:form id="Users_servicesManagerForm" name="Users_servicesManagerForm"  theme="simple" method="post">                    
                    <div>
                        <table width="100%">
                            <tr>   

                                <td width="10%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong><fmt:message key="qltk.tkkd" /></strong></span>
                                </td>
                                <td width="200px">
                                   <%-- <s:select required="true" onchange="searchservices();" list="#request.sUser_KdList"
                                              headerKey="-1" headerValue="--Chọn tài khoản--"
                                              listKey="username" listValue="username"
                                              name="Users_servicesManagerForm.username"
                                              id="Users_servicesManagerForm.username"
                                              cssStyle="width:306px;">
                                    </s:select>--%>
                                    <select onchange="searchservices()"
                                        name="Users_servicesManagerForm.username"
                                            id="Users_servicesManagerForm.username"
                                            cssStyle="width:306px;">
                                        <option value="-1"><fmt:message key="cc.ctk" /></option>
                                            <c:forEach items="${sUser_KdList}" var="data">
                                                <option value="${data.username}" > ${data.username}</option>
                                            </c:forEach>
                                        </select>
                                           
                                </td>  
                                <td width="10px">&nbsp;</td>   
                                <td width="16%" align="right">  
                                    <fmt:message key="bt.cn" var="_submit" scope="request" />
                                    <vb:sxbutton  value="#_submit" id="Cập nhập" executeScripts="true"
                                                  beforeNotifyTopics="Users_servicesManagerForm/updateInsertUsers_services"
                                                  afterNotifyTopics="Users_servicesManagerForm/after"
                                                  errorNotifyTopics="Users_servicesManagerForm/after"
                                                  targets="listUsers_servicesManagerDiv"/>

                                </td>                                
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listUsers_servicesManagerDiv">
                        <jsp:include page="Users_servicesManagerList.jsp" />
                    </sx:div>                    
                </s:form>                
            </s:if>
        </div>
    </div>
</div>
