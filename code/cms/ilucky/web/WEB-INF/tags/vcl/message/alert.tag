<%@tag display-name="VCL-Message: Alert" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="value"%>
<%@attribute name="global"%>

<%
    String idClause = "";
    String shortcutClause = "";
    String closeClause = "";

    if( global != null && "true".equalsIgnoreCase( global ) ) {
        idClause = "id=\"glbAlert\"";
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

<div ${idClause} class="alert">
    ${shortcutClause}
    <div class="icon" onclick="${closeClause}( this );"></div>
    <div class="inner">
        ${value}
    </div>
</div>