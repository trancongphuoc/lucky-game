<%-- 
    Document   : category
    Created on : Jan 14, 2010, 9:21:00 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<div id="sidebar" class="left">
    <h2 class="ico_mug">Quản trị</h2>
    <ul id="menu">
        <li>
            <a href="#" class="ico_posts" >Nội dung</a>
            <ul>
                <s:if test="#attr.userToken.status =='1'">
                    <li>
                        <a href="javascript:prepareRevenues('deviceAction!prepareDevice.do');" title="Device"> Tìm kiếm Device </a>
                    </li>
                    <li>
                        <a href="javascript:prepareRevenues('configNameAction!prepareConfigName.do');" title="Device"> Config </a>
                    </li>
                </s:if>
                <s:elseif test="#attr.userToken.roles=='manager'">
                    <li>
                        <a href="javascript:prepareRevenues('configNameAction!prepareConfigName.do');" title="Device"> Config </a>
                    </li>
                </s:elseif>
                <s:elseif test="#attr.userToken.roles=='cskh'">
                    <li>
                        <a href="javascript:prepareRevenues('configNameAction!prepareConfigName.do');" title="Device"> CSKH </a>
                    </li>
                </s:elseif>
                <s:elseif test="#attr.userToken.roles=='test'">
                    <li>
                        <a href="javascript:prepareRevenues('configNameAction!prepareConfigName.do');" title="Device"> TEST </a>
                    </li>
                </s:elseif>

            </ul>
        </li>
    </ul>
</div><!-- end #sidebar -->
<script type="text/javascript">
    prepareRevenues=function(action){
        var areaId = 'bodyContent';
        //var action = 'prepareRevenues!prepareRevenues.do';

        var t='0';

        var param = ["year=" + t];
        var afterCallback = function(_areaId, _data ) {
            dialog.closeLoadingDialog();
        }
        var errorCallback = function( _areaId, _data ) {
            dialog.closeLoadingDialog();
        }
        ajax( areaId, action, param, afterCallback, errorCallback );
        dialog.showLoadingDialog();
    }

    prepareRevenuesBase=function(action){
        var areaId = 'baseContent';
        //var action = 'prepareRevenues!prepareRevenues.do';
        var t='0';
        var param = ["year=" + t];
        var afterCallback = function(_areaId, _data ) {
            dialog.closeLoadingDialog();
        }
        var errorCallback = function( _areaId, _data ) {
            dialog.closeLoadingDialog();
        }
        ajax( areaId, action, param, afterCallback, errorCallback );
        dialog.showLoadingDialog();
    }


</script>

<script type="text/javascript">
    function initMenu() {

        $('#menu ul').hide();

        $('#menu ul:first').show();
        $('#menu li a').click(
        function() {
            var checkElement = $(this).next();
            if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
                return false;
            }
            if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
                $('#menu ul:visible').slideUp('normal');
                checkElement.slideDown('normal');
                return false;
            }
        }
    );
    }
    initMenu();
</script>
