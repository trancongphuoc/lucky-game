<%@tag display-name="VCL-Button: Native-style button" pageEncoding="UTF-8"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@attribute name="id"%>
<%@attribute name="value"%>

<%@attribute name="onfocus"%>
<%@attribute name="onblur"%>

<%@attribute name="onclick"%>
<%@attribute name="ondblclick"%>

<%@attribute name="onmousedown"%>
<%@attribute name="onmouseup"%>
<%@attribute name="onmousemove"%>
<%@attribute name="disabled"%>

<%
        String id2 = (id != null) ? "id=\"" + id + "\"" : "";

        String callbacks = "";
        callbacks += (onfocus != null) ? "onfocus=\"" + onfocus + "\" " : "";
        callbacks += (onblur != null) ? "onblur=\"" + onblur + "\" " : "";
        callbacks += (onclick != null) ? "onclick=\"" + onclick + "\" " : "";
        callbacks += (ondblclick != null) ? "ondblclick=\"" + ondblclick + "\" " : "";
        callbacks += (onmousedown != null) ? "onmousedown=\"" + onmousedown + "\" " : "";
        callbacks += (onmouseup != null) ? "onmouseup=\"" + onmouseup + "\" " : "";
        callbacks += (onmousemove != null) ? "onmousemove=\"" + onmousemove + "\" " : "";

        request.setAttribute("callbacks", callbacks);
        request.setAttribute("id2", id2);
        String _disabled = (disabled != null && disabled.equalsIgnoreCase("true")) ? "disabled=\"disabled\"" : "";
        request.setAttribute("_disabled", _disabled);

%>
<div ${callbacks} ${id2} class="btnType1" onmouseover="turnon( this );" onmouseout="turnoff( this );"><button type="button" class="l" tabindex="-1">&nbsp;</button><button type="submit" onclick="return false;" class="m" ${_disabled} >${value}</button><button type="button" class="r" tabindex="-1">&nbsp;</button></div>

