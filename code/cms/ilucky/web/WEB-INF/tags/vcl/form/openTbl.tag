<%@tag display-name="VCL-Form: Open Table Form" pageEncoding="UTF-8"%>

<%@attribute name="id"%>
<%@attribute name="title"%>
<%@attribute name="width"%>
<%@attribute name="theme"%>

<%
            String widthClause, idClause, idContainerClause, themeClause;

            widthClause = (width != null) ? "style=\"width:" + width + ";\"" : "";
            idClause = (id != null) ? "id=\"" + id + "\" name=\"" + id + "\"" : "";
            idContainerClause = (id != null) ? "id=\"" + id + "Container\"" : "";
            themeClause = (theme != null) ? "class=\"" + theme + "\"" : "class=\"frmTbl\"";
            request.setAttribute("widthClause", widthClause);
            request.setAttribute("idClause", idClause);
            request.setAttribute("idContainerClause", idContainerClause);
            request.setAttribute("themeClause", themeClause);
%>

<div ${idContainerClause} ${widthClause}>
    <%--<table class="frmTbl" ${idClause} width="100%"  cellspacing="0" cellpadding="0">--%>
    <table ${themeClause} ${idClause} width="100%"  cellspacing="0" cellpadding="0">

        <tr>
            <th>
                <div class="label">${title}</div><div class="displayToggle" onclick="toggleDisplayFrmTbl( this );"></div>
            </th>
        </tr>
        <tr>
            <td style="margin:0px;padding:4px;" valign="top">
                <table width="100%" cellspacing="0" cellpadding="0">
