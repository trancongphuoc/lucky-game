<%@tag display-name="VCL-Form: Close Fieldset Form" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="title"%>
<%@attribute name="width"%>
<%@attribute name="height"%>
<%@attribute name="theme"%>

<%
    String heightClause, widthClause, idClause, idContainerClause, themeClause;
    String styleClause = "";

    //widthClause = ( width != null ) ? "style=\"width:" + width + ";\"" : "";
    heightClause = ( height != null ) ? "style=\"height:" + height + ";\"" : "";
    if( width != null ) {
        styleClause = "style=\"width:" + width + ";";
        if( height != null ) {
            styleClause += "height:" + height + ";";
        }
        styleClause += "\"";
    }
    idClause = ( id != null ) ? "id=\"" + id + "\" name=\"" + id + "\"" : "";
    idContainerClause = ( id != null ) ? "id=\"" + id + "Container\"" : "";
themeClause = ( theme != null ) ? "class=\"" + theme + "\"" : "class=\"transparent\"";

    //request.setAttribute( "widthClause", widthClause );
    //request.setAttribute( "heightClause", heightClause );
    request.setAttribute( "idClause", idClause );
    request.setAttribute( "idContainerClause", idContainerClause );
    request.setAttribute( "themeClause", themeClause );

    request.setAttribute( "styleClause", styleClause );
%>

<div ${idContainerClause} ${styleClause}>
<fieldset ${idClause} ${themeClause} style="height:100%;">
    <legend><span class="iconFS" onclick="toggleDisplayFS( this );"></span><span class="titleFS">${title}</span></legend>
    <div class="innerFS">
        <table width="100%" cellspacing="0" cellpadding="0">