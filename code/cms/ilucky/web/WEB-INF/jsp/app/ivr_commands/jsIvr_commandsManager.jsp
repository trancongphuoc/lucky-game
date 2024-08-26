<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="../../common/language.jsp" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("Ivr_commandsManagerForm_searchIvr_commands", function(event, widget) {
//        alert('haind');
        widget.href = "IvrCommandAction!searchIvr_commands.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Ivr_commandsManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });
//    searchIvr_commands_a = function() {
//        var areaId = 'listIvr_commandsManagerDiv';
//        var action = 'Ivr_commandsManagerAction!searchIvr_commands.do';
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
    var regex_string = /^\s*\d+\s*$/;
    dojo.event.topic.subscribe("Ivr_commandsManagerEditForm/updateInsertIvr_commands", function(event, widget) {
        

        widget.href = "IvrCommandAction!updateInsertIvr_commands.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("Ivr_commandsManagerEditForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('Ivr_commandsEditDlg');
    });


    var closeIvr_commandsManagerDlg = function() {
        dialog.close('Ivr_commandsEditDlg');
    };

    var prepareAddIvr_commands = function() {
//        alert('aa');
        var areaId = 'Ivr_commandsEditDlgDiv';
        var action = 'IvrCommandAction!preAddIvr_commands.do';
//        var param = ["sub_service_name=" + document.getElementById('Ivr_commandsManagerForm.sub_service_name').value];
        var param = [];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('Ivr_commandsEditDlg');
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var deleteIvr_commands = function(id, sub_service_name) {
        var isDelete = confirm('are you sure to delete: ' + id + ' ?');
        id =  encodeURIComponent(id);
        if (isDelete) {
            var areaId = 'listIvr_commandsManagerDiv';
            var action = 'IvrCommandAction!deleteIvr_commands.do';
            var param = ["id=" + id, "sub_service_name=" + sub_service_name, "struts.token.name=" + document.getElementsByName('struts.token.name')[0].value, "token=" + document.getElementsByName('token')[0].value];
            var afterCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            };

            var errorCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            };
            ajax(areaId, action, param, afterCallback, errorCallback);
            dialog.showLoadingDialog();
        }
        else {
            event.cancel = true;
            return;
        }
    };

    var prepareEditIvr_commands = function(id, sub_service_name) {
        var areaId = 'Ivr_commandsEditDlgDiv';
        var action = 'IvrCommandAction!preEditIvr_commands.do';
        id =  encodeURIComponent(id);
        var param = ["id=" + id, "sub_service_name=" + sub_service_name];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('Ivr_commandsEditDlg');
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var searchSub_Services_Ivr_command = function() {
        var areaId = 'Ivr_commandsDiv';
        var action = 'IvrCommandAction!searchSub_Service.do';
        var param = ["service_name=" + document.getElementById('Ivr_commandsManagerForm.service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var searchWS_Ivr_command = function() {
        var areaId = 'Ivr_commandsEditDlgDiv';
        var action = 'IvrCommandAction!searchWebservice.do';
        var param = ["sub_service_name=" + document.getElementById('Ivr_commandsManagerEditForm.sub_service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
    
    var searchWS_Ivr_command1 = function() {
        var areaId = 'IvrDlgDiv';
        var action = 'NewServicesManagerAction!searchWebservice.do';
        var param = ["sub_service_name=" + document.getElementById('Ivr_commandsManagerEditForm.sub_service_name').value];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }  ; 
    
    var CopyIvr_commands = function(id, sub_service_name) {
        var areaId = 'Ivr_commandsEditDlgDiv';
        var action = 'IvrCommandAction!preEditIvr_commands.do';
        id =  encodeURIComponent(id);
        var param = ["id=" + id, "sub_service_name=" + sub_service_name,"Copy_Ivr=1"];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('Ivr_commandsEditDlg');
        };

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

</script>