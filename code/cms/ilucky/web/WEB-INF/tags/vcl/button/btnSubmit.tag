<%@tag display-name="VCL-Button: Native-style button" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>


<%@attribute name="id"%>
<%@attribute name="value"%>

<%@attribute name="disabled"%>
<%@attribute name="onclick"%>

<%
        String id2 = (id != null) ? "id=\"" + id + "\"" : "";
        request.setAttribute("id2", id2);
        String _disabled = (disabled != null && disabled.equalsIgnoreCase("true")) ? "disabled=\"disabled\"" : "";
        request.setAttribute("_disabled", _disabled);
        String _onclick = (onclick != null) ? "onclick=\"" + onclick + "\"" : "";

%>
<%--onclick="searchStatisticsBrands(); return false;"--%>
                  <!--<div ${id2} class="myButton" >-->
                    <!--<button class="l" tabindex="-1" type="button">&nbsp;</button>-->
                    <button  type="submit"  class="myButton" ${_disabled} tabindex="-1"  ${_onclick}  >${value}</button>
                    <!--<button class="r" tabindex="-1">&nbsp;</button>-->
                  <!--</div>-->                 
<%--<div ${id2} class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );">
                    <button type="button" class="l" tabindex="-1">&nbsp;</button><button type="submit" onclick="return false;" class="m" ${_disabled} >${value}</button><button type="button" class="r" tabindex="-1">&nbsp;</button></div>--%>

