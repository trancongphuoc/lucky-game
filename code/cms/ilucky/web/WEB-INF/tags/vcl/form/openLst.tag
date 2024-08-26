<%@tag display-name="VCL-Form: Open Table List" pageEncoding="UTF-8"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@attribute name="id"%>
<%@attribute name="title"%>
<%@attribute name="width"%>

<%
    String widthClause, idClause, idContainerClause;
    
    widthClause = ( width != null ) ? "style=\"width:" + width + ";\"" : "";
    idClause = ( id != null ) ? "id=\"" + id + "\" name=\"" + id + "\"" : "";
    idContainerClause = ( id != null ) ? "id=\"" + id + "Container\"" : "";

    request.setAttribute( "widthClause", widthClause );
    request.setAttribute( "idClause", idClause );
    request.setAttribute( "idContainerClause", idContainerClause );
%>

<div ${idContainerClause} ${widthClause}>
<table class="lstTbl" ${idClause} width="100%" cellspacing="0" cellpadding="0">
    <tr><th><div class="label">${title}</div>
        
        </th></tr>
    
    <tr>
        <td>