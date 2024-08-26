<%-- 
    Document   : jsStats_SubscribersManager
    Created on : Sep 10, 2013, 9:28:37 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<script type="text/javascript">

    dojo.event.topic.subscribe("Stats_SubscribersManagerForm/searchStats_Subscribers", function(event, widget) {
        widget.href = "Stats_SubscribersManagerAction!searchStats_SubscribersManager.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Stats_SubscribersManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });  
//    searchStats_SubscribersManager = function() {
//        var areaId = 'listStats_SubscribersManagerDiv';
//        var action = 'Stats_SubscribersManagerAction!searchStats_SubscribersManager.do';
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
    
    searchServices_Stats_Sub = function() {
        var areaId = 'Stats_Subscribers_ServiceDiv';
        var action = 'Stats_SubscribersManagerAction!searchServices_Stats_Sub.do';
        var param = ["provider_name=" + document.getElementById('Stats_SubscribersManagerForm.provider_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }
    
    searchSub_Service_Stats_sub = function() {
        var areaId = 'Stats_Subscribers_Sub_ServiceDiv';
        var action = 'Stats_SubscribersManagerAction!searchSub_Service_Stats_sub.do';
        var param = ["service_name=" + document.getElementById('Stats_SubscribersManagerForm.service_name').value];

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
