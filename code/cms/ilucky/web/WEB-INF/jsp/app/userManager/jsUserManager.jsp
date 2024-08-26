<%-- 
    Document   : jsUser
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/language.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<script type="text/javascript">


    
    dojo.event.topic.subscribe("userManagerForm/searchUser", function(event, widget) {
        widget.href = "userManagerAction!searchUser.do";        
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("userManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();       
    });
//    searchUser = function() {
//        var areaId = 'listUserManagerDiv';
//        var action = 'userManagerAction!searchUser.do';
//        var param = ["struts.token.name=" +document.getElementsByName('struts.token.name')[0].value, "token=" +document.getElementsByName('token')[0].value];
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
    dojo.event.topic.subscribe("userManagerEditForm/updateInsertUser", function(event, widget) {
        if (document.getElementById('userManagerEditForm.username') == null || document.getElementById('userManagerEditForm.username').value == "") {
            dialog.alert('<fmt:message key="c.tdnktr" />');
            event.cancel = true;
            return;
        }

        if (document.getElementById('userManagerEditForm.username') == null || document.getElementById('userManagerEditForm.username').value == "") {
            if (document.getElementById('userManagerEditForm.password').value != document.getElementById('repassword').value) {
                dialog.alert('<fmt:message key="c.mkkdtn" />');
                event.cancel = true;
                return;
            }
            var regex_string = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
            if (!document.getElementById('userManagerEditForm.password').value.match(regex_string))
            {
                dialog.alert('<fmt:message key="c.mkkdm" />');
                event.cancel = true;
                return;
            }
        }
		
	
	//alert();	
		var arrActive = ["0", "1"]; 
		var chooseActive = arrActive.indexOf(document.getElementById('userManagerEditForm.status').value);  
		if(chooseActive == -1)
		{
			//dialog.alert('<fmt:message key="c.sinv" />');
			dialog.alert('Invalid status');
			event.cancel = true;
			return;
		}
		
		var arrAuthor = ["1", "2", "3", "4","5"]; 
		var chooseAuthor = arrAuthor.indexOf(document.getElementById('userManagerEditForm.user_type').value);  
		if(chooseAuthor == -1)
		{
			//dialog.alert('<fmt:message key="c.usnvl" />');
			dialog.alert('Invalid type user');
			event.cancel = true;
			return;
		}

        widget.href = "userManagerAction!updateInsertUser.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("userManagerEditForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('userEditDlg');
    });
    dojo.event.topic.subscribe("userChangePassForm/updateChangePassUser", function(event, widget) {
        if (document.getElementById('userChangePassForm.password') == null || document.getElementById('userChangePassForm.password').value == "") {
            dialog.alert('<fmt:message key="c.bpntmkm" /> ');
            event.cancel = true;
            return;
        }
        var regex_string = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
        if (!document.getElementById('userChangePassForm.password').value.match(regex_string))
        {
            dialog.alert('<fmt:message key="c.mkkdm" />');
            event.cancel = true;
            return;
        }
        if (document.getElementById('newrepassword') == null || document.getElementById('newrepassword').value == "") {
            dialog.alert('<fmt:message key="c.bpnlmkm" />');
            event.cancel = true;
            return;
        }
        if (document.getElementById('userChangePassForm.password').value != document.getElementById('newrepassword').value) {
            dialog.alert('<fmt:message key="c.mkmktn" />');
            event.cancel = true;
            return;
        }
        widget.href = "userManagerAction!updateChangePass.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("userChangePassForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
        dialog.close('userChangePassDlg');
    });

    closeUserManagerDlg = function() {
        dialog.close('userEditDlg');
    }
    closeUserChangePassDlg = function() {
        dialog.close('userChangePassDlg');
    }
    prepareAddUser = function() {
        var areaId = 'userEditDlgDiv';
        var action = 'userManagerAction!preAddUser.do';
        var param = ['username=""'];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('userEditDlg');
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }

    prepareDeleteUser = function(username) {
        var isDelete = confirm('<fmt:message key="c.bcccthxnd" /> ' + username + ' ?');
        if (isDelete) {
            var areaId = 'listUserManagerDiv';
            var action = 'userManagerAction!deleteUser.do';
            var param = ["username=" + username, "struts.token.name=" +document.getElementsByName('struts.token.name')[0].value, "token=" +document.getElementsByName('token')[0].value];
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

    prepareEditUser = function(username) {
        var areaId = 'userEditDlgDiv';
        var action = 'userManagerAction!preEditUser.do';
        var param = ["username=" + username];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('userEditDlg');
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }
    prepareChangePassUser = function(username) {
        var areaId = 'userChangePassDlgDiv';
        var action = 'userManagerAction!preChangePassUser.do';
        var param = ["username=" + username];

        var afterCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
            dialog.open('userChangePassDlg');
        }

        var errorCallback = function(_areaId, _data) {
            dialog.closeLoadingDialog();
        }
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    }
</script>