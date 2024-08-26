
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
<%--<sx:head  extraLocales="en-us,nl-nl,de-de"/>--%>

<div id="content">
    <div class="breadcrumb">
        <a href="#">Báo cáo Voice BroadCast</a>
    </div>
    <div class="box">
        <div class="heading">
            <h1>
                <img src="view/image/product.png" alt="" />Information
            </h1>
        </div>
        <div class="content">
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1">
                <s:form id="reportVoiceBroadCastForm" name="reportVoiceBroadCastForm"  theme="simple" method="post">
                    <div>
                        <table width="100%">
                            <tr>
                                <td width="7%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong>Campaign:</strong></span>
                                </td>
                                <td width="200px">
                                    <s:select list="#attr.lstCampaign" required="true"
                                              listKey="campaign_name" listValue="campaign_name"
                                              headerKey="0" headerValue="Tất cả"
                                              name="reportVoiceBroadCastForm.campaign_name"
                                              id="reportVoiceBroadCastForm.campaign_name"
                                              />
                                </td>
                                <td width="10px">&nbsp;</td>
                                <td width="7%" class="table_list_itemtd" style="text-align:left">
                                    <span style="font-size:12px;"><strong>Thời gian:</strong></span>
                                </td>
                                <td width="450px" class="table_list_itemtd">
                                    <span style="font-size:12px;"><strong>- Từ ngày:</strong></span>
                                    &nbsp;&nbsp;&nbsp;
                                    <s:textfield id="reportVoiceBroadCastForm.datetime_from" maxlength="10"
                                                 name="reportVoiceBroadCastForm.datetime_from" style="width:100px;">
                                    </s:textfield>
                                    <%--<s:textfield name="reportVoiceBroadCastForm.start_time" id="reportVoiceBroadCastForm.start_time"
                                                 cssStyle="border: 1px solid #b5b8c8;
                                                 font-family: Arial,Helvetica,sans-serif;
                                                 width:85px;height:17px;font-weight: bold;color:#FF4848;font-size:15px;
                                                 text-align:left;" maxlength="10" />--%>
                                    <a href="javascript:NewCssCal('reportVoiceBroadCastForm.datetime_from','ddmmyyyy')">
                                        <img src="<%=request.getContextPath()%>/share/images/iconlich.gif" width="22" height="22" alt="Pick a date"/>
                                    </a>
                                    &nbsp;&nbsp;&nbsp;
                                    <span style="font-size:12px;"><strong>- Đến ngày:</strong></span>
                                    &nbsp;&nbsp;&nbsp;
                                    <s:textfield id="reportVoiceBroadCastForm.datetime_to" maxlength="10"
                                                 name="reportVoiceBroadCastForm.datetime_to" style="width:100px;">
                                    </s:textfield>
                                    <%--<s:textfield name="reportVoiceBroadCastForm.end_time" id="reportVoiceBroadCastForm.end_time"
                                                 cssStyle="border: 1px solid #b5b8c8;
                                                 font-family: Arial,Helvetica,sans-serif;
                                                 width:85px;height:17px;font-weight: bold;color:#FF4848;font-size:15px;
                                                 text-align:left;" maxlength="10" />--%>
                                    <a href="javascript:NewCssCal('reportVoiceBroadCastForm.datetime_to','ddmmyyyy')">
                                        <img src="<%=request.getContextPath()%>/share/images/iconlich.gif" width="22" height="22" alt="Pick a date"/>
                                    </a>
                                </td>
                                <td width="10px">&nbsp;</td>
                                <td width="20%">
                                    <!--</div>-->
                                    <vb:sxbutton  value="Tìm kiếm" id="Tìm kiếm" executeScripts="true"
                                                  beforeNotifyTopics="reportVoiceBroadCastForm/searchReportVoiceBroadCast"
                                                  afterNotifyTopics="reportVoiceBroadCastForm/after"
                                                  errorNotifyTopics="reportVoiceBroadCastForm/after"
                                                  targets="listReportVoiceBroadCastDiv"/>
                                </td>
                            </tr>
                        </table>
                    </div>   
                    <div id="files_list"></div>
                    <sx:div id="listReportVoiceBroadCastDiv">
                        <jsp:include page="ReportVoiceBroadCastList.jsp"/>
                    </sx:div>
                </s:form>
            </s:if>
        </div>
    </div>
</div>