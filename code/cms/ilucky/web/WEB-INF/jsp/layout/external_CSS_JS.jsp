<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="com.viettel.utils.ResourceBundleUtils"%>
<%@include file="../common/language.jsp" %>


<link href="<%=request.getContextPath()%>/share/js/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="<%=request.getContextPath()%>/share/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/share/js/jquery-ui.min.js"></script>

<%--start new style--%>
<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/version/latest/style.css" type="text/css">-->
<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/template/css/stylesheet.css" type="text/css">-->
<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/template/css/jquery-ui-1.css" type="text/css">-->
<!--<link rel="stylesheet" href="<%=request.getContextPath()%>/template/css/invoice.css" type="text/css">-->
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/version/latest/img/ddaccordion.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/version/latest/img/jquery.min.js"></script>


<!--<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/multifile.js"></script>-->


<%--end new style--%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/formset.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/block.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/label.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/dialog.css" type="text/css">
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/ajax.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/DOM.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/dialog.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/datetimepicker_css.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/displaytag.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/share/css/displaytagex.css" type="text/css">

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/commons.js"></script>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/share/js/jsUtil.js"></script>

<link rel="shortcut icon" href="<%=request.getContextPath()%>/share/icon/icon_vt.ico">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/share/css/layout.css" media="all" />

    
 

<script type="text/javascript">

    var dom = new DOM();
    var dialog = new Dialog();
    var mouseXOnBrowser, mouseYOnBrowser, mouseXOnScreen, mouseYOnScreen;
    var jTblStore = [];

    function documentMouseMoveHandler( evt ) {
        var event = ( window.event ) ? window.event : evt;

        mouseXOnBrowser = event.clientX;
        mouseYOnBrowser = event.clientY;
        mouseXOnScreen = event.screenX;
        mouseYOnScreen = event.screenY;

        if( dialog.getCurrentDialog() != null ) {
            var dlg = dialog.getCurrentDialog();
            var y = Math.round( mouseYOnBrowser ) + dlg.indentY;
            var x = Math.round( mouseXOnBrowser ) + dlg.indentX;

            dom.gotoXY( dlg, x, y );

            if( dlg.iFr ) {
                dom.gotoXY( dlg.iFr, x, y );
            }
        }

    }

    function windowResizeHandler( evt ) {
        var event = ( window.event ) ? window.event : evt;
        var divs = document.getElementsByTagName( 'div' );
        var i;

        var x, y;
        var actualPageW, actualPageH;
        var pageSize;

        pageSize = dom.getPageWH();

        x = '0px';
        y = '0px';

        actualPageW = pageSize[2];
        actualPageH = pageSize[3];

        for( i = 0; i < divs.length; i++ ) {
            if( divs[i].id.indexOf( Dialog.divKey ) != -1 && dom.isVisible( divs[i].id ) ) {
                dom.style( divs[i] ).top = y;
                dom.style( divs[i] ).left = x;
                dom.style( divs[i] ).height = actualPageH + 'px';
                dom.style( divs[i] ).width = actualPageW + 'px';
            }
        }

    }

    document.onmousemove = documentMouseMoveHandler;
    window.onresize = windowResizeHandler;
    document.onmouseup = function( e ) {
        var event = ( window.event ) ? window.event : e;
        var prop;

        var mXOnBrowser = event.clientX;
        var mYOnBrowser = event.clientY;

        for( prop in jTblStore ) {
            if( jTblStore[prop] && jTblStore[prop].test ) {
                jTblStore[prop].test( mXOnBrowser, mYOnBrowser );
            }
        }
    }
    Dialog.SYS_WARNING_DIALOG_TITLE = "error";
    Dialog.MESSAGE_DIALOG_DEFAULT_TITLE = "error";
    Dialog.CONFIRM_DIALOG_DEFAULT_TITLE = "error";
</script>
<iframe id="uploadFrameInS40" name="uploadFrameInS40" style="display:none;"></iframe>

<!-- [ LongH -->

<script language="javascript">

    function calcLockingDivAppearance() {
        var div = $( 'overlay' );
        var bodyContent = document.getElementById('bodyContent');
        div.style.top = bodyContent.offsetTop + 'px';
        div.style.left = bodyContent.offsetLeft + 'px';
        div.style.width = bodyContent.offsetWidth + 'px';
        div.style.height = bodyContent.offsetHeight + 'px';
    }

    function disableStart(){
        var bodyContent = document.getElementById('bodyContent');
    }
    document.title = "LUCKYWHEEL";
    initWidget();
</script>

<div id="dialogVCL-loading">
    <div class="inner"><b><fmt:message key="wait" /></b></div>
</div>

<div id="dialogVCL" class="dialog">
    <div class="top-left" onmousedown="dialogMouseDown( this );" onmouseup="dialogMouseUp( this );">
        <div class="top-right">
            <div class="top-middle">
                <div class="label"><div id="dialogVCL-title"></div></div>
                <div onmouseover="dialogMouseEvent( 'over', 'close', this )" onmouseout="dialogMouseEvent( 'out', 'close', this )" onmousedown="dialogMouseEvent( 'down', 'close', this )" class="close"></div>
                <div onmouseover="dialogMouseEvent( 'over', 'minimize', this )" onmouseout="dialogMouseEvent( 'out', 'minimize', this )" onmousedown="dialogMouseEvent( 'down', 'minimize', this )" class="minimize"></div>
            </div>
        </div>
    </div>
    <div class="body-left">
        <div class="body-right">
            <div class="body-middle">
                <div id="dialogVCL-content"></div>
<!--                <div class="button" style="text-align:center;">
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );">
                        <button class="l" tabindex="-1">&nbsp;</button>
                        <button class="m" onclick="dialog.closeTextDialog();">Close</button>
                        <button class="r" tabindex="-1">&nbsp;</button>
                    </div>
                </div>-->
                
                <div style="text-align: center; margin-bottom: 5px;">
                    <div  class="myButton" onclick="dialog.closeTextDialog();">Close</div>
                        </div>
            </div>
        </div>
    </div>
    <div class="bottom-left">
        <div class="bottom-right">
            <div class="bottom-middle"></div>
        </div>
    </div>
</div>

<div id="dialogVCL-confirm" class="dialog">
    <div class="top-left" onmousedown="dialogMouseDown( this );" onmouseup="dialogMouseUp( this );">
        <div class="top-right">
            <div class="top-middle">
                <div class="label"><div id="dialogVCL-confirm-title"></div></div>
                <div onmouseover="dialogMouseEvent( 'over', 'close', this )" onmouseout="dialogMouseEvent( 'out', 'close', this )" onmousedown="dialogMouseEvent( 'down', 'close', this )" class="close"></div>
                <div onmouseover="dialogMouseEvent( 'over', 'minimize', this )" onmouseout="dialogMouseEvent( 'out', 'minimize', this )" onmousedown="dialogMouseEvent( 'down', 'minimize', this )" class="minimize"></div>
            </div>
        </div>
    </div>
    <div class="body-left">
        <div class="body-right">
            <div class="body-middle">
                <div id="dialogVCL-confirm-content" class="questionIcon"></div>
                <div class="button" style="text-align:center;">
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button class="l" tabindex="-1">&nbsp;</button><button class="m" onclick="dialog.closeConfirmDialog( true );"><fmt:message key="button.dy" /></button><button class="r" tabindex="-1">&nbsp;</button></div>
                    &nbsp;
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button class="l" tabindex="-1">&nbsp;</button><button class="m" onclick="dialog.closeConfirmDialog( false );"><fmt:message key="button.h" /></button><button class="r" tabindex="-1">&nbsp;</button></div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottom-left">
        <div class="bottom-right">
            <div class="bottom-middle"></div>
        </div>
    </div>
</div>
<div id="magicDiv-modalDialog"></div>
<div id="magicDiv-loadingDialog"></div>


<script type="text/javascript">

    ////button
    //// [ Stylish button
    function turnon() {        
        var btnContainer;
        var leftBtn, middleBtn, rightBtn;
        btnContainer = arguments[0];
        var btns = btnContainer.getElementsByTagName( 'button' );
        var inputs = btnContainer.getElementsByTagName( 'input' );

        switch( arguments.length ) {
            case 1: // Native button
                leftBtn = btns[0];
                middleBtn = btns[1];
                rightBtn = btns[2];
                break;
            case 2: // SX Submit
                leftBtn = btns[0];
                middleBtn = inputs[0];
                rightBtn = btns[1];
                break;
        }

        btnContainer.style.cursor = "pointer";

        leftBtn.style.cursor = "pointer";
        middleBtn.style.cursor = "pointer";
        rightBtn.style.cursor = "pointer";
//        leftBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_hover_left.gif )";
//        middleBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_hover_middle.gif )";
//        rightBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_hover_right.gif )";


        middleBtn.style.color="black";


    }

    function turnoff() {        
        var btnContainer;
        var leftBtn, middleBtn, rightBtn;

        btnContainer = arguments[0];
        var btns = btnContainer.getElementsByTagName( 'button' );
        var inputs = btnContainer.getElementsByTagName( 'input' );

        switch( arguments.length ) {
            case 1: // Native button
                leftBtn = btns[0];
                middleBtn = btns[1];
                rightBtn = btns[2];
                break;
            case 2: // SX submit
                leftBtn = btns[0];
                middleBtn = inputs[0];
                rightBtn = btns[1];
                break;
        }

        btnContainer.style.cursor = "default";

        leftBtn.style.cursor = "default";
        middleBtn.style.cursor = "default";
        rightBtn.style.cursor = "default";

//        leftBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_left.gif )";
//        middleBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_middle.gif )";
//        rightBtn.style.backgroundImage = "url(<%=request.getContextPath()%>/version/latest/buttons/btn1_right.gif )";

        middleBtn.style.color="black";
    }
    /// end button
</script>