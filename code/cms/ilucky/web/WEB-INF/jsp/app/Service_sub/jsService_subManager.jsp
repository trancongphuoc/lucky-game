<%-- 
    Document   : jsService_sub
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="../../common/language.jsp" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("Service_subManagerForm/searchService_sub", function(event, widget) {
//        alert('haind');
        if (document.getElementById('Service_subManagerForm.msisdn') == null || document.getElementById('Service_subManagerForm.msisdn').value == "") {
            dialog.alert('<fmt:message key="ss.nstb" />');
            event.cancel = true;
            return;
        }
        widget.href = "Service_subManagerAction!searchService_sub.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Service_subManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });

//    searchSub_Service_Sub_a = function() {
//        var areaId = 'listService_subManagerDiv';
//        var action = 'Service_subManagerAction!searchSub_Service_Sub.do';
//        var param = ["struts.token.name=" + document.getElementsByName('struts.token.name')[0].value, "token=" + document.getElementsByName('token')[0].value];
//
//        var afterCallback = function(_areaId, _data) {
//            dialog.closeLoadingDialog();//            
//        }
//
//        var errorCallback = function(_areaId, _data) {
//            dialog.closeLoadingDialog();
//        }
//        ajax(areaId, action, param, afterCallback, errorCallback);
//        dialog.showLoadingDialog();
//    }

    searchSub_Service_Sub = function() {
        var areaId = 'Service_subDiv';
        var action = 'Service_subManagerAction!searchSub_Service_Sub.do';
        var param = ["service_name=" + document.getElementById('Service_subManagerForm.service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var Service_sub_cancel = function(msisdn, sub_service_name) {
        var areaId = 'Service_sub_cancel_DlgDiv';
        var action = 'Service_subManagerAction!Service_sub_cancel.do';
        var param = ["msisdn=" + msisdn, "sub_service_name=" + sub_service_name];
       // alert(msisdn + sub_service_name);
        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('Service_sub_cancel_Dlg');
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var closeService_sub_cancel_Dlg = function() {
        dialog.close('Service_sub_cancel_Dlg');
    };

    dojo.event.topic.subscribe("Service_subManagerForm/Cancel_service_sub", function(event, widget) {
        if (document.getElementById('Service_subManagerForm.descriptions') == null || document.getElementById('Service_subManagerForm.descriptions').value == "") {
            dialog.alert('<fmt:message key="ss.ldhktr" />');
            event.cancel = true;
            return;
        }
        widget.href = "Service_subManagerAction!Cancel_service_sub.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Service_subManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('Service_sub_cancel_Dlg');
    });

    dojo.event.topic.subscribe("Service_subManagerForm/Active_service_sub", function(event, widget) {
        widget.href = "Service_subManagerAction!Active_service_sub.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Service_subManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('Service_sub_cancel_Dlg');
    });
</script>