<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <tiles:insertTemplate template="/WEB-INF/jsp/layout/external_CSS_JS.jsp" />
        <title>LUCKYWHEEL</title>
    </head>
    <sx:div id="bodyContent">
        <tiles:insertAttribute name="body"/>
    </sx:div>
</html>

<!--/s:i18n-->