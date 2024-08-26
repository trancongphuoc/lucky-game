<%-- 
    Document   : jsStats_ErrorManager
    Created on : Feb 14, 2014, 10:55:07 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<script type="text/javascript">

    dojo.event.topic.subscribe("Stats_ErrorManagerForm/searchStats_Error", function(event, widget) {
        var date = '';
        var month = '-1';
        var years = '-1';
        if (dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1] != null && dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1].value != null) {
            date = dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1].value;
        }

        if (document.getElementById('Stats_ErrorManagerForm.month') != null && document.getElementById('Stats_ErrorManagerForm.month').value != null) {
            month = document.getElementById('Stats_ErrorManagerForm.month').value;
        }

        if (document.getElementById('Stats_ErrorManagerForm.years') != null && document.getElementById('Stats_ErrorManagerForm.years').value != null) {
            years = document.getElementById('Stats_ErrorManagerForm.years').value;
        }

        if (date == '') {
            if (month == '-1' || years == '-1') {
                dialog.alert('Hãy lựa chọn ngày hoặc tháng, năm để thống kê!');
                event.cancel = true;
                return;
            }
        }

        widget.href = "Stats_ErrorManagerAction!getStats_Error.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Stats_ErrorManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });

    searchStatistics = function() {
        var action = 'Stats_ErrorManagerAction!getStats_Error1.do';
        var areaId = 'listStats_ErrorManagerDiv';
        
        var provider_name = document.getElementById('Stats_ErrorManagerForm.provider_name').value;
        var service_name = document.getElementById('Stats_ErrorManagerForm.service_name').value;
        var sub_service_name = document.getElementById('Stats_ErrorManagerForm.sub_service_name').value;        
        
        var date = '';
        var month = '-1';
        var years = '-1';

        if (dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1] != null && dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1].value != null) {
            date = dojo.widget.byId('Stats_ErrorManagerForm.date').domNode.childNodes[1].value;        }

        if (document.getElementById('Stats_ErrorManagerForm.month') != null && document.getElementById('Stats_ErrorManagerForm.month').value != null) {
            month = document.getElementById('Stats_ErrorManagerForm.month').value;            

        }

        if (document.getElementById('Stats_ErrorManagerForm.years') != null && document.getElementById('Stats_ErrorManagerForm.years').value != null) {
            years = document.getElementById('Stats_ErrorManagerForm.years').value;            

        }
        
        if (date == '') {
            if (month == '-1' || years == '-1') {
                dialog.alert('Hãy lựa chọn ngày hoặc tháng, năm để thống kê!');
                return;
            }
        }

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();

        }
        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }        
        var param = ["provider_name=" + provider_name, "service_name=" + service_name, "sub_service_name=" + sub_service_name, "date=" + date, "month=" + month, "years=" + years];
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();

    }


    searchServices_Error_Sub = function() {
        var areaId = 'Stats_Error_ServiceDiv';
        var action = 'Stats_ErrorManagerAction!searchServices.do';
        var param = ["provider_name=" + document.getElementById('Stats_ErrorManagerForm.provider_name').value];
        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }

    searchSub_Service_Stats_E = function() {
        var areaId = 'Stats_Error_Sub_Service_Div';
        var action = 'Stats_ErrorManagerAction!searchSub_Service_Stats_E.do';
        var param = ["service_name=" + document.getElementById('Stats_ErrorManagerForm.service_name').value];
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
