<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<sx:head/>
<div id="toolbar-box">
    <div class="t">
        <div class="t">
            <div class="t"></div>
        </div>
    </div>
    <div class="m">
        <%--<div class="toolbar" id="toolbar">--%>
        <div   style="float: right;text-align: right; padding-left:  5px;">
            <table>
                <s:form method="post" theme="simple">
                    <s:token/>
                    <tr>
                        <td id="toolbar-new" class="button" style="padding: 1px 1px 1px 4px; text-align: center; color: #666; height: 48px;">
                            <a class="toolbar" id="newTest" style="display: block; float: left;
                               white-space: nowrap;
                               border: 1px solid #fbfbfb;
                               padding: 1px 5px;
                               cursor: pointer;"  href="#">
                                <span class="icon-32-new" style="float: none; width: 32px; height: 32px; margin: 0 auto; display: block; " title="Thêm mới"></span>
                                Thêm mới</a>
                            <sx:submit beforeNotifyTopics="serviceForm/prepareAddService"
                                   afterNotifyTopics="serviceForm/afterAddService"
                                   errorNotifyTopics="serviceForm/afterAddService"
                                   targets="serviceDlgDiv"
                                   value="Insert"
                                   id="serviceForm.prepareAddService"
                                   showLoadingText="false"
                                   parseContent="true"
                                   cssStyle="height:22px"
                                   executeScripts="true"/>
                        </td>
                    </tr>                    
                </s:form>
            </table>
        </div>
        <div class="header  icon-48-addedit" style="vertical-align: top"  >Article Manager</div>


        <div class="clr"></div>
    </div>
    <div class="b">
        <div class="b">
            <div class="b"></div>
        </div>
    </div>
</div>