<%-- 
    Document   : jsUser
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("reportCRBTForm/searchReportCRBT", function(event, widget){       
        widget.href = "reportCRBTAction!searchReportCRBT.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("reportCRBTForm/after", function(event, widget){       
        dialog.closeLoadingDialog();
    });
    
    exportExcelReportCRBT=function(){
//        var param=[""];
        ajax("DivFormReportCRBT", "reportCRBTAction!exportExcel.do");
    }
   
</script>