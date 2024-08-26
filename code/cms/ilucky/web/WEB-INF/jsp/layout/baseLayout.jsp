<%--<%@page import="com.viettel.database.utils.SessionCounter"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"  %>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%--<%@include file="../common/language.jsp" %>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<s:i18n name="com/viettel/config/Language">
    <html>
        <head>
            <title>
                CMS
            </title>
            <tiles:insertTemplate template="/WEB-INF/jsp/layout/external_CSS_JS.jsp" />
            <!--<link href="version/latest/style.css" rel="stylesheet" type="text/css" />-->
            <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/template/css/stylesheet.css" />

            <link type="text/css" href="<%=request.getContextPath()%>/template/js/jquery/ui/themes/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />


        </head>
        <!--<script type="text/javascript" src="version/latest/img/jquery.min.js"></script>-->
        <script type="text/javascript" src="<%=request.getContextPath()%>/version/latest/img/ddaccordion.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery/ui/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery/tabs.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/template/js/jquery/superfish/js/superfish.js"></script>
        <script type="text/javascript">
            var djConfig = {
                isDebug: false,
                parseOnLoad: true
            };
        </script>

        <script type="text/javascript">
            ddaccordion.init({
                headerclass: "submenuheader", //Shared CSS class name of headers group
                contentclass: "submenu", //Shared CSS class name of contents group
                revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
                mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
                collapseprev: true, //Collapse previous content (so only one open at any time)? true/false
                defaultexpanded: [], //index of content(s) open by default [index1, index2, etc] [] denotes no content
                onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
                animatedefault: false, //Should contents open by default be animated into view?
                persiststate: true, //persist state of opened contents within browser session?
                toggleclass: ["", ""], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
                togglehtml: ["suffix", "<img src='share/newStyle/img/plus.gif' class='statusicon' />", "<img src='share/newStyle/img/minus.gif' class='statusicon' />"], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
                animatespeed: "fast", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
                oninit: function(headers, expandedindices) { //custom code to run when headers have initalized
                    //do nothing
                },
                onopenclose: function(header, index, state, isuseractivated) { //custom code to run whenever a header is opened or closed
                    //do nothing
                }
            })
        </script>

        <body>     
            </script>
            <div id="container">
                <div id="container">
                    <div id="header">
                        <div class="div1">
                            <%--<%@include file="../common/language_choice.jsp" %>--%>
                            <%@include file="../common/language_choice.jsp" %>
                            <%@include file="../common/language.jsp" %>
                            <div class="div2" style="font-weight: 400;font-size: 20px;"><fmt:message key="login.header" /></div>
                            
                        </div>
                    </div>

                    <!--Menu bar-->
                    <jsp:include page="menubar.jsp"/>
                    <!--Menu bar-->

                </div>

                <sx:div id="bodyContent">
                    <!--Content-->
                    <div>
                        <tiles:insertAttribute name="body"/>                
                        <tiles:insertTemplate template="/WEB-INF/jsp/layout/external_CSS_JS_Project.jsp" />
                        <!--Content-->
                    </div>
                </sx:div>
            </div>                   
            <div id="footer">
                <a href="http://">LUCKYWHEEL</a> 
                © 2022<br> ver 1
            </div>
        </body>

    </html>
</s:i18n>
