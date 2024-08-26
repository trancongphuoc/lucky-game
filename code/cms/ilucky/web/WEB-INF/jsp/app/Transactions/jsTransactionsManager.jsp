<%-- 
    Document   : jsTransactions
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

    dojo.event.topic.subscribe("TransactionsManagerForm/searchTransactions", function(event, widget) {
        if (document.getElementById('TransactionsManagerForm.msisdn') == null || document.getElementById('TransactionsManagerForm.msisdn').value == "") {
            dialog.alert('<fmt:message key="ss.nstb" />');
            event.cancel = true;
            return;
        }
        widget.href = "TransactionsManagerAction!searchTransactions.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("TransactionsManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });  

//    searchTransactions = function() {
//        var areaId = 'listTransactionsManagerDiv';
//        var action = 'TransactionsManagerAction!searchTransactions.do';
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
    
    var ViewMO_MT_Dlg = function(Id) {
        var areaId = 'MO_MT_DlgDiv';
        var action = 'TransactionsManagerAction!ViewMO_MT.do';
        var param = ["Id=" + Id];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('MO_MT_Dlg');
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
    
    var closeMO_MT_Dlg= function() {
        dialog.close('MO_MT_Dlg');
    };
    
    var searchSub_Services = function() {
        var areaId = 'TransactionsDiv';
        var action = 'TransactionsManagerAction!searchSub_Service.do';
        var param = ["service_name=" + document.getElementById('TransactionsManagerForm.service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
    
    function downloadTransactionLog(url) {
        
//        widget.href = "mt_allManagerAction!searchmt_all.do";
        var msidn = document.getElementById("TransactionsManagerForm.msisdn").value;
        var from_date = "";
        var to_date = "";
        var x = document.getElementsByName("dojo.TransactionsManagerForm.request_time");
        if (x != null && x.length > 0) {
          from_date = x[0].value;
        }
        
        x = document.getElementsByName("dojo.TransactionsManagerForm.response_time");
        if (x != null && x.length > 0) {
          to_date = x[0].value;
        }
        
        var hrf = url + "/downloadTransactionLog.do?msisdn=" + msidn 
                + "&from_date="+from_date
                + "&to_date="+to_date
        ;
//        alert(hrf);
//        widget.href = hrf;
        window.location = hrf;
    };
</script>