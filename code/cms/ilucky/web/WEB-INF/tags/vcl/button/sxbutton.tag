<%@tag display-name="VCL-Button: SX-style button" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@attribute name="targets"%>
<%@attribute name="formId"%>

<%@attribute name="loadingText"%>
<%@attribute name="showLoadingText"%>

<%@attribute name="validate"%>
<%@attribute name="ajaxAfterValidation"%>
<%@attribute name="executeScripts"%>
<%@attribute name="parseContent"%>

<%@attribute name="errorNotifyTopics"%>
<%@attribute name="beforeNotifyTopics"%>
<%@attribute name="afterNotifyTopics"%>

<%@attribute name="type" %>
<%@attribute name="id" %>
<%@attribute name="value" %>
<%@attribute name="disabled" %>

<%
    String targets2 = (targets != null) ? targets : "";
    String formId2 = (formId != null) ? formId : "";

    String showLoadingText2 = (showLoadingText != null) ? showLoadingText : "true";

    String validate2 = (validate != null) ? validate : "false";
    String ajaxAfterValidation2 = (ajaxAfterValidation != null) ? ajaxAfterValidation : "false";
    String executeScripts2 = (executeScripts != null) ? executeScripts : "true";
    String parseContent2 = (parseContent != null) ? parseContent : "true";

    String errorNotifyTopics2 = (errorNotifyTopics != null) ? "errornotifytopics=\"" + errorNotifyTopics + "\"" : "";
    String beforeNotifyTopics2 = (beforeNotifyTopics != null) ? "beforeNotifyTopics=\"" + beforeNotifyTopics + "\"" : "";
    String afterNotifyTopics2 = (afterNotifyTopics != null) ? "afterNotifyTopics=\"" + afterNotifyTopics + "\"" : "";

    String type2 = (type != null) ? type : "submit";
    String id2 = (id != null) ? "id=\"" + id + "\"" : "";
    String value2 = (value != null) ? value : "";
    if (!"".equals(value2 ) && value2.startsWith("#")) {
        String tmp = String.valueOf(request.getAttribute(value2.substring(1)));
        if (null != tmp) {
            value2 = tmp;
        }
    }
    
    String disabled2 = "";
    if (disabled != null && disabled.equals("true")) {
        disabled2 = disabled2 + " disabled=\"" + disabled + "\" ";
    }


//////////////////////////////////////////////////////


    request.setAttribute("targets2", targets2);
    request.setAttribute("formId2", formId2);

    request.setAttribute("showLoadingText2", showLoadingText2);

    request.setAttribute("validate2", validate2);
    request.setAttribute("ajaxAfterValidation2", ajaxAfterValidation2);
    request.setAttribute("executeScripts2", executeScripts2);
    request.setAttribute("parseContent2", parseContent2);

    request.setAttribute("errorNotifyTopics2", errorNotifyTopics2);
    request.setAttribute("beforeNotifyTopics2", beforeNotifyTopics2);
    request.setAttribute("afterNotifyTopics2", afterNotifyTopics2);

    request.setAttribute("type2", type2);
    request.setAttribute("id2", id2);
    request.setAttribute("value2", value2);
    request.setAttribute("disabled2", disabled2);

%>
<!--<div class="btnType1" onmouseover="turnon( this, true );" onmouseout="turnoff( this, true );">-->
<!--<button style="font-size: 13px;" class="l" tabindex="-1" onclick="return false;" type="button">&nbsp;</button>-->
<input
    dojotype="struts:Bind" class="myButton" events="onclick"    
    targets="${targets2}"
    formid="${formId2}"
    validate="${validate2}"
    ajaxaftervalidation="${ajaxAfterValidation2}"
    executescripts="${executeScripts2}"
    parsecontent="${parseContent2}"

    ${errorNotifyTopics2}
    ${beforeNotifyTopics2}
    ${afterNotifyTopics2}

    type="${type2}"
    ${id2}
    value="${value2}"
    ${disabled2}
    />  <!--<button class="r" style="font-size: 13px;" onclick="return false;" tabindex="-1">&nbsp;</button>
</div>-->
