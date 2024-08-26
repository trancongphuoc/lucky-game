<%-- 
    Document   : ChangePassMsg
    Created on : Nov 8, 2012, 2:35:05 PM
    Author     : Hanhnv62
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="vb" tagdir="/WEB-INF/tags/vcl/button/" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../common/language.jsp" %>

<div class="warning_password">
    <br/>
    <h2>
       <p id="form-login-warning" style="text-align: left; padding-left: 40px;">
            <label class="FormLabel" id="warning_id" style="color:red; width: 250px; margin-top: 300px;" >
                <b>
                    <i><fmt:message key="wj.tdptc" /></i>
                    
                   
            </label>
            <br/>
        </p>
    </h2>
</div>
<script type="text/javascript" > 
    var time = 1; //How long (in seconds) to countdown 
    var page = "<%=request.getContextPath()%>/Login!logout.do"; //The page to redirect to 
    function countDown()
    { 
        time--; 
        if(time==0)
        {
//            window.alert("THAY ĐỔI MẬT KHẨU THÀNH CÔNG"+"\n\n"+" Vui lòng đăng nhập lại với mật khẩu mới");
window.alert("<fmt:message key="qltk.tdmktc" />");
            window.location = page;
        }
        gett("changePassLogout").innerHTML = time;
    } 
    function gett(id)
    { 
        if(document.getElementById) return document.getElementById(id); 
        if(document.all) return document.all.id; 
        if(document.layers) return document.layers.id; 
        if(window.opera) return window.opera.id; 
    } 
    function init()
    {
        if(gett('changePassLogout'))
        {
            setInterval(countDown, 1000); gett("changePassLogout").innerHTML = time; 
        } 
        else
        { 
            setTimeout(init, 50);
        } 
    }
    document.onload = init(); 
</script> 
