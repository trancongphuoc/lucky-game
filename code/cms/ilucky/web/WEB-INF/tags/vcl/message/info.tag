<!--package WEB-INF.message;-->

<%@tag display-name="VCL-Message: Info" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="value"%>
<%@attribute name="global"%>

<%
    String idClause = "";
    String shortcutClause = "";
    String closeClause = "";

    if( global != null && "true".equalsIgnoreCase( global ) ) {
        idClause = "id=\"glbMessage\"";
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

<div ${idClause} class="message">
    ${shortcutClause}
    <div class="icon" onclick="${closeClause}( this );"></div>
    <div class="inner">
        ${value}
    </div>
</div>