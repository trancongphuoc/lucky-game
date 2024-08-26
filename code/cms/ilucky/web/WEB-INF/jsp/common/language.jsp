
<%@page import="java.util.PropertyResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
<%@page import="javax.servlet.jsp.jstl.core.Config"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:set value="0" var="languageEnable"/>

<%--<c:if test="${languageEnable==0}">--%>
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="Language" /> 
<%--</c:if>--%>




