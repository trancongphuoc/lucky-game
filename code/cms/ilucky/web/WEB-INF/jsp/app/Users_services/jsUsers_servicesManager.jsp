<%-- 
    Document   : jsUsers_services
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("Users_servicesManagerForm/updateInsertUsers_services", function(event, widget) {
        widget.href = "Users_servicesManagerAction!updateInsertUsers_services.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Users_servicesManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });

    searchservices = function() {
        var areaId = 'pagingUsers_servicesManagerDiv';
        var action = 'Users_servicesManagerAction!searchservices.do';
        var param = ["username=" + document.getElementById('Users_servicesManagerForm.username').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };


    function checkAll() 
    {
        //alert("Check all the checkboxes..."); 
        var allRows = document.getElementById()("chkId");
        for (var i=0; i < allRows.length; i++) {
            if (allRows[i].type == 'checkbox') 
            {
                allRows[i].checked = true;
            }
        }

    }

</script>