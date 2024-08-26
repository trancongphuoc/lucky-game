<%-- 
    Document   : jsUser
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="../../common/language.jsp" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("changePassForm/changePassword", function(event, widget){  
        if(document.getElementById('changePassForm.oldPass') ==null || document.getElementById('changePassForm.oldPass').value==""){
            dialog.alert('<fmt:message key="login.bpnmkht" /> ');
            event.cancel=true;
            return;
        }
        if(document.getElementById('changePassForm.newPass') ==null || document.getElementById('changePassForm.newPass').value==""){
            dialog.alert('<fmt:message key="c.bpntmkm" /> ');
            event.cancel=true;
            return;
        }
        if(document.getElementById('changePassForm.newPass2') ==null || document.getElementById('changePassForm.newPass2').value==""){
            dialog.alert('<fmt:message key="c.bpnlmkm" /> ');
            event.cancel=true;
            return;
        }
        if(document.getElementById('changePassForm.newPass').value!=document.getElementById('changePassForm.newPass2').value){
            dialog.alert('<fmt:message key="c.mkmktn" />');
            event.cancel=true;
            return;
        }
        if(document.getElementById('changePassForm.oldPass').value==document.getElementById('changePassForm.newPass').value){
            dialog.alert('<fmt:message key="cj.tbmkmpkmkc" /> ');
            event.cancel=true;
            return;
        }
        var regex_string = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_]).{8,})";
        if(!document.getElementById('changePassForm.newPass').value.match(regex_string))
        {
            dialog.alert('<fmt:message key="c.mkkdm" />');
            event.cancel=true;
            return;                
        }
        widget.href = "changePassAction!changePassword.do";
        dialog.showLoadingDialog();
    });
    dojo.event.topic.subscribe("changePassForm/after", function(event, widget){       
        dialog.closeLoadingDialog();
    });
    
</script>