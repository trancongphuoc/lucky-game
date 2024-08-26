<%-- 
  
    Author     : trieunv
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/language.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<script type="text/javascript">

dojo.event.topic.subscribe("SearchCpKpiSoapForm/searchCpKpiSoap", function (event, widget) {
    
     if (document.getElementById('SearchCpKpiSoapForm.msisdn') == null || document.getElementById('SearchCpKpiSoapForm.msisdn').value == "") {
            dialog.alert('<fmt:message key="sl.fail.msisdnne" />');
            event.cancel = true;           
            return;
        }
        widget.href = "SearchCpKpiSoapAction!searchCpKpiSoap.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("SearchCpKpiSoapForm/after", function (event, widget) {
        dialog.closeLoadingDialog();
    });
    

</script>