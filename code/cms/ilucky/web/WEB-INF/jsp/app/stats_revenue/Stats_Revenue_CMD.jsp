
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@taglib prefix="vf" tagdir="/WEB-INF/tags/vcl/form/" %>
<%@taglib prefix="vb" tagdir="/WEB-INF/tags/vcl/button/" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="vd" tagdir="/WEB-INF/tags/vcl/dialog/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>


<sx:div id="listStats_Revenue_CMDDiv">
    <s:if test="#request.R_CommandsList !=null">
       <%-- <s:select list="#request.R_CommandsList"
                  listKey="cmd" listValue="cmd"
                  headerKey="-1" headerValue="--Chọn mã lệnh--"
                  name="Stats_RevenueManagerForm.cmd"
                  id="Stats_RevenueManagerForm.cmd" cssStyle="width:150px;">
        </s:select>    --%>
                <select
            name="Stats_RevenueManagerForm.cmd" 
                  id="Stats_RevenueManagerForm.cmd" cssStyle="width:150px;">
            <option value="-1" > <fmt:message key="c.cml" /></option>
            <c:forEach items="${R_CommandsList}" var="data">
                <option value="${data.cmd}" > ${data.cmd}</option>
            </c:forEach>
        </select>
    </s:if>
</sx:div>