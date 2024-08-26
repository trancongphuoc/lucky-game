<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/form/" prefix="vf" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/button/" prefix="vb" %>--%>

<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/form/" prefix="vf" %>

<html lang="${language}">
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LUCKY WHEEL</title>

        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/template/css/stylesheet.css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery-1.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery-ui-1.js"></script>
        <link type="text/css" href="<%=request.getContextPath()%>/template/css/jquery-ui-1.css" rel="stylesheet">
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/tabs.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/superfish.js"></script>

        <script language="javascript">
            function submit()
            {
                document.forms["login"].submit();
            }
        </script>

        <script language="javascript">
            function RefreshImage(id, image) {            //hàm reset captcha khi click                
                var elm = document.getElementById(id);
                var dt = new Date();
                elm.src = image + "?dt=" + dt;
                return true;
            }
        </script>

    </head>

    <body>        
        <div id="container">
            <div id="header">
                <div class="div1">
                    <%@include file="WEB-INF/jsp/common/language_choice.jsp" %>
                    <%@include file="WEB-INF/jsp/common/language.jsp" %>
                    <div class="div2" style="font-weight: 400;font-size: 20px;"><fmt:message key="login.header" /></div>
                </div>
            </div>
            <div id="content">
                <div class="box" style="width: 400px; min-height: 300px; margin-top: 40px; margin-left: auto; margin-right: auto;">
                    <div class="heading">
                        <h1><img src="<%=request.getContextPath()%>/template/images/product.png" alt=""><fmt:message key="login.title" /></h1>
                    </div>
                    <div class="content" style="min-height: 150px; overflow: hidden;">  
                        <s:form action="Login"  method="post" name="login" id="form-login" style="clear: both;">
                            <tbody><tr>
                                    <td style="text-align: center;" rowspan="4">
                                        <img src="<%=request.getContextPath()%>/template/images/j_joomla_box.jpg" alt="enter your information">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="login.account" /><br>
                                        <input autocomplete="off" id="username" name="username"
                                               onfocus="if (this.value == <fmt:message key="login.inputname" />)
                                                           this.value = '';"
                                               onblur="if (this.value == '')
                                                           this.value = <fmt:message key="login.inputname" />;" type="text" style="width: 135px;">
                                        <br>
                                        <br>

                                        <fmt:message key="login.pass" /><br>
                                        <input autocomplete="off" id="password" name="password"
                                               onfocus="if (this.value == <fmt:message key="login.inputpass" />)
                                                           this.value = '';"
                                               onblur="if (this.value == '')
                                                           this.value = <fmt:message key="login.inputname" />;" type="password" style="width: 135px;">
                                        <br>
                                        <br>

                                        <s:if test="#attr.login>=3">
                                            <div>
                                                <img name="captcha" id="captcha" src="captchaImage.jsp" style="cursor: pointer" onclick="return RefreshImage('captcha', 'captchaImage.jsp')" width="141" height="38"/>                                                                                         
                                            </div>
                                            <div>
                                                <input name="secureletter" type="text" id="secureletter" maxlength="6" style="width:135px;"/>
                                            </div>
                                        </s:if>

                                        <br>
                                        <s:if test="request.getAttribute(\"warning\") != null">
                                            <div class="warning">
                                                <%--<%= // request.getAttribute("warning") != null ? request.getAttribute("warning") : ""%>--%>
                                                <fmt:message key="${requestScope.warning}" />;
                                            </div>
                                        </s:if>

                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;">                                            
                                        <a onclick="return submit();" class="button"><fmt:message key="login.submit" /></a>
                                    </td>
                                </tr>
                            </tbody>
                            </table>                            
                        </s:form>     
                    </div>                    
                </div>
            </div>
            <script language="javascript">
                $('#form-login').keydown(
                        function(e) {
                            if (e.keyCode == 13) {
                                document.forms["login"].submit();
                            }
                        }
                );
            </script>
        </div>

        <div id="footer"> <a href="">LUCKY WHEEL GAME 2022</a> 
                </div>
    </body>
</html>
