<%-- 
    Document   : jsCommands
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

    dojo.event.topic.subscribe("CommandsManagerForm/searchCommands", function(event, widget) {
        widget.href = "CommandsManagerAction!searchCommands.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("CommandsManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });

//    searchCommands = function() {
//        var areaId = 'listCommandsManagerDiv';
//        var action = 'CommandsManagerAction!searchCommands.do';
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
    
    dojo.event.topic.subscribe("CommandsManagerEditForm/updateInsertCommands", function(event, widget) {
        if (document.getElementById('CommandsManagerEditForm.cmd') == null || document.getElementById('CommandsManagerEditForm.cmd').value == "-1") {
            dialog.alert('cmd.cktr');
            event.cancel = true;
            return;
        }
        widget.href = "CommandsManagerAction!updateInsertCommands.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("CommandsManagerEditForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('CommandsEditDlg');
    });


    closeCommandsManagerDlg = function() {
        dialog.close('CommandsEditDlg');
    }

    prepareAddCommands = function() {
        var areaId = 'CommandsEditDlgDiv';
        var action = 'CommandsManagerAction!preAddCommands.do';
        var param = ["sub_service_name=" + document.getElementById('CommandsManagerForm.sub_service_name').value]

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('CommandsEditDlg');
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }

    prepareDeleteCommands = function(cmd, sub_service_name) {
        var isDelete = confirm('<fmt:message key="cmd.bctccx" /> ' + cmd + ' ?');
        if (isDelete) {
            var areaId = 'listCommandsManagerDiv';
            var action = 'CommandsManagerAction!deleteCommands.do';
            var param = ["cmd=" + cmd, "sub_service_name=" + sub_service_name, "struts.token.name=" + document.getElementsByName('struts.token.name')[0].value, "token=" + document.getElementsByName('token')[0].value];
            var afterCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            }

            var errorCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            }
            ajax(areaId, action, param, afterCallback, errorCallback);
            dialog.showLoadingDialog();
        }
        else {
            event.cancel = true;
            return;
        }
    }

    prepareEditCommands = function(cmd, sub_service_name) {
        var areaId = 'CommandsEditDlgDiv';
        var action = 'CommandsManagerAction!preEditCommands.do';
        var param = ["cmd=" + cmd, "sub_service_name=" + sub_service_name];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('CommandsEditDlg');
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }

    searchSub_Services_command = function() {
        var areaId = 'CommandsDiv';
        var action = 'CommandsManagerAction!searchSub_Service.do';
        var param = ["service_name=" + document.getElementById('CommandsManagerForm.service_name').value];

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