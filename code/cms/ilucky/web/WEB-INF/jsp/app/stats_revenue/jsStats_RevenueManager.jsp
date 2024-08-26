<%-- 
    Document   : jsStats_RevenueManager
    Created on : Sep 10, 2013, 9:28:37 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<script type="text/javascript">

    dojo.event.topic.subscribe("Stats_RevenueManagerForm/searchStats_Revenue", function(event, widget) {
        widget.href = "Stats_RevenueManagerAction!searchStats_RevenueManager.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Stats_RevenueManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });  
//    searchStats_RevenueManager = function() {
//        var areaId = 'listStats_RevenueManagerDiv';
//        var action = 'Stats_RevenueManagerAction!searchStats_RevenueManager.do';
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

    searchCMD = function() {
        var areaId = 'Stats_Revenue_CMD_Div';
        var action = 'Stats_RevenueManagerAction!R_searchCommands.do';
        var param = ["sub_service_name=" + document.getElementById('Stats_RevenueManagerForm.sub_service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }
    searchSub_Service_Stats_Re = function() {
        var areaId = 'Stats_Revenue_Sub_Service_Div';
        var action = 'Stats_RevenueManagerAction!searchSub_Service_Stats_Re.do';
        var param = ["service_name=" + document.getElementById('Stats_RevenueManagerForm.service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }

</script>
