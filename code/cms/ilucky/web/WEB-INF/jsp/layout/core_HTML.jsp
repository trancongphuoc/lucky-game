<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../common/language.jsp" %>

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
                <div class="button" style="text-align:center;">
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button class="l" tabindex="-1">&nbsp;</button><button class="m" onclick="dialog.closeTextDialog();">Đóng</button><button class="r" tabindex="-1">&nbsp;</button></div>
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
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button class="l" tabindex="-1">&nbsp;</button><button class="m" onclick="dialog.closeConfirmDialog( true );">Đồng ý</button><button class="r" tabindex="-1">&nbsp;</button></div>
                    &nbsp;
                    <div class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button class="l" tabindex="-1">&nbsp;</button><button class="m" onclick="dialog.closeConfirmDialog( false );">Bỏ qua</button><button class="r" tabindex="-1">&nbsp;</button></div>
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

