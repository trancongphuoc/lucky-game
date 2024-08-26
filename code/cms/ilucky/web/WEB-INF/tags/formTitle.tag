<%@tag display-name="Form Title" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@attribute name="colspan"%>
<%@attribute name="value"%>

<%
    String colspanClause = "";
    colspanClause = ( colspan != null ) ? "colspan=\"" + colspan + "\"" : "";

    request.setAttribute( "colspanClause", colspanClause );
%>

<th ${colspanClause}>
    <div  style="border:solid 1px #CCCCCC;padding:4px;font-weight:bold;background-color:#C6E1EE;color:#296F94">${value}</div>
</th>