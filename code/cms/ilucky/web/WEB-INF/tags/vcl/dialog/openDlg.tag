<%@tag display-name="VCL-Dialog: Open Dialog" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="title"%>

<%@attribute name="width"%>
<%@attribute name="height"%>


<%
    String widthClause, heightClause, idClause;

    widthClause = ( width != null ) ? "style=\"width:" + width + ";\"" : "";
    heightClause = ( height != null ) ? "height:" + height + ";" : "";
    idClause = ( id != null ) ? "id=\"" + id + "\"" : "";

    request.setAttribute( "widthClause", widthClause );
    request.setAttribute( "heightClause", heightClause );
    request.setAttribute( "idClause", idClause );
    String title2 = (title != null) ? title : "title";

    if (!"".equals(title2 ) && title2.startsWith("#")) {
        String tmp = String.valueOf(request.getAttribute(title2.substring(1)));
        if (null != tmp) {
            //out.println("haind: " + tmp);
            title2 = tmp;
        }
    }
     request.setAttribute("titleClause", title2);
%>

<div ${idClause} ${widthClause}  class="dialog">
    <div class="top-left" onmousedown="dialogMouseDown( this );" onmouseup="dialogMouseUp( this );">
        <div class="top-right">
            <div class="top-middle">
                <div class="dialog-label">${titleClause}</div>
                <div onmouseover="dialogMouseEvent( 'over', 'close', this )" onmouseout="dialogMouseEvent( 'out', 'close', this )" onmousedown="dialogMouseEvent( 'down', 'close', this )" class="close"></div>
                <div onmouseover="dialogMouseEvent( 'over', 'minimize', this )" onmouseout="dialogMouseEvent( 'out', 'minimize', this )" onmousedown="dialogMouseEvent( 'down', 'minimize', this )" class="minimize"></div>
            </div>
        </div>
    </div>
    <div class="body-left">
        <div class="body-right">
            <div class="body-middle">
                <div style="padding:4px; ${heightClause}">
