<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@include file="../common/language.jsp" %>

<%--<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/form/" prefix="vf" %>
<%@include file="WEB-INF/jsp/common/language.jsp" %>--%>
<sx:head />

<div id="menu">
    <s:if test="#attr.changePass!=0 && #attr.loginUpdatepass!=0 && #attr.userToken.getStatus() !=0">
        <ul class="left" style="display: none;">
            <li id="dashboard"><a href="" class="top"><fmt:message key="menu.home" /></a></li>
                <%--<s:if test="#attr.userToken.getUserType() ==1 or #attr.userToken.getUserType() ==2  or #attr.userToken.getUserType() ==3 or #attr.userToken.getUserType() ==5">--%>
                <li id="catalog"><a class="top"><fmt:message key="menu.sheach" /></a>
                    <ul>
                        <%--<s:if test="#attr.userToken.getUserType() ==1 ">--%>
                          <li>
                              <a href="javascript:prepareRevenues('Service_subManagerAction!prepareService_subManager.do')">Subscriber</a>
                          </li> 
                          <li>
                              <a href="javascript:prepareRevenues('TransactionsManagerAction!prepareTransactionsManager.do')">Transaction Log</a>
                          </li>  
                          <li>
                              <a href="javascript:prepareRevenues('MtAllAction!prepare.do')"><fmt:message key="menu.shech.allmt" /></a>
                          </li>
                        <%--</s:if>--%>
                    </ul>
                </li>
            <%--</s:if>--%>
           <%--  <s:if test="#attr.userToken.getUserType() ==1">
                <li id="Li1"><a class="top">Golo</a>
                    <ul>
                        <li>
                            <a href="javascript:prepareRevenues('IvrCommandAction!prepareIvr_commandsManager.do')">Question</a>
                        </li>
                        <li>
                            <a href="javascript:prepareRevenues('userManagerAction!prepareUserManager.do')"> <fmt:message key="menu.qluser" /></a>
                        </li>
                    </ul>
                </li>
            </s:if> --%>
            <s:if test="#attr.userToken.getUserType() ==1">
                <li id="Li11"><a class="top"><fmt:message key="menu.tkcp" /></a>
                    <ul>
<!--                        <li>
                            <a href="javascript:prepareRevenues('ServicesVcgwinfoAction!prepareServicesVcgwinfo.do')"><fmt:message key="menu.kbvic"/></a>
                        </li>-->
                        <li>
                            <a href="javascript:prepareRevenues('Stats_DailyManagerAction!prepareStats_DailyManager.do')"><fmt:message key="menu.tk.tqdt" /></a>
                        </li>
                    </ul>
                </li>
             </s:if>
        </ul>
    </s:if>
    <ul class="right">
        <li id="store">
            <a class="top" href="#" ><fmt:message key="menu.welcomename" /> <s:property escapeJavaScript="true" value="#attr.userToken.username"></s:property></a>
            </li>
<!--            <li id="store">
                <a class="top" href="javascript:prepareRevenues('changePassAction!prepareChangePass.do')"><fmt:message key="menu.changepass" /></a>
            </li>-->
            <li id="store">
                <a class="top" href="Login!logout.do"><fmt:message key="menu.logout" /></a>
        </li>
    </ul>

    <script type="text/javascript"><!--
        $(document).ready(function() {
            $('#menu > ul').superfish({
                hoverClass: 'sfHover',
                pathClass: 'overideThisToUse',
                delay: 0,
                animation: {height: 'show'},
                speed: 'normal',
                autoArrows: false,
                dropShadows: false,
                disableHI: false, /* set to true to disable hoverIntent detection */
                onInit: function() {
                },
                onBeforeShow: function() {
                },
                onShow: function() {
                },
                onHide: function() {
                }
            });

            $('#menu > ul').css('display', 'block');
        });

        function getURLVar(urlVarName) {
            var urlHalves = String(document.location).toLowerCase().split('?');
            var urlVarValue = '';

            if (urlHalves[1]) {
                var urlVars = urlHalves[1].split('&');

                for (var i = 0; i <= (urlVars.length); i++) {
                    if (urlVars[i]) {
                        var urlVarPair = urlVars[i].split('=');

                        if (urlVarPair[0] && urlVarPair[0] == urlVarName.toLowerCase()) {
                            urlVarValue = urlVarPair[1];
                        }
                    }
                }
            }

            return urlVarValue;
        }

        $(document).ready(function() {
            route = getURLVar('route');

            if (!route) {
                $('#dashboard').addClass('selected');
            } else {
                part = route.split('/');

                url = part[0];

                if (part[1]) {
                    url += '/' + part[1];
                }

                $('a[href*=\'' + url + '\']').parents('li[id]').addClass('selected');
            }
        });
        //--></script>

</div>
</div>


<script type="text/javascript">
     var prepareRevenues = function(action) {
        var areaId = 'bodyContent';
        var t = '0';
        var param = ["year=" + t];

        var afterCallback = function() {
            dialog.closeLoadingDialog();
        };
        var errorCallback = function() {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };

    var prepareRevenuesBase = function(action) {
        var areaId = 'baseContent';
        var t = '0';
        var param = ["year=" + t];
        var afterCallback = function() {
            dialog.closeLoadingDialog();
        };
        var errorCallback = function() {
            dialog.closeLoadingDialog();
        };
        ajax(areaId, action, param, afterCallback, errorCallback);
        dialog.showLoadingDialog();
    };
</script>