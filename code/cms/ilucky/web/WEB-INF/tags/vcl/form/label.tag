<%@tag display-name="VCL-Form: Form Item Label" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="value"%>
<%@attribute name="required"%>

<%
String idClause, requiredClause;

idClause = ( id != null ) ? "for=\"" + id + "\"" : "";
requiredClause = ( required != null && "true".equalsIgnoreCase( required ) ) ? "class=\"required\"" : "";

request.setAttribute( "idClause", idClause );
request.setAttribute( "requiredClause", requiredClause );
%>

<label ${idClause} ${requiredClause}>${value}: </label>