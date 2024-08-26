<%-- 
    Document   : jsStats_DailyManager
    Created on : Feb 14, 2014, 10:55:07 AM
    Author     : hanhnv62
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<script type="text/javascript">

    function IsPositiveNumber(sText)
    {
        var ValidChars = "0123456789";
        var IsNumber = true;
        var Char;
        for (i = 0; i < sText.length && IsNumber == true; i++)
        {
            Char = sText.charAt(i);
            if (ValidChars.indexOf(Char) == -1)
            {
                IsNumber = false;
                return false;
            }
        }
        return true;
    }


    dojo.event.topic.subscribe("Stats_DailyManagerForm/searchStats_Daily", function(event, widget) {
        if (dojo.widget.byId('Stats_DailyManagerForm.date').domNode.childNodes[1].value == null || dojo.widget.byId('Stats_DailyManagerForm.date').domNode.childNodes[1].value == "") {
            dialog.alert('Hãy chọn ngày!');
            event.cancel = true;
            return;
        }else {
            widget.href = "Stats_DailyManagerAction!getStats_Daily.do";        }

        dialog.showLoadingDialog();
        
    });
    dojo.event.topic.subscribe("Stats_DailyManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });
    
    var searchServices_Daily_Sub = function() {
        var areaId = 'Stats_Daily_ServiceDiv';
        var action = 'Stats_DailyManagerAction!searchServices.do';
        var param = ["provider_name=" + document.getElementById('Stats_DailyManagerForm.provider_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
    
    var searchSub_Service_Stats_Da = function() {
        var areaId = 'Stats_Daily_Sub_Service_Div';
        var action = 'Stats_DailyManagerAction!searchSub_Service_Stats_Da.do';
        var param = ["service_name=" + document.getElementById('Stats_DailyManagerForm.service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
    
    function downloadExcelRevuenue(url) {
        var from_date = "";
        var to_date = "";
        var x = document.getElementsByName("dojo.Stats_DailyManagerForm.date");
        if (x != null && x.length > 0) {
          from_date = x[0].value;
        }
        
        x = document.getElementsByName("dojo.Stats_DailyManagerForm.toDate");
        if (x != null && x.length > 0) {
          to_date = x[0].value;
        }
       
        
        var hrf = url + "/downloadRevenue.do?from_date="+from_date
                + "&to_date="+to_date
        ;
        window.location = hrf;
    };

</script>
