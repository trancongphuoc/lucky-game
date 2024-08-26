



<%@tag display-name="VCL-Message: Warning" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="value"%>
<%@attribute name="global"%>

<%
    String idClause = "";
    String shortcutClause = "";
    String closeClause = "";
    
    if( global != null && "true".equalsIgnoreCase( global ) ) {
        idClause = "id=\"glbWarning\"";
        closeClause = "closeGlbMsg";
        shortcutClause = "";
    } else {
        idClause = ( id != null ) ? "id=\"" + id + "\"" : "";
        closeClause = "closeMsg";
        shortcutClause = "<div class=\"shortcut\" onclick=\"shortcutMsg( this );\"></div>";
    }

    request.setAttribute( "idClause", idClause );
    request.setAttribute( "closeClause", closeClause );
    request.setAttribute( "shortcutClause", shortcutClause );
%>

<div ${idClause} class="warning">
<%--<div style="margin:5px 0px;border:1px solid #c82820;color:#c82820;font-family: Arial, Arial, sans-serif;font-size:12px;font-weight:700;background:#e7928d;padding:5px;-moz-border-radius:3px;-webkit-border-radius: 3px;">--%>
    ${shortcutClause}
    <div class="icon" onclick="${closeClause}( this );"></div>
    <div class="inner">
        ${value}
    </div>
</div>